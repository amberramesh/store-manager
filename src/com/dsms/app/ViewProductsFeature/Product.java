/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dsms.app.ViewProductsFeature;

import javafx.beans.property.SimpleStringProperty;


public class Product {
    public SimpleStringProperty prodSKU;
    public SimpleStringProperty prodName;
    public SimpleStringProperty variant;
    public SimpleStringProperty qtyInStock;
    public SimpleStringProperty deptName;
    public SimpleStringProperty prodType;

    public Product(String sProdSKU, String sProdName, String sVariant, String sQtyInStock, String sDeptName, String sProdType) {
        this.prodSKU = new SimpleStringProperty(sProdSKU);
        this.prodName = new SimpleStringProperty(sProdName);
        this.variant = new SimpleStringProperty(sVariant);
        this.deptName = new SimpleStringProperty(sDeptName);
        this.qtyInStock = new SimpleStringProperty(sQtyInStock);
        this.prodType = new SimpleStringProperty(sProdType);
    }
    
    public Product(String sProdSKU, String sProdName, String sQtyInStock, String sDeptName, String sProdType) {
        this.prodSKU = new SimpleStringProperty(sProdSKU);
        this.prodName = new SimpleStringProperty(sProdName);
        this.qtyInStock = new SimpleStringProperty(sQtyInStock);
        this.deptName = new SimpleStringProperty(sDeptName);
        this.prodType = new SimpleStringProperty(sProdType);
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

    public String getQtyInStock() {
        return qtyInStock.get();
    }
    
    public String getDeptName() {
        return deptName.get();
    }

    public String getProdType() {
        return prodType.get();
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
    
    public void setQtyInStock(String sQtyInStock) {
        qtyInStock.set(sQtyInStock);
    }

    public void setDeptName(String sDeptName) {
        deptName.set(sDeptName);
    }

    public void setProductType(String sProductType) {
        prodType.set(sProductType);
    }
    
  
}