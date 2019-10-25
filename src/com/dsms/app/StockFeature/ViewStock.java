/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package com.dsms.app.StockFeature;

import com.dsms.app.DatabaseConnection;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ViewStock {

    @FXML
    TableView<Stock> table;
    @FXML
    TableColumn colProdName;
    @FXML
    TableColumn colVariant;
    @FXML
    TableColumn colDeptName;
    @FXML
    TableColumn colQtyAvl;
    @FXML
    TableColumn colQtyThr;
    @FXML
    TableColumn colStatus;

    private ObservableList<Stock> data;
    Connection con;

    @FXML
    public void initialize() {
        try {
        
        con=DatabaseConnection.getConnection();
        
        colProdName.setCellValueFactory(
                new PropertyValueFactory<>("prodName"));
        
        colVariant.setCellValueFactory(
                new PropertyValueFactory<>("variant"));
        
        colDeptName.setCellValueFactory(
                new PropertyValueFactory<>("deptName"));
        
        colQtyAvl.setCellValueFactory(
                new PropertyValueFactory<>("qtyAvl"));
        
        colQtyThr.setCellValueFactory(
                new PropertyValueFactory<>("qtyThr"));
        
        colStatus.setCellValueFactory(
                new PropertyValueFactory<>("status"));
        
        CallableStatement callStock = con.prepareCall("{CALL COMPUTE_PRODUCT_STATUS}");
        callStock.execute();
        
        buildTable();
        } catch(SQLException se) {
            System.out.println("Over here?");
            System.err.println(se);
        }
    }

    public void buildTable() {
        data = FXCollections.observableArrayList();
        try {
            String SQL = "SELECT * FROM V_STOCK";
            ResultSet rs = con.createStatement().executeQuery(SQL);
            while (rs.next()) {
                data.add(new Stock(
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
            System.out.println(se);
        }
    }
}
