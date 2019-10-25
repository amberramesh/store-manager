/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dsms.app.CreateAccountFeature;

import javafx.beans.property.SimpleStringProperty;


public class Account {
    private SimpleStringProperty empIDs;

    public Account(String sEmpIDs) {
        this.empIDs = new SimpleStringProperty(sEmpIDs);
    }


    public String getDptCodes() {
        return empIDs.get();
    }

    public void setFetchedVariants(String sEmpIDs) {
        empIDs.set(sEmpIDs);
    }
     
}
