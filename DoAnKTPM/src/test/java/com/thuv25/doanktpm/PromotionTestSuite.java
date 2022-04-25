/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thuv25.doanktpm;

import com.thunv25.pojo.Promotion;
import com.thunv25.pojo.Staff;
import com.thunv25.services.PromotionService;
import java.sql.Date;
import java.sql.SQLException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 *
 * @author thu.nv2512
 */
public class PromotionTestSuite {

    @Test
    @DisplayName("Kiểm tra thêm khuyến mãi thành công")
    public void addSuccess() throws SQLException {
        String productID = "2a02ee39-2aff-4ee4-8dc3-e915287e1f19";
        Date date1 = new Date(2021, 5, 10);
        Date date2 = new Date(2021, 5, 15);
        Assertions.assertTrue(PromotionService.createPromotion(productID, date1, date2, 0));
        PromotionService.getPromotion();
        boolean flag = false;
        for (Promotion pd : PromotionService.getListPromotions()) {
            if (pd.getProductID().equals(productID)) {
                if (pd.getEndDate().equals(date2) && pd.getStartDate().equals(date1)) {
                    flag = true;
                    break;
                }
            }
        }
        Assertions.assertTrue(flag);
    }

    @Test
    @DisplayName("Kiểm tra khuyến mãi phẩm khỏi hệ thống thành công")
    public void deleteSuccess() throws SQLException {
        String promoID = "2d6e2697-8b05-4161-8ba7-76482d1c6ea9";
        Assertions.assertTrue(PromotionService.deletePromotion(promoID));
        PromotionService.getPromotion();
        boolean flag = true;
        for (int i = 0; i < PromotionService.getListPromotions().size(); i++) {
            if (PromotionService.getListPromotions().get(i).getPromoID().equals(promoID)) {
                flag = false;
                break;
            }
        }
        Assertions.assertTrue(flag);
    }

    @Test
    @DisplayName("Kiểm tra khuyến mãi phẩm khỏi hệ thống thành công")
    public void deleteFail() throws SQLException {
        String promoID = "sđa2d6e2697-8b05-4161-8ba7-76482d1c6ea9";
        Assertions.assertFalse(PromotionService.deletePromotion(promoID));
    }
}
