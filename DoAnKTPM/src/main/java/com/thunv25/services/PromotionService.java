/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thunv25.services;

import com.thunv25.doanktpm.SaleController;
import com.thunv25.pojo.Bill;
import com.thunv25.pojo.Product;
import com.thunv25.pojo.Promotion;
import com.thunv25.utils.JdbcUtils;
import com.thunv25.utils.Utils;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TableView;

/**
 *
 * @author thu.nv2512
 */
public class PromotionService {
    private static ArrayList<Promotion> listPromotions = new ArrayList<>();
    static{
        try {
            PromotionService.getPromotion();
        } catch (SQLException ex) {
            Logger.getLogger(PromotionService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static ArrayList<Promotion> getListPromotions() {
        return listPromotions;
    }
    public static void getPromotion() throws SQLException {
        listPromotions = new ArrayList<>();
        try (Connection conn = JdbcUtils.getConnection()) {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM promotion");
            while (rs.next()) {
                Promotion promotion = new Promotion(rs.getString("promoID"), rs.getString("productID"), rs.getDate("startDate"), rs.getDate("endDate"), rs.getDouble("pecentDiscount"));
                listPromotions.add(promotion);
            }
        }
    }
    public static boolean checkValidToCreate(String proID,Date startDate){
        for (int i = 0; i < listPromotions.size(); i++) {
            if (listPromotions.get(i).getProductID().equals(proID)) {
                if (startDate.before(listPromotions.get(i).getEndDate()) || startDate.equals(listPromotions.get(i).getEndDate())) {
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
    
    public void checkProductDiscount(TableView<Product> tbView) {
        LocalDate currentDate = LocalDate.now();
        double discount2 = 0;
        boolean flag = false;
        List<Product> arrayList = new ArrayList<>();
        Product product = new Product();
        //Get data from tableview
        for (int i = 0; i < tbView.getItems().size(); i++) {
            product = tbView.getItems().get(i);
            arrayList.add(product);
        }

        for (int i = 0; i < arrayList.size(); i++) {
            for (int j = 0; j < PromotionService.getListPromotions().size(); j++) {
                if (PromotionService.getListPromotions().get(j).getProductID().equals(arrayList.get(i).getId())
                        && (PromotionService.getListPromotions().get(j).getStartDate().equals(java.sql.Date.valueOf(currentDate))
                        || PromotionService.getListPromotions().get(j).getStartDate().before(java.sql.Date.valueOf(currentDate)))
                        && (PromotionService.getListPromotions().get(j).getEndDate().after(java.sql.Date.valueOf(currentDate))
                        || PromotionService.getListPromotions().get(j).getEndDate().equals(java.sql.Date.valueOf(currentDate)))) {
                    discount2 = PromotionService.getListPromotions().get(j).getPercentDiscount();
//                    System.out.println(arrayList.get(i).getPrice());
//                    System.out.println(ProductService.getListProducts().get(i).getPrice());
                    for (int h = 0; h < ProductService.getListProducts().size(); h++) {
                        if (arrayList.get(i).getPrice() == ProductService.getListProducts().get(h).getPrice()) {
                            double temp2 = arrayList.get(i).getPrice();
                            arrayList.get(i).setPrice(temp2 - (temp2 * discount2));
                            tbView.refresh();
                        }
                    }

                }
            }
        }
        SaleController.discount2 = discount2;
    }
    public static boolean deletePromotion(String promoID) throws SQLException {
        try ( Connection conn = JdbcUtils.getConnection()) {
            PreparedStatement stm1 = conn.prepareStatement("DELETE FROM promotion WHERE promoID = ? ");
            stm1.setString(1, promoID);
            return stm1.executeUpdate() > 0;
        }
    }
}
