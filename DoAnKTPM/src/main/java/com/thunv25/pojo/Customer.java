/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thunv25.pojo;

import com.thunv25.utils.Utils;
import java.util.Date;


/**
 *
 * @author thu.nv2512
 */
public class Customer {
    private String cusID;
    private String name;
    private int gender;
    private String phone;
    private Date dob;

    public Customer(String name, int gender, String phone, Date dob) {
        this.cusID = Utils.getUUID();
        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.dob = dob;
    }

    public Customer(String cusID, String name, int gender, String phone, Date dob) {
        this.cusID = cusID;
        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.dob = dob;
    }

    public Customer() {
    } 
    public String getCusID() {
        return cusID;
    }
    public void setCusID(String cusID) {
        this.cusID = cusID;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getGender() {
        return gender;
    }
    public void setGender(int gender) {
        this.gender = gender;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public Date getDob() {
        return dob;
    }
    public void setDob(Date dob) {
        this.dob = dob;
    }

    @Override
    public String toString() {
        return this.name; //To change body of generated methods, choose Tools | Templates.
    }

}