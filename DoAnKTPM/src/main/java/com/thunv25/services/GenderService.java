package com.thunv25.services;

import com.thunv25.doanktpm.AddCustomerController;
import com.thunv25.doanktpm.SaleController;
import com.thunv25.pojo.Customer;
import com.thunv25.pojo.Gender;
import com.thunv25.utils.JdbcUtils;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GenderService {
    private static ArrayList<Gender> listGenders = new ArrayList<>();
    
    static {
        try {
            GenderService.getGenders();
        } catch (SQLException ex) {
            Logger.getLogger(GenderService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static ArrayList<Gender> getListGenders() {
        return listGenders;
    }
    
    
    public static void getGenders() throws SQLException {
        try ( Connection conn = JdbcUtils.getConnection()) {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM gender");

            while (rs.next()) {
                Gender gender = new Gender(rs.getInt("genderID"), rs.getString("name"));
                listGenders.add(gender);
            }
        } 
    }
}
