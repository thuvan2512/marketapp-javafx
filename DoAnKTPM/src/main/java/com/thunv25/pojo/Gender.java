/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thunv25.pojo;

import static com.thunv25.pojo.Role.listRole;
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
public class Gender {

    private int genderID;
    private String name;
    public static ArrayList<Gender> listGender = new ArrayList<>();

    static {
        try {
            getListGender();
        } catch (SQLException ex) {
            Logger.getLogger(Gender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void getListGender() throws SQLException {
        try ( Connection conn = JdbcUtils.getConnection()) {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM gender");
            while (rs.next()) {
                int id = rs.getInt("genderID");
                String name = rs.getString("name");
                listGender.add(new Gender(id, name));
            }

        }
    }

    public Gender(int genderID, String name) {
        this.genderID = genderID;
        this.name = name;
    }

    public Gender() {
    }
    
    public int getGenderID() {
        return genderID;
    }
    
    public void setGenderID() {
        this.genderID = genderID;
    }
    
    public String getName() {
        return name;
    }
    
    public void setGenderName() {
        this.name = name;
    }
    @Override
    public String toString() {
        return this.name; //To change body of generated methods, choose Tools | Templates.
    }

}
