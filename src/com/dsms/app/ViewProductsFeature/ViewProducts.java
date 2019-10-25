/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package com.dsms.app.ViewProductsFeature;

import com.dsms.app.DatabaseConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ViewProducts {

    @FXML
    TableView<Product> table;
    @FXML
    TableColumn colSKU;
    @FXML
    TableColumn colProdName;
    @FXML
    TableColumn colVariant;
    @FXML
    TableColumn colQtyInStock;
    @FXML
    TableColumn colDepartment;
    @FXML
    TableColumn colProductType;

    private ObservableList<Product> data;
    Connection con;

    @FXML
    public void initialize() {
        try {
        
        con=DatabaseConnection.getConnection();
        
        colSKU.setCellValueFactory(
                new PropertyValueFactory<>("prodSKU"));
        
        colProdName.setCellValueFactory(
                new PropertyValueFactory<>("prodName"));
        
        colVariant.setCellValueFactory(
                new PropertyValueFactory<>("variant"));
        
        colDepartment.setCellValueFactory(
                new PropertyValueFactory<>("deptName"));
        
        colQtyInStock.setCellValueFactory(
                new PropertyValueFactory<>("qtyInStock"));
        
        colProductType.setCellValueFactory(
                new PropertyValueFactory<>("prodType"));
        showVariantsTable();
        } catch(Exception e) {
            System.out.println("Over here?");
            System.err.println(e);
        }
    }

    public void showVariantsTable() {
        colVariant.setVisible(true);
        data = FXCollections.observableArrayList();
        try {
            String SQL = "SELECT * FROM V_PRODUCTS";
            ResultSet rs = con.createStatement().executeQuery(SQL);
            while (rs.next()) {
                data.add(new Product(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6)
                ));
            }
            table.setItems(data);
        } catch (SQLException se) {
            System.err.println(se);
        }   
    }
    
       public void hideVariantsTable() {
        colVariant.setVisible(false);
        data = FXCollections.observableArrayList();
        try {
            String SQL = "SELECT * FROM V_PRODUCTS_WO_VARIANT";
            ResultSet rs = con.createStatement().executeQuery(SQL);
            while (rs.next()) {
                data.add(new Product(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5)
                ));
            }
            table.setItems(data);
        } catch (SQLException se) {
            System.err.println(se);
        }
    }
}
