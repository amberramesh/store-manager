package com.dsms.app.MenuFeature;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.dsms.app.LoginFeature.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;


public class Menu {
    
    @FXML
    private Label menuGreeting;
    @FXML
    private Group mgrGroup;
    @FXML
    private Group adminGroup;
    
    @FXML
    public void initialize() {
        String name = User.getFirstName();
        menuGreeting.setText("Welcome, "+name+"!");
        
        int isAdmin = User.getIsAdmin();
        int isManager = User.getIsManager();
        
        if(isAdmin==1)
            adminGroup.setDisable(false);
        if(isManager==1)
            mgrGroup.setDisable(false);
        
    }       
    
    @FXML
    private void makeNewOrder() throws Exception {
        
        Parent root = FXMLLoader.load(getClass().getResource("NewOrder.fxml"));
        Scene scene = new Scene(root);
        
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("[DSMS] Make New Order");
        stage.show();
        
    }
    
    @FXML
    private void viewOrders() throws Exception {
        
        Parent root = FXMLLoader.load(getClass().getResource("ViewOrders.fxml"));
        Scene scene = new Scene(root);
        
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("[DSMS] View Orders");
        stage.show();
    }
    
    @FXML
    private void viewProducts() throws Exception {
        
        Parent root = FXMLLoader.load(getClass().getResource("ViewProducts.fxml"));
        Scene scene = new Scene(root);
        
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("[DSMS] View Products");
        stage.show();
    }
    
    @FXML
    private void addUpdateProducts() throws Exception {
        
        Parent root = FXMLLoader.load(getClass().getResource("AddUpdateProducts.fxml"));
        Scene scene = new Scene(root);
        
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("[DSMS] Add/Update Products");
        stage.show();
    }
    
    @FXML
    private void viewStock() throws Exception {
        
        Parent root = FXMLLoader.load(getClass().getResource("ViewStock.fxml"));
        Scene scene = new Scene(root);
        
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("[DSMS] View Stock");
        stage.show();
    }
    
    @FXML
    private void createAccount() throws Exception {
        
        Parent root = FXMLLoader.load(getClass().getResource("CreateAccount.fxml"));
        Scene scene = new Scene(root);
        
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("[DSMS] Create Account");
        stage.show();
    }
    
    @FXML
    private void customQuery() throws Exception {
        
        Parent root = FXMLLoader.load(getClass().getResource("CustomQuery.fxml"));
        Scene scene = new Scene(root);
        
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("[DSMS] Custom Query");
        stage.show();
    }
    
}