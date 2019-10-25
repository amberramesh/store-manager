/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dsms.app.ViewOrdersFeature;

import com.dsms.app.DatabaseConnection;
import com.dsms.app.LoginFeature.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ViewOrders {

    @FXML
    Label userFullName;
    @FXML
    Label userEmpID;
    @FXML
    TableView<Order> table;
    @FXML
    TableColumn colOrderID;
    @FXML
    TableColumn colOrderTimeStamp;
    @FXML
    TableColumn colPurchaseAmount;
    

    private ObservableList<Order> data;
    Connection con;
    
    String empID;
    String fullName;

    @FXML
    public void initialize() {
        
        con=DatabaseConnection.getConnection();
        
        empID = User.getEmployeeID();
        fullName = User.getFirstName() +" "+ User.getLastName();
        
        userEmpID.setText(empID);
        userFullName.setText(fullName);
        
        colOrderID.setCellValueFactory(
                new PropertyValueFactory<>("orderID"));
        
        colOrderTimeStamp.setCellValueFactory(
                new PropertyValueFactory<>("orderTimeStamp"));
        
        colPurchaseAmount.setCellValueFactory(
                new PropertyValueFactory<>("purchaseAmt"));
        
        buildTable();
        
    }

    public void buildTable() {
        data = FXCollections.observableArrayList();
        try {
            String SQL = "SELECT ORDER_ID,ORDER_TIMESTAMP,PURCHASE_AMOUNT FROM ORDERS WHERE EMPLOYEE_ID=? AND PURCHASE_AMOUNT IS NOT NULL";
            PreparedStatement listOrders = con.prepareStatement(SQL);
            listOrders.setString(1,empID);
            ResultSet rs = listOrders.executeQuery();
            while (rs.next()) {
                data.add(new Order(
                        rs.getString(1),
                        rs.getTimestamp(2),
                        rs.getInt(3)
                ));
            }
            table.setItems(data);
        } catch (SQLException se) {
            System.err.println(se);
        }   
    }
}
