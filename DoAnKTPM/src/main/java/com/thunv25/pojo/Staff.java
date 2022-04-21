package com.thunv25.pojo;

import com.thunv25.utils.Utils;

/**
 *
 * @author thu.nv2512
 */
public class Staff {
    private String staffID;
    private String name;
    private int gender;
    private String homeTown;
    private String email;
    private String phone;
    private String userName;
    private String passWord;
    private int role;
    private String branch;

    public Staff(String staffID, String name, int gender, String homeTown, String email, String phone, String userName, String passWord, int role, String branch) {
        this.staffID = staffID;
        this.name = name;
        this.gender = gender;
        this.homeTown = homeTown;
        this.email = email;
        this.phone = phone;
        this.userName = userName;
        this.passWord = passWord;
        this.role = role;
        this.branch = branch;
    }

    public Staff( String name, int gender, String homeTown, String email, String phone, String userName, String passWord, int role, String branch) {
        this.staffID = Utils.getUUID();
        this.name = name;
        this.gender = gender;
        this.homeTown = homeTown;
        this.email = email;
        this.phone = phone;
        this.userName = userName;
        this.passWord = passWord;
        this.role = role;
        this.branch = branch;
    }

    public Staff() {
    }
    
    public String getBranch() {
        return branch;
    }
    public void setBranch(String branch) {
        this.branch = branch;
    }
    public String getStaffID() {
        return staffID;
    }
    public void setStaffID(String staffID) {
        this.staffID = staffID;
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
    public String getHomeTown() {
        return homeTown;
    }
    public void setHomeTown(String homeTown) {
        this.homeTown = homeTown;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassWord() {
        return passWord;
    }
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
    public int getRole() {
        return role;
    }
    public void setRole(int role) {
        this.role = role;
    }


    @Override
    public String toString() {
        return this.name; //To change body of generated methods, choose Tools | Templates.
    }
       
    
}