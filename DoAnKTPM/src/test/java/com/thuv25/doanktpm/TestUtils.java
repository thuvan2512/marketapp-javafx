/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thuv25.doanktpm;

import com.thunv25.utils.Utils;
import java.sql.SQLException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 *
 * @author thu.nv2512
 */
public class TestUtils {
    
    @Test
    @DisplayName("Kiểm tra hàm check số bằng valid value")
    public void checkIsNum() throws SQLException {
        String so = "17.6";
        Assertions.assertTrue(Utils.isNumber(so));
    }
    
    @Test
    @DisplayName("Kiểm tra hàm check số bằng invalid value")
    public void checkIsNotNum() throws SQLException {
        String so = "17.6nk";
        Assertions.assertFalse(Utils.isNumber(so));
    }
}
