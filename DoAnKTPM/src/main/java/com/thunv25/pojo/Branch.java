/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thunv25.pojo;

import com.thunv25.utils.Utils;

/**
 *
 * @author thu.nv2512
 */
public class Branch {
    private String branchID;
    private String address;

    public Branch() {
    }
    public Branch(String branchID, String address) {
        this.branchID = branchID;
        this.address = address;
    }
    public Branch( String address) {
        this.branchID = Utils.getUUID();
        this.address = address;
    }
    public String getBranchID() {
        return branchID;
    }
    public void setBranchID(String branchID) {
        this.branchID = branchID;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return this.address; //To change body of generated methods, choose Tools | Templates.
    }

}