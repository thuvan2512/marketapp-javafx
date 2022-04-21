package com.thunv25.services;

import com.thunv25.doanktpm.SaleController;
import com.thunv25.pojo.Customer;
import com.thunv25.pojo.Product;
import com.thunv25.utils.JdbcUtils;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TextField;

public class CustomerService {

    private static ArrayList<Customer> listCustomers = new ArrayList<>();

    public static ArrayList<Customer> getListCustomers() {
        return listCustomers;
    }

    public void getCustomers() {
        try ( Connection conn = JdbcUtils.getConnection()) {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM customer");

            while (rs.next()) {
                Customer customer = new Customer(rs.getString("cusId"), rs.getString("name"), rs.getInt("gender"), rs.getString("phone"), rs.getDate("dob"));
                listCustomers.add(customer);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SaleController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addCustomer(Customer customer) throws SQLException {
        try ( Connection conn = JdbcUtils.getConnection()) {
            PreparedStatement stm2 = conn.prepareStatement("INSERT INTO `customer` (`cusID`, `name`, `gender`, `phone`, `dob`) VALUES(?, ?, ?, ?, ?)");
            stm2.setString(1, customer.getCusID());
            stm2.setString(2, customer.getName());
            stm2.setInt(3, customer.getGender());
            stm2.setString(4, customer.getPhone());
            stm2.setDate(5, (Date) customer.getDob());
            stm2.executeUpdate();
        }
    }

    public boolean isNumberic(String string) {
        if (string == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(string);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

}
