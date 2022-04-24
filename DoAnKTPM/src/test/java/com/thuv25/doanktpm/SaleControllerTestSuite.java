package com.thuv25.doanktpm;

import com.thunv25.pojo.Bill;
import com.thunv25.pojo.Customer;
import com.thunv25.pojo.Order;
import com.thunv25.pojo.Product;
import com.thunv25.pojo.Staff;
import com.thunv25.services.BillService;
import com.thunv25.services.CustomerService;
import com.thunv25.services.OrderService;
import com.thunv25.services.ProductService;
import com.thunv25.services.StaffServices;
import com.thunv25.utils.JdbcUtils;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class SaleControllerTestSuite {
//    private static Connection conn;
    private static ProductService productService;
    
    @BeforeAll
    public static void BeforeAll() throws SQLException {
//        conn = JdbcUtils.getConnection();
        System.out.println("Hello World!");
    }
    
    @Test
    public void isNumberic() {
        String s = "huynh123";
        boolean expected = false;
        ProductService productService = new ProductService();
        boolean actual = productService.isNumberic(s);
        
        Assertions.assertEquals(expected, actual);
    }
    
    @Test
    public void testNullProduct() {
        ArrayList<Product> product = ProductService.getListProducts();
        product.forEach(p -> Assertions.assertNotNull(p.getName()));
    }
    
    @Test
    public void textAddBill() throws SQLException {
        LocalDate currentDate = LocalDate.now();
        Bill bill = new Bill(UUID.randomUUID().toString(), "A", "B", true, 0.1, 150, java.sql.Date.valueOf(currentDate));
        BillService billService = new BillService();
        Assertions.assertTrue(billService.addBill(bill));
        
    }
    
    @Test
    public void testAddOrderFalse() throws SQLException {
        OrderService orderService = new OrderService();
        Order order = new Order(UUID.randomUUID().toString(), 4, UUID.randomUUID().toString(), UUID.randomUUID().toString());
        Assertions.assertFalse(orderService.addOrder(order));
    }
    
    @Test
    public void textAddCustomer() throws SQLException {
        LocalDate currentDate = LocalDate.now();
        CustomerService customerService = new CustomerService();
        Customer customer = new Customer(UUID.randomUUID().toString(), "Huynh", 1, "0989915415", java.sql.Date.valueOf(currentDate));
        Assertions.assertTrue(customerService.addCustomer(customer));
        
        
    }
    
    @Test 
    public void textSamePhoneNumber() {
        CustomerService customerService = new CustomerService();
        boolean flag = customerService.checkPhone("0989915415");
//        customerService.checkPhone("0989915415");
        Assertions.assertFalse(flag);
    }
}
