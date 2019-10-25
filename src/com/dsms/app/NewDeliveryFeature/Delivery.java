/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dsms.app.NewDeliveryFeature;

import javafx.beans.property.SimpleStringProperty;

public class Delivery {
    private static String orderID;
    private static int amount;
    private SimpleStringProperty deliveryEmployee;
    
    public Delivery(String sDeliveryEmployee) {
        this.deliveryEmployee = new SimpleStringProperty(sDeliveryEmployee);
    }

    public static String getOrderID() {
        return orderID;
    }

    public String getDeliveryEmployee() {
        return deliveryEmployee.get();
    }

    public void setDeliveryEmployee(String sDeliveryEmployee) {
        deliveryEmployee.set(sDeliveryEmployee);
    }

    public static void setOrderID(String orderID) {
        Delivery.orderID = orderID;
    }

    public static int getAmount() {
        return amount;
    }

    public static void setAmount(int amount) {
        Delivery.amount = amount;
    }

}
