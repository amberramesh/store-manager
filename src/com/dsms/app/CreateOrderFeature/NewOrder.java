/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dsms.app.CreateOrderFeature;

import javafx.beans.property.SimpleStringProperty;


public class NewOrder {
    public SimpleStringProperty prodSKU;
    public SimpleStringProperty prodName;
    public SimpleStringProperty variant;
    public SimpleStringProperty prodQty;
    public SimpleStringProperty fetchedVariants;
    
    public NewOrder(String sProdSKU,String sProdName,String sVariant,String sProdQty) {
        this.prodSKU = new  SimpleStringProperty(sProdSKU);
        this.prodName = new  SimpleStringProperty(sProdName);
        this.variant = new  SimpleStringProperty(sVariant);
        this.prodQty = new  SimpleStringProperty(sProdQty);
    }

    public NewOrder(String sFetchedVariants) {
        this.fetchedVariants = new SimpleStringProperty(sFetchedVariants);
    }
    
    public String getProdSKU() {
        return prodSKU.get();
    }

    public String getProdName() {
        return prodName.get();
    }

    public String getVariant() {
        return variant.get();
    }
    
    public String getProdQty() {
        return prodQty.get();
    }

    public String getFetchedVariants() {
        return fetchedVariants.get();
    }
    
    public void setProdSKU(String sProdSKU) {
        prodSKU.set(sProdSKU);
    }

    public void setProdName(String sProdName) {
        prodName.set(sProdName);
    }
    
    public void setVariant(String sVariant) {
        variant.set(sVariant);
    }

    public void setProdQty(String sProdQty) {
        prodQty.set(sProdQty);
    }

    public void setFetchedVariants(String sFetchedVariants) {
        fetchedVariants.set(sFetchedVariants);
    }
     
}
