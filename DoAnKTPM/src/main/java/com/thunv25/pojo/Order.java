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
public class Order {
    private String orderID;
    private double quantity;
    private String billID;
    private String productID;

    public Order() {
    }
    public Order(double quantity, String billID, String productID) {
        this.orderID = Utils.getUUID();
        this.quantity = quantity;
        this.billID = billID;
        this.productID = productID;
    }

    public Order(String orderID, double quantity, String billID, String productID) {
        this.orderID = orderID;
        this.quantity = quantity;
        this.billID = billID;
        this.productID = productID;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }


    public double getQuantity() {
        return quantity;
    }
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getBillID() {
        return billID;
    }

    public void setBillID(String billID) {
        this.billID = billID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }


    @Override
    public String toString() {
        return "order"; //To change body of generated methods, choose Tools | Templates.
    }



}