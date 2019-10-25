/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dsms.app.AddUpdateProductsFeature;

import com.dsms.app.DatabaseConnection;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;

public class AddUpdateProducts {
    
    @FXML
    Accordion panelAccordion;
    @FXML
    TitledPane addProductPane;
    @FXML
    Label databaseMsg;
    @FXML
    TextField txtAddName;
    @FXML
    TextField txtAddVariant;
    @FXML
    ChoiceBox cbAddDptCode;
    @FXML
    TextField txtAddProdType;
    @FXML
    TextField txtAddVendor;
    @FXML
    TextField txtAddMRP;
    @FXML
    TextField txtAddStockQty;
    @FXML
    ChoiceBox cbAddQtyUnit;
    @FXML
    TextField txtAddQtyThr;
    @FXML
    TextField txtFetchSKU;
    @FXML
    TextField txtUpdateVariant;
    @FXML
    TextField txtUpdateName;
    @FXML
    ChoiceBox cbUpdateDptCode;
    @FXML
    TextField txtUpdateProdType;
    @FXML
    TextField txtUpdateVendor;
    @FXML
    TextField txtUpdateMRP;
    @FXML
    TextField txtUpdateStockQty;
    @FXML
    ChoiceBox cbUpdateQtyUnit;
    @FXML
    TextField txtUpdateQtyThr;

    Connection con;

    String addName;
    String addVariant;
    String addDptCode;
    String addProdType;
    String addVendor;
    int addMRP;
    int addStockQty;
    String addQtyUnit;
    int addQtyThr;
    
    String fetchedSKU;
    String updateName;
    String updateVariant;
    String updateDptCode;
    String updateProdType;
    String updateVendor;
    int updateMRP;
    int updateStockQty;
    String updateQtyUnit;
    int updateQtyThr;

    @FXML
    public void initialize() {
        try {
        
            con=DatabaseConnection.getConnection();
        
            panelAccordion.setExpandedPane(addProductPane);
            
            ObservableList<String> dptData = FXCollections.observableArrayList();
            try {
                String SQL = "SELECT DEPARTMENT_CODE FROM DEPARTMENT";
                ResultSet rs = con.createStatement().executeQuery(SQL);
                while(rs.next()) {
                    dptData.add(new ProductDepartment(rs.getString(1)).getDptCodes());
                }
                cbAddDptCode.setItems(dptData);
                cbUpdateDptCode.setItems(dptData);
                cbAddDptCode.getSelectionModel().select("GEN");
                cbUpdateDptCode.getSelectionModel().select("GEN");
            } catch(SQLException se) {
                databaseMsg.setText(se.toString());
                System.err.println(se);
            }   
            
            cbAddQtyUnit.setItems(FXCollections.observableArrayList("pcs","kg","g","m"));
            cbAddQtyUnit.getSelectionModel().selectFirst();
        
            cbUpdateQtyUnit.setItems(FXCollections.observableArrayList("pcs","kg","g","m"));
            cbUpdateQtyUnit.getSelectionModel().selectFirst();
        
        } catch(Exception e) {
            System.err.println(e);
        }
    }

    public void addProduct() {
        
        try {
            
            addName = txtAddName.getText();
            if(txtAddVariant.getText().equals(""))
                addVariant = "None";
            else addVariant = txtAddVariant.getText();
            addDptCode = cbAddDptCode.getSelectionModel().getSelectedItem().toString();
            addProdType = txtAddProdType.getText();
            addVendor = txtAddVendor.getText();
            addQtyUnit = cbAddQtyUnit.getSelectionModel().getSelectedItem().toString();
            
            addMRP = Integer.parseInt(txtAddMRP.getText());
            addStockQty = Integer.parseInt(txtAddStockQty.getText());
            addQtyThr = Integer.parseInt(txtAddQtyThr.getText());
            
            if(1>addStockQty ||  1>addQtyThr)
                throw new SQLException("Quantity must be a positive number.");
            
            CallableStatement generateSKU = con.prepareCall("{CALL ADD_NEW_PRODUCT(?,?,?,?,?)}");
            generateSKU.setString(1,addName);
            generateSKU.setString(2,addDptCode);
            generateSKU.setString(3,addProdType);
            generateSKU.setString(4,addVendor);
            generateSKU.registerOutParameter(5,Types.CHAR);
            generateSKU.execute();
            String createdSKU = generateSKU.getString(5).substring(0,8);
            System.out.println(createdSKU+" lalala");
            
            PreparedStatement productDescription = con.prepareStatement("INSERT INTO PRODUCT_DESCRIPTION(SKU,VARIANT,MRP,STOCK_QUANTITY,QUANTITY_THRESHOLD,QUANTITY_UNIT) VALUES(?,?,?,?,?,?)");
            productDescription.setString(1,createdSKU);
            productDescription.setString(2,addVariant);
            productDescription.setInt(3,addMRP);
            productDescription.setInt(4,addStockQty);
            productDescription.setInt(5,addQtyThr);
            productDescription.setString(6,addQtyUnit);
            int rowsUpdated=productDescription.executeUpdate();
            databaseMsg.setText("Added "+rowsUpdated+" product as "+createdSKU);            
            
        } catch(NumberFormatException nfe) {
            databaseMsg.setText("Quantity must be a value.");
        } catch (SQLException se) {
            System.err.println(se);
            databaseMsg.setText(se.toString());
        }   
    }
    
