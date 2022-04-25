package com.thuv25.doanktpm;

import com.thunv25.pojo.Product;
import com.thunv25.pojo.ProductBranch;
import com.thunv25.services.ProductBranchService;
import com.thunv25.services.ProductService;
import com.thunv25.utils.JdbcUtils;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

public class ProductTestSuite {
//    private static Connection conn;
//    @BeforeAll
//    public static void beforeAll() {
//        try {
//            conn = JdbcUtils.getConnection();
//        } catch (SQLException ex) {
//            Logger.getLogger(ProductTestSuite.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//    @AfterAll
//    public static void afterAll() {
//        if (conn != null)
//        try {
//            conn.close();
//        } catch (SQLException ex) {
//            Logger.getLogger(ProductTestSuite.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//    
//    

    @Test
    @DisplayName("Kiểm tra tìm kiếm thành công")
    public void testSearchSuccessful() throws SQLException {
        String kw = "kg";
        ArrayList<Product> listSearchProduct = ProductService.filterListByKeyword(ProductService.getListProducts(), kw);

        for (Product p : listSearchProduct) {
            Assertions.assertTrue(p.getName().toLowerCase().contains(kw.toLowerCase()));
        }
    }

    @Test
    @DisplayName("Kiểm tra tìm kiếm thất bại")
    public void testSearchFail() throws SQLException {
        String kw = "fhghjh";
        ArrayList<Product> listSearchProduct = ProductService.filterListByKeyword(ProductService.getListProducts(), kw);

        for (Product p : listSearchProduct) {
            Assertions.assertFalse(p.getName().toLowerCase().contains(kw.toLowerCase()));
        }
    }

    @Test
    @DisplayName("Kiểm tra xóa sản phẩm khỏi hệ thống thất bại")
    public void deleteProductFail() throws SQLException {
        String productID = "5679662d-cad8-43c5-8c0a-34562fa36475";
        Assertions.assertFalse(ProductService.deleteProduct(productID));
        ProductService.getProducts();
    }

    @Test
    @DisplayName("Kiểm tra xóa sản phẩm khỏi chi nhánh thất bại")
    public void deleteProductBranchFail() throws SQLException {
        String productID = "499999670eb24-3985-40f9-bab6-a86de52a5c34";
        String branchID = "hsdojdsjs1dfd7ce7-4c4d-467b-9824-478f805004f2";
        Assertions.assertFalse(ProductService.deleteProductBranch(productID, branchID));
    }

    @Test
    @DisplayName("Kiểm tra xóa sản phẩm khỏi hệ thống thành công")
    public void deleteProductSuccess() throws SQLException {
        String productID = "4670eb24-3985-40f9-bab6-a86de52a5c34";
        Assertions.assertTrue(ProductService.deleteProduct(productID));
        ProductService.getProducts();
        Assertions.assertNull(ProductService.findProductByID(productID));
    }

    @Test
    @DisplayName("Kiểm tra xóa sản phẩm khỏi chi nhánh thành công")
    public void deleteProductBranchSuccess() throws SQLException {
        String productID = "1689662d-cad8-43c5-8c0a-34562fa36475";
        String branchID = "115dd543-06f0-417e-941a-1ec75fde5f64";
        Assertions.assertTrue(ProductService.deleteProductBranch(productID,branchID));
        ProductService.getProducts();
        ProductBranchService.getProductBranch();
        Assertions.assertNotNull(ProductService.findProductByID(productID));
        for (int i = 0; i < ProductBranchService.getListProductBranch().size(); i++) {
            ProductBranch pb = ProductBranchService.getListProductBranch().get(i);
            Assertions.assertNotEquals(pb.getBranchID() + pb.getProductID(),branchID + productID);
        }
    }

    @Test
    @DisplayName("Kiểm tra thêm thành công")
    public void addProductBranchSuccess() throws SQLException {
        String branchID = "gdfb6f1b-c178-4e3f-a8a6-73c85d266d91";
        String spName = "SP test";
        Assertions.assertTrue(ProductService.createNewProduct(spName,"Test" , 0, branchID));
        ProductService.getProducts();
        ArrayList<Product> listProduct = ProductService.getProductByBranchID(branchID);
        boolean flag = false;
        for (Product pd : listProduct) {
            if (pd.getName().equals(spName)) {
                flag = true;
                break;
            }
        }
        Assertions.assertTrue(flag);
    }
        
        @Test
    @DisplayName("Kiểm tra cập nhật thất bại")
    public void updateProductBranchFail() throws SQLException {
        String productID = "6489662d-cad8-43c5-8c0a-34562fa36475";
        String name = "Update test";
        String origin = "Viet Nam";
        Assertions.assertFalse(ProductService.updateProduct(productID, name,origin , 0));
    }
    
        @Test
    @DisplayName("Kiểm tra cập nhật thành công")
    public void updateProductBranchSuccess() throws SQLException {
        String productID = "1689662d-cad8-43c5-8c0a-34562fa36475";
        String name = "Update test";
        String origin = "Viet Nam";
        Assertions.assertTrue(ProductService.updateProduct(productID, name,origin , 0));
        ProductService.getProducts();
        Product product = ProductService.findProductByID(productID);
        Assertions.assertEquals(product.getName(),name);
        Assertions.assertEquals(product.getOrigin(),origin);
    }
    
       
}
