package com.thunv25.pojo;

import com.thunv25.utils.Utils;
import java.util.Date;

public class Bill {

    private String id;
    private String cusID;
    private String barnchID;
    private boolean paymentState;
    private double percentDiscount;
    private double totalPrice;
    private Date createdDate;

    public Bill() {
    }

    public Bill(String id, String cusID, String barnchID, boolean paymentState, double percentDiscount, double totalPrice, Date createdDate) {
        this.id = id;
        this.cusID = cusID;
        this.barnchID = barnchID;
        this.paymentState = paymentState;
        this.percentDiscount = percentDiscount;
        this.totalPrice = totalPrice;
        this.createdDate = createdDate;
    }
    
    public Bill(String cusID, String barnchID, boolean paymentState, double percentDiscount) {
        this.id = Utils.getUUID();
        this.cusID = cusID;
        this.barnchID = barnchID;
        this.paymentState = paymentState;
        this.percentDiscount = percentDiscount;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCusID() {
        return cusID;
    }

    public void setCusID(String cusID) {
        this.cusID = cusID;
    }

    public String getBarnchID() {
        return barnchID;
    }

    public void setBarnchID(String barnchID) {
        this.barnchID = barnchID;
    }

    public boolean isPaymentState() {
        return paymentState;
    }

    public void setPaymentState(boolean paymentState) {
        this.paymentState = paymentState;
    }

    public double getPercentDiscount() {
        return percentDiscount;
    }

    public void setPercentDiscount(double percentDiscount) {
        this.percentDiscount = percentDiscount;
    }
    
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    public double getTotalPrice() {
        return totalPrice;
    }
    
    public void setCreatedDate(Date createDate) {
        this.createdDate = createDate;
    }
    
    public Date getCreatedDate() {
        return createdDate;
    }

//    private int billID;
//    private int customerID;
//    private int paymentState;
//    private int branchID;
//    private double percentDiscount;
//
//    public Bill() {
//    }
//
//    public Bill(int billID, int customerID, int paymentState, int branchID, double percentDiscount) {
//        this.billID = billID;
//        this.customerID = customerID;
//        this.paymentState = paymentState;
//        this.branchID = branchID;
//        this.percentDiscount = percentDiscount;
//    }
//
//    public int getBillID() {
//        return billID;
//    }
//
//    public void setBillID(int billID) {
//        this.billID = billID;
//    }
//
//    public int getCustomerID() {
//        return customerID;
//    }
//
//    public void setCustomerID(int customerID) {
//        this.customerID = customerID;
//    }
//
//    public int getPaymentState() {
//        return paymentState;
//    }
//
//    public void setPaymentState(int paymentState) {
//        this.paymentState = paymentState;
//    }
//
//    public int getBranchID() {
//        return branchID;
//    }
//
//    public void setBranchID(int branchID) {
//        this.branchID = branchID;
//    }
//
//    public double getPercentDiscount() {
//        return percentDiscount;
//    }
//
//    public void setPercentDiscount(double percentDiscount) {
//        this.percentDiscount = percentDiscount;
//    }
//
//    @Override
//    public String toString() {
//        return this.billID + "";
//    }
}
