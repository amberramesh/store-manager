/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dsms.app.CustomQueryFeature;

import com.dsms.app.DatabaseConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.util.Callback;

public class CustomQuery {

    @FXML
    TextArea taQueryArea;
    @FXML
    Label outputLabel;
    @FXML
    TableView customTable;
 
    Connection con;
    private ResultSet rs;
    private ObservableList<ObservableList> data;
    
    private String queryArea;

    @FXML
    public void initialize() {
        try {
        
        con=DatabaseConnection.getConnection();
        con.setAutoCommit(false);
        data = FXCollections.observableArrayList();
         
        } catch(SQLException se) {
            System.err.println(se);
        }
    }

    public void queryGo() {
        
        queryArea = taQueryArea.getText();
        try {
            if(!queryArea.contains("select")) {
                con.createStatement().execute(queryArea);
                    outputLabel.setText("Query was executed.");
            } else {
                customTable.getItems().clear();
                customTable.getColumns().clear();
                rs = con.createStatement().executeQuery(queryArea);
                for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++) {
                final int j = i;                
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>() {                    
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {                                                                                              
                        return new SimpleStringProperty(param.getValue().get(j).toString());                        
                    }                    
                });
               
                customTable.getColumns().addAll(col); 
                System.out.println("Column ["+i+"] ");
            }

            while(rs.next()){
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    if(rs.getString(i)!=null)
                        row.add(rs.getString(i));
                    else row.add("NULL");
                }
                System.out.println(row );
                data.add(row);

            }
            customTable.setItems(data);
        }
            
        } catch (SQLException se) {
            outputLabel.setText("An error occurred.");
            System.err.println(se);
        }   
    }
}
