package com.thunv25.doanktpm;

import java.net.URL;
import com.thunv25.utils.JdbcUtils;
import com.thunv25.pojo.Bill;
import com.thunv25.pojo.Customer;
import com.thunv25.pojo.Order;
import com.thunv25.pojo.Product;
import com.thunv25.services.BillService;
import com.thunv25.services.BranchService;
import com.thunv25.services.OrderService;
import com.thunv25.services.ProductService;
import com.thunv25.services.StaffServices;
import com.thunv25.utils.Utils;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import java.util.UUID;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class SaleController implements Initializable {

    public static double tongTien = 0;
    public static double tienThoiLai = 0;
    public static double discount = 0;
    public static boolean temp = false;
    @FXML
    private TableColumn<Product, Integer> colProductID;
    @FXML
    private TableColumn<Product, String> colName;
    @FXML
    private TableColumn<Product, String> colOrigin;
    @FXML
    private TableColumn<Product, Double> colUnitPrice;
    @FXML
    private TableColumn<Product, Double> colQuantity;
    @FXML
    TableView<Product> tbView;
    @FXML
    private TextField txtQuantity;
    @FXML
    private ComboBox<Product> cbProductID;
    @FXML
    private Label lbTotalPrice;
    @FXML
    private Text lbStaff;
    @FXML
    private Text lbBranch;
    @FXML
    private Label lbLeftMoney;
    @FXML
    private TextField txtCustomerMoney;
    @FXML
    private TextField txtMaKhachHang;

    List<Customer> getCustomer = new ArrayList<>();
    ProductService productService = new ProductService();
    BillService billService = new BillService();

    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        try {
            lbStaff.setText(StaffServices.getCurrentUser().getName());
            lbBranch.setText(BranchService.findBranchByID(StaffServices.getCurrentUser().getBranch()).getAddress());
        } catch (Exception e) {
            Utils.showBox(e.getMessage(), Alert.AlertType.NONE).show();
        }

        try ( Connection conn = JdbcUtils.getConnection()) {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM customer");

            while (rs.next()) {
                Customer customer = new Customer(rs.getString("cusId"), rs.getString("name"), rs.getInt("gender"), rs.getString("phone"), rs.getDate("dob"));
                getCustomer.add(customer);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SaleController.class.getName()).log(Level.SEVERE, null, ex);
        }

        cbProductID.setItems(FXCollections.observableArrayList(ProductService.getProductByBranchID(StaffServices.getCurrentUser().getBranch())));
        colProductID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colOrigin.setCellValueFactory(new PropertyValueFactory<>("origin"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("unit"));
    }

    public void showNumbers(MouseEvent event) {
        Product product = tbView.getSelectionModel().getSelectedItem();
        productService.showNumbers(product, txtQuantity);
    }

    public void addButton(ActionEvent event) {
        if (cbProductID.getValue() != null) {
            Product product = new Product();
            productService.checkDataToAdd(cbProductID, product, tbView, txtQuantity);
            productService.showTotalMoney(tbView, lbTotalPrice, txtQuantity);
        }
    }

    public void updateButton(ActionEvent event) {
        productService.update(txtQuantity, tbView);
        productService.showTotalMoney(tbView, lbTotalPrice, txtQuantity);
    }

    public void deleteButton(ActionEvent event) {
        productService.delete(tbView, txtQuantity);
        productService.showTotalMoney(tbView, lbTotalPrice, txtQuantity);
    }

    public void btThanhToan(ActionEvent event) throws SQLException {
        if (!txtCustomerMoney.getText().isEmpty()) {
            ObservableList<Product> items = tbView.getItems();

            if (items.isEmpty()) {
                Utils.showBox("Your table is empty!", AlertType.ERROR).show();
            } else {
                if (productService.isNumberic(txtCustomerMoney.getText())) {
                    Double d = Double.parseDouble(txtCustomerMoney.getText());
                    if (d < 0) {
                        Utils.showBox("Not enough money!", Alert.AlertType.ERROR).show();
                    } else {
                        Product products = new Product();
                        List<String> newList = new ArrayList<>();

                        double tienKhachDua = Double.parseDouble(txtCustomerMoney.getText());
                        String maKhachHang = "";
                        String cusID = "";

                        double tongTien = SaleController.tongTien;

                        //Kiem tra xem textfield ma khach hang co bi rong khong
                        if (!txtMaKhachHang.getText().equals("")) {
                            maKhachHang = txtMaKhachHang.getText();
                        }

                        //Dieu kien
                        productService.checkDiscount(getCustomer, newList, maKhachHang, cusID, tongTien);

                        //Neu temp == true thi giam gia tong tien theo phan tram, nguoc lai thi khong giam gia
                        productService.discount(temp, tongTien, discount, tienKhachDua);
                        lbLeftMoney.setText(String.format("%,.0f VND", tienThoiLai));
                        //Hien thong bao neu dua khong du tien, nguoc lai thi hien so tien va ghi vao co so du lieu
                        if (tienThoiLai < 0) {
                            Utils.showBox("Not enough money!", Alert.AlertType.ERROR).show();

                        } else {
                            productService.arlertPaymentAndInsertData(products, cusID, billService, discount, tbView);
                        }
                    }

                } else {
                    Utils.showBox("Nhap sai dinh dang!", AlertType.ERROR).show();
                }
            }

        } else {
            Utils.showBox("Khong duoc de rong!", AlertType.ERROR).show();
        }

    }

    public void goBackLoginForm(ActionEvent event) throws IOException {
        StaffServices.setCurrentUser(null);
        App.setRoot("FXMLlogin");
    }
    
    public void goToAddCustomerController(ActionEvent event) throws IOException {
        App.setRoot("FXMLaddCustomer");
    }
}
