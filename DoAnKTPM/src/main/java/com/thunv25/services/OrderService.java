package com.thunv25.services;

import com.thunv25.pojo.Order;
import com.thunv25.utils.JdbcUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderService {
    public boolean addOrder(Order order) throws SQLException {
        try (Connection conn = JdbcUtils.getConnection()) {
            conn.setAutoCommit(false);
            PreparedStatement stm1 = conn.prepareStatement("INSERT INTO `order` (`orderID`, `quantity`, `billID`, `productID`) VALUES(?, ?, ?, ?)");
            stm1.setString(1, order.getOrderID());
            stm1.setDouble(2, order.getQuantity());
            stm1.setString(3, order.getBillID());
            stm1.setString(4, order.getProductID());
            stm1.executeUpdate();
            
            conn.commit();
            return true;
        }
    }
}
