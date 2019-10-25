/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dsms.app.CreateOrderFeature;

import com.dsms.app.DatabaseConnection;
import com.dsms.app.NewDeliveryFeature.Delivery;
import com.dsms.app.LoginFeature.User;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class CreateOrder {

    @FXML
    Label databaseMsg;
    @FXML
    Label currentOrderID;
    @FXML
    TextField txtProdSKU;
    @FXML
    ChoiceBox cbProdVariant;
    @FXML
    TextField txtProdQty;
    @FXML
    Button btnAddEntry;
    @FXML
    Button btnGetVariants;
    @FXML
    CheckBox chkbDelivery;
    @FXML
    TableView<NewOrder> table;
    @FXML
    TableColumn colProdSKU;
    @FXML
    TableColumn colProdName;
    @FXML
    TableColumn colVariant;
    @FXML
    TableColumn colQuantity;
    

    private ObservableList<NewOrder> data;
    private 
    Connection con;
    
    String prodSKU;
    String prodVariant;
    int prodQty;
    
    String dbMsg;
    String empID;
    String currOrderID;

    @FXML
    public void initialize() {
        try {
        
        con=DatabaseConnection.getConnection();
        
        empID = User.getEmployeeID();
        
        CallableStatement generateOrder = con.prepareCall("{CALL MAKE_NEW_ORDER(?,?)}");
        generateOrder.setString(1,empID);
        generateOrder.registerOutParameter(2, Types.CHAR);
        generateOrder.execute();
        currOrderID = generateOrder.getString(2).substring(0,13);
        currentOrderID.setText(currOrderID);
        
        colProdSKU.setCellValueFactory(
                new PropertyValueFactory<>("prodSKU"));
        
        colProdName.setCellValueFactory(
                new PropertyValueFactory<>("prodName"));
        
        colVariant.setCellValueFactory(
                new PropertyValueFactory<>("variant"));
        
         colQuantity.setCellValueFactory(
                new PropertyValueFactory<>("prodQty"));
         
        cbProdVariant.setItems(FXCollections.observableArrayList("None"));
        cbProdVariant.getSelectionModel().selectFirst();
        
        } catch(SQLException se) {
            System.err.println(se);
        }
    }

    public void addEntry() {
        
        data = FXCollections.observableArrayList();
        try {
            
            prodSKU = "SKU"+txtProdSKU.getText();
            if(cbProdVariant.getSelectionModel().isEmpty())
                prodVariant="None";
            else prodVariant = cbProdVariant.getSelectionModel().getSelectedItem().toString();
            prodQty = Integer.parseInt(txtProdQty.getText());
            
            if(1>prodQty)
                throw new SQLException("Quantity must be a positive number.");
            
            PreparedStatement createEntry = con.prepareStatement("INSERT INTO V_NEW_ORDER(ORDER_ID,SKU,VARIANT,QUANTITY) VALUES(?,?,?,?)");
            createEntry.setString(1,currOrderID);
            createEntry.setString(2,prodSKU);
            createEntry.setString(3,prodVariant);
            createEntry.setInt(4,prodQty);
            int rowsUpdated=createEntry.executeUpdate();
            databaseMsg.setText("Added "+rowsUpdated+" entry with values "+prodSKU+" ("+prodVariant+") ("+prodQty+")");
            
            cbProdVariant.getItems().clear();
            cbProdVariant.setItems(FXCollections.observableArrayList("None"));
            cbProdVariant.getSelectionModel().selectFirst();
            refresh();
            
        } catch(NumberFormatException nfe) {
            databaseMsg.setText("Quantity must be a value.");
        } catch (SQLException se) {
            System.err.println(se);
            databaseMsg.setText(se.toString());
        }   
    }
    
    public void refresh() {
    try {            
            PreparedStatement viewEntries = con.prepareStatement("SELECT * FROM V_NEW_ORDER WHERE ORDER_ID=?");
            viewEntries.setString(1,currOrderID);
            ResultSet rs = viewEntries.executeQuery();
            while (rs.next()) {
                data.add(new NewOrder(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4)
                ));
            }
            table.setItems(data);
        } catch (SQLException se) {
            System.err.println(se);
            databaseMsg.setText(se.toString());
        }   
    }
    
    public void fetchVariants() {
        
        cbProdVariant.getItems().clear();
        prodSKU = "SKU"+txtProdSKU.getText();
        if (prodSKU.equals("SKU"))
                databaseMsg.setText("You must enter SKU to list its variants.");
        else {
            ObservableList<String> varData = FXCollections.observableArrayList();
            try {
                String SQL = "SELECT VARIANT FROM PRODUCT_DESCRIPTION WHERE SKU='"+prodSKU+"'";
                ResultSet rs = con.createStatement().executeQuery(SQL);
                if(rs.isBeforeFirst()) {
                    while(rs.next()) {
                        varData.add(new NewOrder(rs.getString(1)).getFetchedVariants());
                    }
                    cbProdVariant.setItems(varData);
                } else cbProdVariant.setItems(FXCollections.observableArrayList("None"));
                cbProdVariant.getSelectionModel().selectFirst();
            } catch(SQLException se) {
                databaseMsg.setText(se.toString());
                System.err.println(se);
            }   
        }
    }
    
    public void finalizeOrder(ActionEvent ae) {
        try {
           
            int billAmount;
           
            CallableStatement finalCall = con.prepareCall("{CALL COMPUTE_PURCHASE_AMOUNT(?)}");
            finalCall.setString(1,currOrderID);
            finalCall.execute();
           
            ResultSet rs = con.createStatement().executeQuery("SELECT PURCHASE_AMOUNT FROM ORDERS WHERE ORDER_ID='"+currOrderID+"'");
            if(rs.next()) {
                billAmount = rs.getInt(1);
                if(chkbDelivery.isSelected()) {
                    Delivery.setOrderID(currOrderID);
                    Delivery.setAmount(billAmount);
                
                    Parent root = FXMLLoader.load(getClass().getResource("NewDelivery.fxml"));
        
                    Scene scene = new Scene(root);
                
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.setTitle("[DSMS] Add New Delivery");
                    stage.show();
                
                    final Node source = (Node) ae.getSource();
                    final Stage orderStage = (Stage) source.getScene().getWindow();
                    orderStage.close();
                }
                else {
                    btnAddEntry.setDisable(true);
                    btnGetVariants.setDisable(true);
                    databaseMsg.setText("Bill Amount is Rs. "+billAmount);
                }
            }
        
       } catch(SQLException | IOException e) {
           databaseMsg.setText(e.toString());
           System.err.println(e);
       }
    }
}

