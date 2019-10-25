package com.dsms.app;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DSMS extends Application {
    
    @Override
    public void init() {
        
        // Loading database drivers and establishing connectivity     
        try { 
            DatabaseConnection.setupConnection(); 
            System.out.println("Connection established.");
        } catch(ClassNotFoundException | SQLException e) {
            System.err.println(e);
        }
        
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("[DSMS] Login");
        stage.show();
    }

    @Override
    public void stop() {
        
        // Terminating database connectivity       
       try { 
            DatabaseConnection.dropConnection();
            System.out.println("Connection terminated.");
        }
        catch(SQLException se) {
            System.err.println(se);
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }   
}
