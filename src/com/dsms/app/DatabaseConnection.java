/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dsms.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseConnection {
    
    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String DB_USER = "bg12cs011";
    private static final String DB_PASSWORD = "bg12cs011";
    public static Connection con;
    
    public static void setupConnection() throws ClassNotFoundException,SQLException { 

        Class.forName("oracle.jdbc.OracleDriver");
        con=DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
        
    }
    public static Connection getConnection() {
        
       return con;
       
    }
    public static void dropConnection() throws SQLException {
        
        con.close();
        
    }
}