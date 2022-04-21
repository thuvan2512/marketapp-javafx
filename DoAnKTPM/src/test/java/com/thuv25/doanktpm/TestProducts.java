package com.thuv25.doanktpm;

import com.thunv25.services.ProductService;
import com.thunv25.utils.JdbcUtils;
import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class TestProducts {
//    private static Connection conn;
//    private static ProductService productService;
    
    @BeforeAll
    public static void BeforeAll() throws SQLException {
//        conn = JdbcUtils.getConnection();
        System.out.println("Hello World!");
    }
    
    @Test
    public void Testing() {
        String s = "huynh123";
        boolean expected = true;
        ProductService productService = new ProductService();
        boolean actual = productService.isNumberic(s);
        
        Assertions.assertEquals(expected, actual);
    }
    
}
