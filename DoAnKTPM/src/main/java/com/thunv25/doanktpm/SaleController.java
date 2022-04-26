package com.thunv25.doanktpm;

import java.net.URL;
import com.thunv25.utils.JdbcUtils;
import com.thunv25.pojo.Bill;
import com.thunv25.pojo.Customer;
import com.thunv25.pojo.Order;
import com.thunv25.pojo.Product;
import com.thunv25.services.BillService;
import com.thunv25.services.BranchService;
import com.thunv25.services.CustomerService;
import com.thunv25.services.OrderService;
import com.thunv25.services.ProductService;
import com.thunv25.services.PromotionService;
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
import javafx.scene.paint.Color;

public class SaleController implements Initializable {

    public static double tongTien = 0;
    public static double tienThoiLai = 0;
    public static double discount = 0;
    public static double discount2 = 0;
    public static boolean temp = false;
    public static String cusID;
    public static double myTemp = 0;
    public static double myTemp2 = 0;
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
    private TableColumn<Product, Double> colPromoPrice;
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
    private Label lbDiscount;
    @FXML
    private TextField txtCustomerMoney;
    @FXML
    private TextField txtMaKhachHang;
    @FXML
    private Label lbDiscountS;
    @FXML
    private Label lbMoneyAfter;

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
        colPromoPrice.setCellValueFactory(new PropertyValueFactory<>("promoPrice"));
    }

    public void showNumbers(MouseEvent event) {
        Product product = tbView.getSelectionModel().getSelectedItem();
        productService.showNumbers(product, txtQuantity);
    }

    public void addButton(ActionEvent event) {
        if (cbProductID.getValue() != null) {
            Product product = new Product();
            PromotionService promotionService = new PromotionService();

//            productService.showTotalMoney(tbView, lbTotalPrice, txtQuantity);
            productService.checkDataToAdd(cbProductID, product, tbView, txtQuantity);
            promotionService.checkProductDiscount(tbView);
            productService.showTotalMoney(tbView, lbTotalPrice, txtQuantity);
            double money = 0;
            if (!lbTotalPrice.getText().equals(lbMoneyAfter.getText())) {
                if (SaleController.myTemp != 0) {
//                    System.out.println(SaleController.myTemp);
                    money = SaleController.tongTien;
                    double money1 = SaleController.tongTien - (SaleController.tongTien * 0.1);
                    lbMoneyAfter.setText(String.format("%,.0f VND", money1));
                } else {
                    money = SaleController.tongTien;
                    lbMoneyAfter.setText(String.format("%,.0f VND", money));
                }
            }
        }
    }

    public void updateButton(ActionEvent event) {
        productService.update(txtQuantity, tbView);
        productService.showTotalMoney(tbView, lbTotalPrice, txtQuantity);

        double money = 0;
        if (!lbTotalPrice.getText().equals(lbMoneyAfter.getText())) {
            if (SaleController.myTemp != 0) {
//                    System.out.println(SaleController.myTemp);
                money = SaleController.tongTien;
                double money1 = SaleController.tongTien - (SaleController.tongTien * 0.1);
                lbMoneyAfter.setText(String.format("%,.0f VND", money1));
            } else {
                money = SaleController.tongTien;
                lbMoneyAfter.setText(String.format("%,.0f VND", money));
            }
        }
    }

    public void deleteButton(ActionEvent event) {
        
        productService.delete(tbView, txtQuantity);
        if (tbView.getItems().isEmpty()) {
            lbMoneyAfter.setText("");
            lbTotalPrice.setText("");
        }
        productService.showTotalMoney(tbView, lbTotalPrice, txtQuantity);
//        productService.showTotalMoney(tbView, lbTotalPrice, txtQuantity);
        double money = 0;
        if (!lbTotalPrice.getText().equals(lbMoneyAfter.getText())) {
            if (SaleController.myTemp != 0) {
                money = SaleController.tongTien;
                double money1 = SaleController.tongTien - (SaleController.tongTien * 0.1);
                lbMoneyAfter.setText(String.format("%,.0f VND", money1));
            } else {
                money = SaleController.tongTien;
                lbMoneyAfter.setText(String.format("%,.0f VND", money));
            }
        }
        
        
//        List<Product> arrayList = new ArrayList<>();
//        Product product = new Product();
//        //Get data from tableview
//        for (int i = 0; i < tbView.getItems().size(); i++) {
//            product = tbView.getItems().get(i);
//            arrayList.add(product);
//        }
//
//        if (arrayList.isEmpty()) {
//            txtCustomerMoney.setText("");
//            txtMaKhachHang.setText("");
//            txtQuantity.setText("");
//            lbTotalPrice.setText("");
//            lbLeftMoney.setText("");
//            lbDiscount.setText("");
////                            lbDiscountS.setText("");
//            tbView.getItems().clear();
//            lbMoneyAfter.setText("");
//        }
    }

    public void btThanhToan(ActionEvent event) throws SQLException {
        if (!txtCustomerMoney.getText().isEmpty()) {
            ObservableList<Product> items = tbView.getItems();

            if (items.isEmpty()) {
                Utils.showBox("Bạn chưa thêm sản phẩm vào bảng!", AlertType.ERROR).show();
            } else {
                if (productService.isNumberic(txtCustomerMoney.getText())) {
                    Double d = Double.parseDouble(txtCustomerMoney.getText());
                    if (d < 0) {
                        Utils.showBox("Không được nhập số âm!", Alert.AlertType.ERROR).show();
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
                        productService.discount(temp, tongTien, SaleController.discount, tienKhachDua);
                        lbLeftMoney.setText(String.format("%,.0f VND", tienThoiLai));
                        //Hien thong bao neu dua khong du tien, nguoc lai thi hien so tien va ghi vao co so du lieu
                        if (tienThoiLai < 0) {
                            Utils.showBox("Số tiền không đủ để thanh toán!", Alert.AlertType.ERROR).show();

                        } else {
                            productService.arlertPaymentAndInsertData(products, SaleController.cusID, billService, discount, tbView);

                            txtCustomerMoney.setText("");
                            txtMaKhachHang.setText("");
                            txtQuantity.setText("");
                            lbTotalPrice.setText("");
                            lbLeftMoney.setText("");
                            lbDiscount.setText("");
//                            lbDiscountS.setText("");
                            tbView.getItems().clear();
                            lbMoneyAfter.setText("");
                        }
                    }

                } else {
                    Utils.showBox("Bạn đã nhập sai định dạng!", AlertType.ERROR).show();
                }
            }

        } else {
            Utils.showBox("Bạn chưa nhập số tiền khách hàng đưa!", AlertType.ERROR).show();
        }

    }

    public void checkProductDiscount(ActionEvent event) {
        boolean temp2 = false;
        for (int i = 0; i < CustomerService.getListCustomers().size(); i++) {
            if (txtMaKhachHang.getText().equals(CustomerService.getListCustomers().get(i).getPhone())) {
                lbDiscountS.setTextFill(Color.web("#4BB543"));
                lbDiscount.setText(CustomerService.getListCustomers().get(i).getName());
                lbDiscount.setTextFill(Color.web("#4BB543"));
                temp2 = true;
            }
        }
        if (temp2 == false) {
            lbDiscount.setTextFill(Color.web("#D0342C"));
            lbDiscount.setText("Không tìm thấy!");
            lbDiscountS.setTextFill(Color.web("#D0342C"));
        }

        boolean temp = false;
        double tongTien = SaleController.tongTien;
        LocalDate currentDate = LocalDate.now();
        if (txtMaKhachHang != null && !txtMaKhachHang.getText().equals("")) {
            for (int i = 0; i < CustomerService.getListCustomers().size(); i++) {
                if (txtMaKhachHang.getText().equals(CustomerService.getListCustomers().get(i).getPhone())
                        && SaleController.tongTien > 1000000
                        && java.sql.Date.valueOf(currentDate).equals(CustomerService.getListCustomers().get(i).getDob())) {
                    SaleController.myTemp2 = tongTien;
                    tongTien = tongTien - tongTien * 0.1;
                    SaleController.myTemp = tongTien;
                    lbMoneyAfter.setText(String.format("%,.0f VND", tongTien));
                    temp = true;
                }
            }
            if (temp == false) {
                lbMoneyAfter.setText(String.format("%,.0f VND", SaleController.tongTien));
            }
        }
//        else {
//            Utils.showBox("Hãy nhập mà khách hàng!", AlertType.ERROR).show();
//        }
    }

    public void goBackLoginForm(ActionEvent event) throws IOException {
        StaffServices.setCurrentUser(null);
        App.setRoot("FXMLlogin");
    }

    public void goToAddCustomerController(ActionEvent event) throws IOException {
        App.setRoot("FXMLaddCustomer");
    }
}
