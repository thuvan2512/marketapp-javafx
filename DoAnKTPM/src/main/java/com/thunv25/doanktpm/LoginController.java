/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thunv25.doanktpm;

import com.thunv25.pojo.Staff;
import com.thunv25.services.StaffServices;
import com.thunv25.utils.Utils;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author thu.nv2512
 */
public class LoginController implements Initializable {
    /**
     * Initializes the controller class.
     */
    @FXML 
    private PasswordField txtPassword;
    
    @FXML
    private TextField txtUsername;
            
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    public void logIntoApp(ActionEvent evt) throws IOException{
//       App.setRoot("FXMLadmin");
//       txtUsername.setText("sis");
//       txtPassword.setText("hkajs");
        if (txtUsername.getText().isBlank() || txtPassword.getText().isBlank()) {
            Utils.showBox("Do not leave the username or password input field blank", Alert.AlertType.WARNING).show();
        }
        else{
            Staff staff = StaffServices.checkLogin(txtUsername.getText(), txtPassword.getText());
            if (staff != null) {
                StaffServices.setCurrentUser(staff);
                switch (staff.getRole()){
                case 1:
                    App.setRoot("FXMLmanager");
                    break;
                case 2:
                    App.setRoot("FXMLsale");
//                    Utils.showBox(StaffServices.getCurrentUser().toString(), Alert.AlertType.NONE).show();
                    break;
            }
            }else{
            Utils.showBox("Login failed", Alert.AlertType.ERROR).show();
                    txtUsername.requestFocus();
            }    
        }
    }
}