    public void fetchProduct() {
        try {
            fetchedSKU = "SKU"+txtFetchSKU.getText();
            if(txtUpdateVariant.getText().equals(""))
                updateVariant = "None";
            else updateVariant = txtUpdateVariant.getText();
            String SQL = "SELECT * FROM V_UPDATE_PRODUCT WHERE SKU=? AND VARIANT=?";
            PreparedStatement fetchStatement = con.prepareStatement(SQL);
            fetchStatement.setString(1,fetchedSKU);
            fetchStatement.setString(2,updateVariant);
            ResultSet rs = fetchStatement.executeQuery();
            
            if(rs.next()) {
                updateName=rs.getString(2);
                updateDptCode=rs.getString(3);
                updateProdType=rs.getString(4);
                updateVendor=rs.getString(5);
                updateVariant=rs.getString(6);
                updateMRP=rs.getInt(7);
                updateStockQty=rs.getInt(8);
                updateQtyThr=rs.getInt(9);
                updateQtyUnit=rs.getString(10);
            }
            
            txtUpdateName.setText(updateName);
            cbUpdateDptCode.getSelectionModel().select(updateDptCode);
            txtUpdateProdType.setText(updateProdType);
            txtUpdateVendor.setText(updateVendor);
            txtUpdateVariant.setText(updateVariant);
            txtUpdateMRP.setText(String.valueOf(updateMRP));
            txtUpdateStockQty.setText(String.valueOf(updateStockQty));
            txtUpdateQtyThr.setText(String.valueOf(updateQtyThr));
            cbUpdateQtyUnit.getSelectionModel().select(updateQtyUnit);
              
        } catch (SQLException se) {
            System.err.println(se);
            databaseMsg.setText(se.toString());
    }
}   
    
    
    
    public void updateProduct () {
        try {
            fetchedSKU = "SKU"+txtFetchSKU.getText();
            if(txtUpdateVariant.getText().equals(""))
                updateVariant = "None";
            else updateVariant = txtUpdateVariant.getText();
            updateName = txtUpdateName.getText();
            if(txtUpdateVariant.getText().equals(""))
                updateVariant = "None";
            updateDptCode = cbUpdateDptCode.getSelectionModel().getSelectedItem().toString();
            updateProdType = txtUpdateProdType.getText();
            updateVendor = txtUpdateVendor.getText();
            updateQtyUnit = cbUpdateQtyUnit.getSelectionModel().getSelectedItem().toString();
            
            updateMRP = Integer.parseInt(txtUpdateMRP.getText());
            updateStockQty = Integer.parseInt(txtUpdateStockQty.getText());
            updateQtyThr = Integer.parseInt(txtUpdateQtyThr.getText());
            
            if(0>updateStockQty ||  0>updateQtyThr)
                throw new SQLException("Quantity must be non-negative.");
            
            PreparedStatement updateProduct = con.prepareStatement("UPDATE PRODUCT SET PRODUCT_NAME='"+updateName+"', DEPARTMENT_CODE='"+updateDptCode+"', PRODUCT_TYPE='"+updateProdType+"', VENDOR='"+updateVendor+"' WHERE SKU='"+fetchedSKU+"'");
            updateProduct.executeUpdate();
            PreparedStatement updateDetails = con.prepareStatement("UPDATE PRODUCT_DESCRIPTION SET VARIANT='"+updateVariant+"', MRP="+updateMRP+", STOCK_QUANTITY="+updateStockQty+", QUANTITY_THRESHOLD="+updateQtyThr+", QUANTITY_UNIT='"+updateQtyUnit+"' WHERE SKU='"+fetchedSKU+"' AND VARIANT='"+updateVariant+"'");
            updateDetails.executeUpdate();
            databaseMsg.setText("Updated details for "+fetchedSKU);
            
            
        } catch(NumberFormatException nfe) {
            databaseMsg.setText("Quantity must be a value.");
        } catch (SQLException se) {
            System.err.println(se);
            databaseMsg.setText(se.toString());
        }
    }
}
