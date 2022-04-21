/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thunv25.pojo;

import java.sql.Date;


/**
 *
 * @author thu.nv2512
 */
public class Promotion {
    private String promoID;
    private String productID;
    private Date startDate;
    private Date endDate;
    private double percentDiscount;

    public Promotion() {
    }

    public Promotion(String promoID, String productID, Date startDate, Date endDate, double percentDiscount) {
        this.promoID = promoID;
        this.productID = productID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.percentDiscount = percentDiscount;
    }

    public String getPromoID() {
        return promoID;
    }

    public void setPromoID(String promoID) {
        this.promoID = promoID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public double getPercentDiscount() {
        return percentDiscount;
    }

    public void setPercentDiscount(double percentDiscount) {
        this.percentDiscount = percentDiscount;
    }
    
}
