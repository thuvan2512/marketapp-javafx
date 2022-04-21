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

    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {

        customerService.getCustomers();
        cbGender.setItems(FXCollections.observableArrayList(GenderService.getListGenders()));

    }

    public void addCustomerButton(ActionEvent event) throws SQLException {
        if (!txtCustomerName.getText().isEmpty()) {
            if (!txtCustomerPhone.getText().isEmpty()) {
               if (cbGender != null) {
                    if (dpCustomer.getValue() != null) {
                        Customer customer = new Customer(UUID.randomUUID().toString(), txtCustomerName.getText(), cbGender.getValue().getGenderID(), txtCustomerPhone.getText(), java.sql.Date.valueOf(dpCustomer.getValue()));
                        customerService.addCustomer(customer);
                        Utils.showBox("Ghi khach hang thanh cong!", Alert.AlertType.INFORMATION).show();
                    } else {
                        Utils.showBox("Chua nhap ngay sinh!", Alert.AlertType.ERROR).show();
                    }
                } else {
                   Utils.showBox("Chua chon gioi tinh!", Alert.AlertType.ERROR).show();
               }
            } else {
                Utils.showBox("Chua nhap so dien thoai khach hang!", Alert.AlertType.ERROR).show();
            }
        } else {
            Utils.showBox("Chua nhap ten khach hang!", Alert.AlertType.ERROR).show();
        }
    }

    public void goBack(ActionEvent event) throws IOException {
        App.setRoot("FXMLsale");
    }
}
