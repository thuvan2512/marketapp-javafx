/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thunv25.pojo;

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
public class Role {
    private int roleID;
    private String name;
    public static ArrayList<Role> listRole = new ArrayList<>();
    static{
        try {
            getListRole();
        } catch (SQLException ex) {
            Logger.getLogger(Role.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     public static void getListRole() throws SQLException { 
        try (Connection conn = JdbcUtils.getConnection()) {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM role");
            while (rs.next()) {
                int id = rs.getInt("roleID");
                String name = rs.getString("name");
                listRole.add(new Role(id, name));
            }

        }
    }
    public Role() {
    }

    public Role(int roleID, String name) {
        this.roleID = roleID;
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name; //To change body of generated methods, choose Tools | Templates.
    }
   

}
