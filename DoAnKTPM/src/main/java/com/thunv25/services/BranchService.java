/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thunv25.services;

import com.thunv25.pojo.Bill;
import com.thunv25.pojo.Branch;
import com.thunv25.pojo.Staff;
import com.thunv25.utils.JdbcUtils;
import com.thunv25.utils.Utils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class BranchService {
    private static ArrayList<Branch> listBranch = new ArrayList<>();
    static {
         try {
             BranchService.getBranchs();
         } catch (SQLException ex) {
             Logger.getLogger(BranchService.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

    public static ArrayList<Branch> getListBranch() {
        return listBranch;
    }
    public static void getBranchs() throws SQLException { 
        BranchService.listBranch = new ArrayList<>();
        try (Connection conn = JdbcUtils.getConnection()) {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM branch");
            while (rs.next()) {
                String id = rs.getString("branchID");
                String address = rs.getString("address");
                listBranch.add(new Branch(id, address));
            }

        }
    }

    public static Branch findBranchByID(String id){
        for (int i = 0; i < listBranch.size(); i++) {
            Branch br = listBranch.get(i);
            if (id.equals(br.getBranchID())) {
                return br;
            }
        }
        return null;
    }
     public static ArrayList<Branch> filterListByKeyword(ArrayList<Branch> list, String key) {
        return (ArrayList<Branch>) list.stream().filter(branch -> branch.getAddress().toLowerCase().contains(key.toLowerCase())).collect(Collectors.toList());
    }
    public static boolean createBranch(String address) throws SQLException {
        try ( Connection conn = JdbcUtils.getConnection()) {
            PreparedStatement stm1 = conn.prepareStatement("INSERT INTO branch (branchID,address) VALUES (?,?);");
            String id = Utils.getUUID();
            stm1.setString(1, id);
            stm1.setString(2, address);
            return stm1.executeUpdate() > 0;
        }
    }
    public static boolean updateBranch(String branchID,String address) throws SQLException {
        try ( Connection conn = JdbcUtils.getConnection()) {
            PreparedStatement stm1 = conn.prepareStatement("UPDATE branch SET address = ? WHERE (branchID = ?);");
            stm1.setString(1, address);
            stm1.setString(2, branchID);
            return stm1.executeUpdate() > 0;
        }
    }
    public static boolean deleteBranch(String branchID) throws SQLException {
        try ( Connection conn = JdbcUtils.getConnection()) {
            
            PreparedStatement stm1 = conn.prepareStatement("DELETE FROM branch WHERE branchID = ?");
            stm1.setString(1, branchID);
            PreparedStatement stm2 = conn.prepareStatement("DELETE FROM staff WHERE branchID=?");
            stm2.setString(1, branchID);
            stm2.executeUpdate();
            PreparedStatement stm4 = conn.prepareStatement("DELETE FROM product_branch WHERE branchID=?");
            stm4.setString(1, branchID);
            stm4.executeUpdate();
            return stm1.executeUpdate() > 0;
        }
    }
}