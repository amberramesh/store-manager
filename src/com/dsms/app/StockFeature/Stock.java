/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dsms.app.StockFeature;

import javafx.beans.property.SimpleStringProperty;


public class Stock {
    public SimpleStringProperty prodName;
    public SimpleStringProperty variant;
    public SimpleStringProperty deptName;
    public SimpleStringProperty qtyAvl;
    public SimpleStringProperty qtyThr;
    public SimpleStringProperty status;

    public Stock(String sProdName, String sVariant, String sDeptName, String sQtyAvl, String sQtyThr, String sStatus) {
        this.prodName = new SimpleStringProperty(sProdName);
        this.variant = new SimpleStringProperty(sVariant);
        this.deptName = new SimpleStringProperty(sDeptName);
        this.qtyAvl = new SimpleStringProperty(sQtyAvl);
        this.qtyThr = new SimpleStringProperty(sQtyThr);
        this.status = new SimpleStringProperty(sStatus);
    }

    public String getProdName() {
        return prodName.get();
    }

    public String getVariant() {
        return variant.get();
    }

    public String getDeptName() {
        return deptName.get();
    }

    public String getQtyAvl() {
        return qtyAvl.get();
    }

    public String getQtyThr() {
        return qtyThr.get();
    }

    public String getStatus() {
        return status.get();
    }

    public void setProdName(String sProdName) {
        prodName.set(sProdName);
    }

    public void setVariant(String sVariant) {
        variant.set(sVariant);
    }

    public void setDeptName(String sDeptName) {
        deptName.set(sDeptName);
    }

    public void setQtyAvl(String sQtyAvl) {
        qtyAvl.set(sQtyAvl);
    }

    public void setQtyThr(String sQtyThr) {
        qtyThr.set(sQtyThr);
    }

    public void setStatus(String sStatus) {
        status.set(sStatus);
    }
    
  
}