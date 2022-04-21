/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thunv25.services;

import com.thunv25.pojo.Product;
import com.thunv25.pojo.ProductBranch;
import com.thunv25.utils.JdbcUtils;
import java.sql.Connection;
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
public class ProductBranchService {
    private static ArrayList<ProductBranch> listProductBranch = new ArrayList<>();
        static {
        try {
            ProductBranchService.getProductBranch();
        } catch (SQLException ex) {
            Logger.getLogger(ProductBranchService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ArrayList<ProductBranch> getListProductBranch() {
        return listProductBranch;
    }
        
        public static void getProductBranch() throws SQLException {
            listProductBranch = new ArrayList<>();
        try ( Connection conn = JdbcUtils.getConnection()) {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM product_branch");
            while (rs.next()) {
                ProductBranch productBranch = new ProductBranch(rs.getString("productID"), rs.getString("branchID"));
                listProductBranch.add(productBranch);
            }

        }
    }
}
