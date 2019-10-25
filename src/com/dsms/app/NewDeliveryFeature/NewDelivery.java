/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dsms.app.NewDeliveryFeature;

import com.dsms.app.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class NewDelivery {

    @FXML
    Label userMessage;
    @FXML
    Label orderID;
    @FXML
    Label amount;
    @FXML
    TextField txtCustName;
    @FXML
    TextField txtAddrLine1;
    @FXML
    TextField txtAddrLine2;
    @FXML
    TextField txtContactNo;
    @FXML
    ChoiceBox cbAssignTo;
    @FXML
    Button btnAddDelivery;
    
    Connection con;
    
    String custName;
    String addrLine1;
    String addrLine2;
    long contactNo;
    String assignTo;

    @FXML
    public void initialize() {
        try {
        
        con=DatabaseConnection.getConnection();
        
        orderID.setText(Delivery.getOrderID());
        amount.setText("Rs. "+Delivery.getAmount());
        
        cbAssignTo.setItems(FXCollections.observableArrayList("< None >"));
        cbAssignTo.getSelectionModel().selectFirst();
        
        ObservableList<String> empData = FXCollections.observableArrayList();
        String SQL = "SELECT EMPLOYEE_ID,FIRST_NAME,LAST_NAME FROM EMPLOYEE WHERE JOB_TITLE='Delivery Person'";
        ResultSet rs = con.createStatement().executeQuery(SQL);
        while(rs.next())
            empData.add(new Delivery(rs.getString(1)+" ("+rs.getString(2)+" "+rs.getString(3)+")").getDeliveryEmployee());
        if(rs.isAfterLast())
            cbAssignTo.setItems(empData);
        
        } catch(SQLException se) {
            System.err.println(se);
        }
    }

    public void createDelivery() {
        
        custName = txtCustName.getText();
        addrLine1 = txtAddrLine1.getText();
        addrLine2 = txtAddrLine2.getText();
        contactNo = Long.parseLong(txtContactNo.getText());
        assignTo = cbAssignTo.getSelectionModel().getSelectedItem().toString().substring(0,8);
                
        try {
            
            PreparedStatement delivery = con.prepareStatement("INSERT INTO DELIVERY(ORDER_ID,EMPLOYEE_ID,CUSTOMER_NAME,ADDRESS_LINE_1,ADDRESS_LINE_2,CONTACT_NO) VALUES(?,?,?,?,?,?)");
            delivery.setString(1, Delivery.getOrderID());
            delivery.setString(2, assignTo);
            delivery.setString(3, custName);
            delivery.setString(4, addrLine1);
            delivery.setString(5, addrLine2);
            delivery.setLong(6, contactNo);
            int rowCount=delivery.executeUpdate();
            if(rowCount==1) {
                userMessage.setText("Delivery added for "+Delivery.getOrderID());
                btnAddDelivery.setDisable(true);
            }
                
                    
        } catch(SQLException | NumberFormatException e) {
                userMessage.setText(e.toString());
                System.err.println(e);
        }
        
    }
}


