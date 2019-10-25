/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dsms.app.AddUpdateProductsFeature;

import javafx.beans.property.SimpleStringProperty;


public class ProductDepartment {
    private SimpleStringProperty dptCodes;

    public ProductDepartment(String sDptCodes) {
        this.dptCodes = new SimpleStringProperty(sDptCodes);
    }


    public String getDptCodes() {
        return dptCodes.get();
    }

    public void setFetchedVariants(String sDptCodes) {
        dptCodes.set(sDptCodes);
    }
     
}
