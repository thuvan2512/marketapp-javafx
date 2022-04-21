/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thunv25.pojo;

/**
 *
 * @author thu.nv2512
 */
public class ProductBranch {
    private String productID;
    private String branchID;

    public ProductBranch() {
    }
    public ProductBranch(String productID, String branchID) {
        this.productID = productID;
        this.branchID = branchID;
    }

    public String getBranchID() {
        return branchID;
    }

    public String getProductID() {
        return productID;
    }

    public void setBranchID(String branchID) {
        this.branchID = branchID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }
    
}
