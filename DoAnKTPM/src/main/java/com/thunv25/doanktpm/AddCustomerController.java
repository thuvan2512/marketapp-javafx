package com.thunv25.doanktpm;

import com.thunv25.pojo.Customer;
import com.thunv25.pojo.Gender;
import com.thunv25.pojo.Product;
import com.thunv25.services.CustomerService;
import com.thunv25.services.GenderService;
import com.thunv25.services.ProductService;
import com.thunv25.services.StaffServices;
import com.thunv25.utils.JdbcUtils;
import com.thunv25.utils.Utils;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.NetworkChannel;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.UUID;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class AddCustomerController implements Initializable {

    @FXML
    private TextField txtCustomerName;
    @FXML
    private TextField txtCustomerPhone;
    @FXML
    private ComboBox<Gender> cbGender;
    @FXML
    DatePicker dpCustomer;

    CustomerService customerService = new CustomerService();
    GenderService genderService = new GenderService();
//    ArrayList<Customer> myCustomer = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        cbGender.setItems(FXCollections.observableArrayList(GenderService.getListGenders()));

    }

    public void addCustomerButton(ActionEvent event) throws SQLException {
        boolean flag = false;
        if (!txtCustomerName.getText().isEmpty()) {
            if (!txtCustomerPhone.getText().isEmpty()) {
                if (cbGender.getValue() != null) {
                    if (dpCustomer.getValue() != null) {
                        flag = customerService.checkPhone(txtCustomerPhone.getText());
//                        for (int i = 0; i < CustomerService.getListCustomers().size(); i++) {
//                            if (txtCustomerPhone.getText().equals(CustomerService.getListCustomers().get(i).getPhone())) {
//                                flag = true;
//                                break;
//                            }
//                        }
                        if (flag == false) {
                            Customer customer = new Customer(UUID.randomUUID().toString(), txtCustomerName.getText(), cbGender.getValue().getGenderID(), txtCustomerPhone.getText(), java.sql.Date.valueOf(dpCustomer.getValue()));
                            customerService.addCustomer(customer);
                            Utils.showBox("Đăng ký khách hàng thành công!", Alert.AlertType.INFORMATION).show();
                        }
                        else {
                            Utils.showBox("Trùng số điện thoại!", Alert.AlertType.ERROR).show();
                        }
                    } else {
                        Utils.showBox("Chưa nhập ngày sinh!", Alert.AlertType.ERROR).show();
                    }
                } else {
                    Utils.showBox("Chưa chọn giới tính!", Alert.AlertType.ERROR).show();
                }
            } else {
                Utils.showBox("Chưa nhập số điện thoại khách hàng!", Alert.AlertType.ERROR).show();
            }
        } else {
            Utils.showBox("Chưa nhập tên khách hàng!", Alert.AlertType.ERROR).show();
        }
    }

    public void goBack(ActionEvent event) throws IOException {
        App.setRoot("FXMLsale");
    }
}
