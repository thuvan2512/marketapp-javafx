/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thunv25.services;

import com.thunv25.pojo.Product;
import com.thunv25.pojo.Staff;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import com.thunv25.utils.JdbcUtils;
import com.thunv25.utils.Utils;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author thu.nv2512
 */
public class StaffServices {

    private static Staff currentUser;
    private static ArrayList<Staff> listStaff = new ArrayList<>();

    static {
        try {
            StaffServices.getStaffs();
        } catch (SQLException ex) {
            Logger.getLogger(StaffServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ArrayList<Staff> getListStaff() {
        return listStaff;
    }

    public static Staff getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(Staff currentUser) {
        StaffServices.currentUser = currentUser;
    }

    public static void getStaffs() throws SQLException {
        listStaff = new ArrayList<>();
        try ( Connection conn = JdbcUtils.getConnection()) {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM staff");
            while (rs.next()) {
                String id = rs.getString("staffID");
                String name = rs.getString("name");
                int gender = rs.getInt("gender");
                String homeTown = rs.getString("homeTown");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String branch = rs.getString("branchID");
                String userName = rs.getString("userName");
                String passWord = rs.getString("passWord");
                int role = rs.getInt("role");
                listStaff.add(new Staff(id, name, gender, homeTown, email, phone, userName, passWord, role, branch));
            }

        }
    }

    public static Staff checkLogin(String username, String password) {
        for (int i = 0; i < StaffServices.listStaff.size(); i++) {
            Staff staff = StaffServices.listStaff.get(i);
            if (staff.getUserName().equals(username.strip()) && staff.getPassWord().equals(password.strip())) {
                return staff;
            }
        }
        return null;
    }

    public static ArrayList<Staff> getListStaffByBranchID(String branchID) {
        ArrayList<Staff> list = new ArrayList<>();
        for (int i = 0; i < StaffServices.listStaff.size(); i++) {
            Staff staff = StaffServices.listStaff.get(i);
            if (staff.getBranch().equals(branchID)) {
                list.add(staff);
            }
        }
        return list;
    }

    public static ArrayList<Staff> filterListByKeyword(ArrayList<Staff> list, String key) {
        return (ArrayList<Staff>) list.stream().filter(staff -> (staff.getName().toLowerCase().contains(key.toLowerCase()) || staff.getHomeTown().toLowerCase().contains(key.toLowerCase()))).collect(Collectors.toList());
    }

    public static boolean createStaff(String name, String phone, String email, String homeTown, String userName, String passWord, int role, int gender, String branch) throws SQLException {
        try ( Connection conn = JdbcUtils.getConnection()) {
            PreparedStatement stm1 = conn.prepareStatement("INSERT INTO staff (staffID, name, gender, homeTown, userName, passWord, phone, email, role, branchID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
            String id = Utils.getUUID();
            stm1.setString(1, id);
            stm1.setString(2, name);
            stm1.setInt(3, gender);
            stm1.setString(4, homeTown);
            stm1.setString(5, userName);
            stm1.setString(6, passWord);
            stm1.setString(7, phone);
            stm1.setString(8, email);
            stm1.setInt(9, role);
            stm1.setString(10, branch);

            return stm1.executeUpdate() > 0;
        }
    }
    public static boolean updateStaff(String name, String phone, String email, String homeTown, String userName, String passWord, int role, int gender, String branch, String staffID) throws SQLException {
        try ( Connection conn = JdbcUtils.getConnection()) {
            PreparedStatement stm1 = conn.prepareStatement("UPDATE staff SET name = ?, gender = ?, homeTown = ?, userName = ?, passWord = ?, phone = ?, email = ?, role =?, branchID = ? WHERE(staffID = ?);");
            stm1.setString(1, name);
            stm1.setInt(2, gender);
            stm1.setString(3, homeTown);
            stm1.setString(4, userName);
            stm1.setString(5, passWord);
            stm1.setString(6, phone);
            stm1.setString(7, email);
            stm1.setInt(8, role);
            stm1.setString(9, branch);
            stm1.setString(10, staffID);

            return stm1.executeUpdate() > 0;
        }
    }

    public static boolean checkNotExistUserName(String username) {
        for (int i = 0; i < listStaff.size(); i++) {
            if (listStaff.get(i).getUserName().equals(username.strip())) {
                return false;
            }
        }
        return true;
    }
    public static boolean checkNotExistUserName(String username, String staffID) {
        for (int i = 0; i < listStaff.size(); i++) {
            if (listStaff.get(i).getUserName().equals(username.strip())) {
                if (listStaff.get(i).getStaffID().equals(staffID) == false) {
                    return false;
                }
            }
        }
        return true;
    }
    public static boolean deleteStaff(String staffID) throws SQLException {
        try ( Connection conn = JdbcUtils.getConnection()) {
            PreparedStatement stm1 = conn.prepareStatement("DELETE FROM staff WHERE staffID=?");
            stm1.setString(1, staffID);
            return stm1.executeUpdate() > 0;
        }
    }
    

}
