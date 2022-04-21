/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thunv25.services;

import com.thunv25.pojo.Bill;
import com.thunv25.pojo.Promotion;
import com.thunv25.utils.JdbcUtils;
import com.thunv25.utils.Utils;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author thu.nv2512
 */
public class PromotionService {
    private static ArrayList<Promotion> listPromo = new ArrayList<>();
    static{
        try {
            PromotionService.getPromo();
        } catch (SQLException ex) {
            Logger.getLogger(PromotionService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static ArrayList<Promotion> getListPromo() {
        return listPromo;
    }
    public static void getPromo() throws SQLException {
        listPromo = new ArrayList<>();
        try (Connection conn = JdbcUtils.getConnection()) {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM promotion");
            while (rs.next()) {
                Promotion promotion = new Promotion(rs.getString("promoID"), rs.getString("productID"), rs.getDate("startDate"), rs.getDate("endDate"), rs.getDouble("pecentDiscount"));
                listPromo.add(promotion);
            }
        }
    }
    public static boolean checkValidToCreate(String proID,Date startDate){
        for (int i = 0; i < listPromo.size(); i++) {
            if (listPromo.get(i).getProductID().equals(proID)) {
                if (startDate.before(listPromo.get(i).getEndDate()) || startDate.equals(listPromo.get(i).getEndDate())) {
                    return false;
                }
            }
        }
        return true;
    }
    public static boolean createPromotion(String productID, Date startDate, Date endDate, double percent) throws SQLException {
        try ( Connection conn = JdbcUtils.getConnection()) {
            PreparedStatement stm1 = conn.prepareStatement("INSERT INTO promotion (promoID, productID, startDate, endDate, pecentDiscount) VALUES (?,? , ?, ?, ?);");
            String id = Utils.getUUID();
            stm1.setString(1, id);
            stm1.setString(2, productID);
            stm1.setDate(3, startDate);
            stm1.setDate(4, endDate);
            stm1.setDouble(5, percent);
            return stm1.executeUpdate() > 0;
        }
    }
    public static boolean deletePromotion(String promoID) throws SQLException {
        try ( Connection conn = JdbcUtils.getConnection()) {
            PreparedStatement stm1 = conn.prepareStatement("DELETE FROM promotion WHERE promoID = ? ");
            stm1.setString(1, promoID);
            return stm1.executeUpdate() > 0;
        }
    }
}
