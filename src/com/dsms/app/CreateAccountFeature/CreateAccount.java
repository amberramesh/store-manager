/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dsms.app.CreateAccountFeature;

import com.dsms.app.DatabaseConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class CreateAccount {

    @FXML
    TextField txtUsername;
    @FXML
    PasswordField txtPassword;
    @FXML
    PasswordField txtConfirmPassword;
    @FXML
    ChoiceBox cbEmployeeID;
    @FXML
    CheckBox cbIsManager;
    @FXML
    CheckBox cbIsAdmin;
    @FXML
    Label userMessage;
    @FXML
    Button btnCreateUser;
    
    Connection con;
    
    String username;
    String password;
    String confirmPassword;
    String employeeID;
    int isManager;
    int isAdmin;
    boolean isValid;
    boolean isExists;

    @FXML
    public void initialize() {
        try {
        
        con=DatabaseConnection.getConnection();
        
        cbEmployeeID.setItems(FXCollections.observableArrayList("< None >"));
        cbEmployeeID.getSelectionModel().selectFirst();
        
        ObservableList<String> empData = FXCollections.observableArrayList();
        String SQL = "SELECT EMPLOYEE_ID,FIRST_NAME,LAST_NAME FROM EMPLOYEE WHERE EMPLOYEE_ID NOT IN (SELECT EMPLOYEE_ID FROM ACCOUNTS)";
        ResultSet rs = con.createStatement().executeQuery(SQL);
        while(rs.next())
            empData.add(new Account(rs.getString(1)+" ("+rs.getString(2)+" "+rs.getString(3)+")").getDptCodes());
        if(rs.isAfterLast())
            cbEmployeeID.setItems(empData);
        
        } catch(SQLException se) {
            System.out.println("Over here?");
            System.err.println(se);
        }
    }

    public void userAuthenticate() {
        
        username = txtUsername.getText();
        password = txtPassword.getText();
        confirmPassword = txtConfirmPassword.getText();
        employeeID = cbEmployeeID.getSelectionModel().getSelectedItem().toString().substring(0,8);
                
        if(cbIsManager.isSelected())
            isManager=1;
        else isManager=0;
        if(cbIsAdmin.isSelected())
            isAdmin=1;
        else isAdmin=0;
        
        isValid = true;
        
        if(!password.equals(confirmPassword)) {
            isValid = false;
            userMessage.setText("Passwords do not match.");
        }     
        
        if(isValid) {
            try {
                isExists = false;
                ResultSet rs = con.createStatement().executeQuery("SELECT EMPLOYEE_ID FROM EMPLOYEE");
                while(rs.next()) {
                    if(employeeID.equals(rs.getString(1)))
                        isExists = true;
                    System.out.println(rs.getString(1));
                }
                if(isExists) {
                    con.createStatement().executeUpdate("INSERT INTO ACCOUNTS VALUES('"+username+"','"+password+"','"+employeeID+"',"+isAdmin+","+isManager+")");
                    userMessage.setText("User Created.");
                    btnCreateUser.setDisable(true);
                } else userMessage.setText("Employee does not exist.");
                    
            } catch(SQLException se) {
                userMessage.setText(se.toString());
                System.err.println(se);
            }
        
        }
    }
}
