/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dsms.app.LoginFeature;

import com.dsms.app.DatabaseConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Login implements Initializable {
    
    @FXML
    Label lblMessage;
    @FXML
    TextField txtUsername;
    @FXML
    PasswordField txtPassword;
    
    public static User activeUser;
    Connection con;
    
    String username;
    String password;
    String employeeID;
    String firstName;
    String lastName;
    
    int isAdmin;
    int isManager;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
        
        con=DatabaseConnection.getConnection();
        
        } catch(Exception e) {
            System.err.println(e);
        }
    }

    public void loginAuthenticate(ActionEvent ae) {
        
        username = txtUsername.getText();
        password = txtPassword.getText();
        
        try {
            PreparedStatement checkUser = con.prepareStatement("SELECT EMPLOYEE_ID,IS_ADMIN,IS_MANAGER FROM ACCOUNTS WHERE USERNAME=? AND PASSWORD=?");
            checkUser.setString(1,username);
            checkUser.setString(2,password);
            ResultSet rs1 = checkUser.executeQuery();
            if(rs1.next()) {
                employeeID = rs1.getString(1);
                isAdmin = rs1.getInt(2);
                isManager = rs1.getInt(3);
                
                try (PreparedStatement userData = con.prepareStatement("SELECT FIRST_NAME,LAST_NAME FROM EMPLOYEE WHERE EMPLOYEE_ID=?")) {
                    userData.setString(1,employeeID);
                    try (ResultSet rs2 = userData.executeQuery()) {
                        if(rs2.next()) {
                            firstName = rs2.getString(1);
                            lastName = rs2.getString(2);
                            
                            rs2.close();
                            userData.close();
                        }
                    }
                }
                
            rs1.close();
            checkUser.close();
            
            activeUser = new User(employeeID,firstName,lastName,isAdmin,isManager);
            showMenu(ae);
            } else {
                System.out.println("Username or password does not match.");
                lblMessage.setText("Username or password does not match.");
            }
           
            
        } catch(SQLException | IOException e) {
            System.out.println(e);
        }
        
        
    }
    
    void showMenu(ActionEvent ae) throws IOException {
        
        Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
        Scene scene = new Scene(root);
        
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("[DSMS] Main Menu");
        stage.show();
        
        final Node source = (Node) ae.getSource();
        final Stage loginStage = (Stage) source.getScene().getWindow();
        loginStage.close();
        
    }    
}

