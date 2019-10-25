/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dsms.app.ViewOrdersFeature;

import java.sql.Timestamp;
import javafx.beans.property.SimpleStringProperty;


public class Order {
    public SimpleStringProperty orderID;
    public SimpleStringProperty orderTimeStamp;
    public SimpleStringProperty purchaseAmt;

    public Order(String sOrderID,Timestamp TSOrderTimeStamp,Integer numPurchaseAmt) {
        this.orderID = new  SimpleStringProperty(sOrderID);
        this.orderTimeStamp = new  SimpleStringProperty(TSOrderTimeStamp.toString().substring(0, 19));
        this.purchaseAmt= new  SimpleStringProperty(numPurchaseAmt.toString());
    }
    
    public String getOrderID() {
        return orderID.get();
    }

    public String getOrderTimeStamp() {
        return orderTimeStamp.get();
    }

    public String getPurchaseAmt() {
        return purchaseAmt.get();
    }
    
    public void setOrderID(String sOrderID) {
        orderID.set(sOrderID);
    }

    public void setProdName(Timestamp TSOrderTimeStamp) {
        orderTimeStamp.set(TSOrderTimeStamp.toString());
    }

    public void setPurchaseAmt(Integer numPurchaseAmt) {
        purchaseAmt.set(numPurchaseAmt.toString());
    }
    
}
