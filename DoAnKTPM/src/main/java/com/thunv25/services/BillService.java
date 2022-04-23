package com.thunv25.services;

import com.thunv25.pojo.Bill;
import com.thunv25.utils.JdbcUtils;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BillService {
    private static ArrayList<Bill> bills = new ArrayList<>();
    
    static {
        try {
            BillService.getBills();
        } catch (SQLException ex) {
            Logger.getLogger(BillService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static ArrayList<Bill> Bills() {
        return bills;
    }
    
    public static void getBills() throws SQLException {
        try (Connection conn = JdbcUtils.getConnection()) {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM bill");
            while (rs.next()) {
                Bill bill = new Bill(rs.getString("billID"), rs.getString("cusID"), rs.getString("branchID"), rs.getBoolean("paymentState"), rs.getDouble("percentDiscount"), rs.getDouble("totalPrice"), rs.getDate("createdDate"));
                bills.add(bill);
            }
        }
    }
    
    public boolean addBill(Bill bill) throws SQLException {
        try (Connection conn = JdbcUtils.getConnection()) {
            PreparedStatement stm2 = conn.prepareStatement("INSERT INTO `bill` (`billID`, `branchID`, `cusID`, `paymentState`, `percentDiscount`, `totalPrice`, `createdDate`) VALUES(?, ?, ?, ?, ?, ?, ?)");
            stm2.setString(1, bill.getId());
            stm2.setString(2, bill.getBarnchID());
            stm2.setString(3, bill.getCusID());
            stm2.setBoolean(4, bill.isPaymentState());
            stm2.setDouble(5, bill.getPercentDiscount());
            stm2.setDouble(6, bill.getTotalPrice());
            stm2.setDate(7, (Date) bill.getCreatedDate());
            stm2.executeUpdate();
            return true;
        }
    }
}
