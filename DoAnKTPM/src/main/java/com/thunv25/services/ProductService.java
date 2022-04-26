package com.thunv25.services;

import com.thunv25.doanktpm.SaleController;
import static com.thunv25.doanktpm.SaleController.tienThoiLai;
import com.thunv25.pojo.Bill;
import com.thunv25.pojo.Customer;
import com.thunv25.pojo.Order;
import com.thunv25.pojo.Product;
import com.thunv25.utils.JdbcUtils;
import com.thunv25.utils.Utils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ProductService {

    private static ArrayList<Product> listProducts = new ArrayList<>();

    static {
        try {
            ProductService.getProducts();
        } catch (SQLException ex) {
            Logger.getLogger(ProductService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ArrayList<Product> getListProducts() {
        return listProducts;
    }

    public void showNumbers(Product product, TextField txtQuantity) {
        if (product == null) {
            txtQuantity.setText("");
        } else {
            String unit = product.getUnit() + "";
            txtQuantity.setText(unit);
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

    public void showTotalMoney(TableView<Product> tbView, Label lbTotalPrice, TextField txtQuantity) {
        //Get data from tableview
        Product product = new Product();
        List<Double> arrayList1 = new ArrayList<>();
        List<Double> arrayList2 = new ArrayList<>();
//        List<Double> arrayList3 = new ArrayList<>();
        //Lay don gia va so luong cua tung dong
        for (int i = 0; i < tbView.getItems().size(); i++) {
            product = tbView.getItems().get(i);
            arrayList1.add(product.getPrice());
            arrayList2.add(product.getUnit());
//            arrayList3.add(product.getPrice());
        }

        //tinh tong bang so luong nhan don gia
        double tong = 0;
        for (int i = 0; i < arrayList1.size(); i++) {
            tong += arrayList1.get(i) * arrayList2.get(i);
        }

        //hien ra label tong 
        lbTotalPrice.setText(String.format("%,.0f VND", tong));
        SaleController.tongTien = tong;
        txtQuantity.setText("");
        tbView.refresh();
    }

    public void checkDataToAdd(ComboBox<Product> cbProductID, Product product, TableView<Product> tbView, TextField txtQuantity) {
        boolean flag = true;
        List<Product> arrayList = new ArrayList<>();

        //Get data from tableview
        for (int i = 0; i < tbView.getItems().size(); i++) {
            product = tbView.getItems().get(i);
            arrayList.add(product);
        }

        //Neu nhu san pham trong tableview trung voi san pham trong combobox thi tang so luong them 1
        for (int i = 0; i < arrayList.size(); i++) {
            if (cbProductID.getValue().getId().equals(arrayList.get(i).getId())) {
                double temp = arrayList.get(i).getUnit();
                arrayList.get(i).setUnit(temp + 1);
                arrayList.get(i).setPromoPrice(cbProductID.getValue().getPrice());
                txtQuantity.setText("");
                tbView.refresh();
                flag = false;
            }
        }

        //Neu nhu khong trung thi them san pham voi so luong mac dinh la 1
        if (flag) {
            Product data = new Product(cbProductID.getValue().getId(), cbProductID.getValue().getName(), cbProductID.getValue().getOrigin(), cbProductID.getValue().getPrice());
            data.setPromoPrice(cbProductID.getValue().getPrice());
            if (txtQuantity.getText().equals("")) {
                tbView.getItems().add(data);
                txtQuantity.setText("");
                tbView.refresh();
            } else {
                if (isNumberic(txtQuantity.getText())) {
                    double d = Double.parseDouble(txtQuantity.getText());
                    if (d <= 0) {
                        Utils.showBox("Bạn phải nhập số lượng lớn hơn 0!", Alert.AlertType.ERROR).show();
                    } else {
                        data.setUnit(d);
                        tbView.getItems().add(data);
                        txtQuantity.setText("");
                        tbView.refresh();
                    }
                } else {
                    Utils.showBox("Bạn nhập sai định dạng!", Alert.AlertType.ERROR).show();
                }
            }
        }
    }

    public void update(TextField txtQuantity, TableView<Product> tbView) {
        if (txtQuantity.getText() != null) {
            Product getProduct = tbView.getSelectionModel().getSelectedItem();
            if (getProduct != null && txtQuantity.getText().length() > 0 && txtQuantity.getText() != null) {
                if (isNumberic(txtQuantity.getText())) {
                    if (Double.parseDouble(txtQuantity.getText()) > 0) {
                        getProduct.setUnit(Double.parseDouble(txtQuantity.getText()));
                        txtQuantity.setText("");
                        tbView.refresh();
                    }
                    else{
                        Utils.showBox("Số lượng phải lớn hơn 0", Alert.AlertType.WARNING).show();
                    }
                }
                else 
                    Utils.showBox("Bạn nhập sai định dạng!", Alert.AlertType.ERROR).show();
            }
        }
    }

    public void delete(TableView<Product> tbView, TextField txtQuantity) {
//        ObservableList<Product> allProducts, singleProducts;
//        Product singleProducts;
//        allProducts = tbView.getItems();
//        singleProducts = tbView.getSelectionModel().getSelectedItems();
//        singleProducts.forEach(allProducts::remove);
        tbView.getItems().removeAll(tbView.getSelectionModel().getSelectedItems());
        txtQuantity.setText("");
        tbView.refresh();
    }

    public void checkDiscount(List<Customer> getCustomer, List<String> newList, String maKhachHang, String cusID, double tongTien) {
        double discount = 0;
        boolean temp = false;

        //Get current date and month
        LocalDate currentDate = LocalDate.now();
        int day = currentDate.getDayOfMonth();
        int month = currentDate.getMonthValue();

        for (int i = 0; i < getCustomer.size(); i++) {
            newList.add(getCustomer.get(i).getDob() + "");
            String[] value = newList.get(i).split("-");
            if (maKhachHang.equals(getCustomer.get(i).getPhone()) && day == Integer.parseInt(value[2]) && month == Integer.parseInt(value[1]) && tongTien > 1000000) {
                temp = true;
                discount = 0.1;
                cusID = getCustomer.get(i).getCusID();
            }
        }

        SaleController.discount = discount;
        SaleController.temp = temp;
        SaleController.cusID = cusID;
    }

    public void discount(boolean temp, double tongTien, double discount, double tienKhachDua) {
        double tienThoiLai;
        if (temp == true) {
            tongTien = tongTien - tongTien * discount;
            SaleController.tongTien = tongTien;
            tienThoiLai = tienKhachDua - tongTien;
        } else {
            tienThoiLai = tienKhachDua - tongTien;
        }
        SaleController.tienThoiLai = tienThoiLai;
        SaleController.tongTien = tongTien;
    }

    public void arlertPaymentAndInsertData(Product products, String cusID, BillService billService, double discount, TableView<Product> tbView) throws SQLException {
        LocalDate currentDate = LocalDate.now();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(String.format("Số tiền thối lại cho khách hàng : %,.0f VND", tienThoiLai));
        alert.setContentText(String.format("Bạn được giảm giá %s%s vào tổng tiền ╰(*°▽°*)╯", SaleController.discount * 100, "%"));
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Bill bills = new Bill(UUID.randomUUID().toString(), cusID, BranchService.findBranchByID(StaffServices.getCurrentUser().getBranch()).getBranchID(), true, discount, SaleController.tongTien, java.sql.Date.valueOf(currentDate));

            //Add bills to database
            billService.addBill(bills);

            //Add order to database
            for (int i = 0; i < tbView.getItems().size(); i++) {
                products = tbView.getItems().get(i);
                Order order = new Order(UUID.randomUUID().toString(), products.getUnit(), bills.getId(), products.getId());
                OrderService orderService = new OrderService();
                orderService.addOrder(order);
            }
            Utils.showBox("Thanh toán thành công!", Alert.AlertType.INFORMATION).show();
        } else {
            Utils.showBox("Thanh toán thất bại!", Alert.AlertType.ERROR).show();
        }
    }

    public static void getProducts() throws SQLException {
        listProducts = new ArrayList<>();
        try ( Connection conn = JdbcUtils.getConnection()) {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM product");
            while (rs.next()) {
                Product product = new Product(rs.getString("productID"), rs.getString("name"), rs.getString("productOrigin"), rs.getDouble("unitPrice"));
                listProducts.add(product);
            }
        }
    }

    public static Product findProductByID(String id) {
        for (int i = 0; i < ProductService.listProducts.size(); i++) {
            if (listProducts.get(i).getId().equals(id)) {
                return listProducts.get(i);
            }
        }
        return null;
    }

    public static ArrayList<Product> getProductByBranchID(String branchID) {
        ArrayList<Product> list = new ArrayList<>();
        for (int i = 0; i < ProductBranchService.getListProductBranch().size(); i++) {
            if (ProductBranchService.getListProductBranch().get(i).getBranchID().equals(branchID)) {
                list.add(ProductService.findProductByID(ProductBranchService.getListProductBranch().get(i).getProductID()));
            }
        }
        return list;
    }

    public static ArrayList<Product> getOldProductByBranchID(String branchID) {
        ArrayList<Product> list1 = new ArrayList<>();
        ArrayList<Product> list2 = new ArrayList<>();
        ArrayList<Product> list3 = new ArrayList<>();
        list1 = ProductService.getListProducts();
        list2 = getProductByBranchID(branchID);
        list3.addAll(list1);
        for (int i = 0; i < list2.size(); i++) {
            for (int j = 0; j < list1.size(); j++) {
                if (list1.get(j).getId().equals(list2.get(i).getId())) {
                    list3.remove(list2.get(i));
                }
            }
        }
        return list3;
    }

    public static boolean deleteProductBranch(String productID, String branchID) throws SQLException {
        try ( Connection conn = JdbcUtils.getConnection()) {
            PreparedStatement stm2 = conn.prepareStatement("DELETE FROM product_branch WHERE productID=? AND branchID = ?");
            stm2.setString(1, productID);
            stm2.setString(2, branchID);
            return stm2.executeUpdate() > 0;
        }
    }

    public static boolean deleteProduct(String productID) throws SQLException {
        try ( Connection conn = JdbcUtils.getConnection()) {
            PreparedStatement stm1 = conn.prepareStatement("DELETE FROM product WHERE productID=?");
            stm1.setString(1, productID);
            PreparedStatement stm2 = conn.prepareStatement("DELETE FROM product_branch WHERE productID=?");
            stm2.setString(1, productID);
            stm2.executeUpdate();
            PreparedStatement stm3 = conn.prepareStatement("DELETE FROM promotion WHERE productID=?");
            stm3.setString(1, productID);
            stm3.executeUpdate();
            return stm1.executeUpdate() > 0;
        }
    }

    public static boolean createNewProduct(String name, String origin, double price, String branchID) throws SQLException {
        try ( Connection conn = JdbcUtils.getConnection()) {
            PreparedStatement stm1 = conn.prepareStatement("INSERT INTO product (productID, name, productOrigin, unitPrice) VALUES (?, ?, ?, ?);");
            String id = Utils.getUUID();
            stm1.setString(1, id);
            stm1.setString(2, name);
            stm1.setString(3, origin);
            stm1.setDouble(4, price);
            PreparedStatement stm2 = conn.prepareStatement("INSERT INTO product_branch (productID, branchID) VALUES (?, ?);");
            stm2.setString(1, id);
            stm2.setString(2, branchID);

            return stm1.executeUpdate() * stm2.executeUpdate() > 0;
        }
    }

    public static boolean createProductBranch(String productID, String branchID) throws SQLException {
        try ( Connection conn = JdbcUtils.getConnection()) {
            PreparedStatement stm2 = conn.prepareStatement("INSERT INTO product_branch (productID, branchID) VALUES (?, ?);");
            stm2.setString(1, productID);
            stm2.setString(2, branchID);

            return stm2.executeUpdate() > 0;
        }
    }

    public static boolean updateProduct(String productID, String name, String origin, double price) throws SQLException {
        try ( Connection conn = JdbcUtils.getConnection()) {
            PreparedStatement stm2 = conn.prepareStatement("UPDATE product SET name = ?, productOrigin = ?, unitPrice = ? WHERE (productID = ?);");
            stm2.setString(1, name);
            stm2.setString(2, origin);
            stm2.setDouble(3, price);
            stm2.setString(4, productID);

            return stm2.executeUpdate() > 0;
        }
    }

    public static ArrayList<Product> filterListByKeyword(ArrayList<Product> list, String key) {
        return (ArrayList<Product>) list.stream().filter(product -> product.getName().toLowerCase().contains(key.toLowerCase())).collect(Collectors.toList());
    }
}
