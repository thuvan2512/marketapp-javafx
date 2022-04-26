/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thunv25.doanktpm;

import com.thunv25.pojo.Branch;
import com.thunv25.pojo.Gender;
import com.thunv25.pojo.Product;
import com.thunv25.pojo.ProductBranch;
import com.thunv25.pojo.Promotion;
import com.thunv25.pojo.Role;
import com.thunv25.pojo.Staff;
import com.thunv25.services.BillService;
import com.thunv25.services.BranchService;
import com.thunv25.services.ProductBranchService;
import com.thunv25.services.ProductService;
import com.thunv25.services.PromotionService;
import com.thunv25.services.StaffServices;
import com.thunv25.utils.Utils;
import java.io.IOException;
import java.net.URL;
import static java.sql.Date.valueOf;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

/**
 * FXML Controller class
 *
 * @author thu.nv2512
 */
public class AdminController implements Initializable {
    
    private static int chooseFunction = 0;
    private static int flag01 = 0; //đánh dấu để thay đổi view của quản lý sản phẩm
    private static int flag02 = 0; // chặn sự kiện thay đổi của cbBranch
    private static Branch currentBranch;
    @FXML
    private Pane pnParent;
    @FXML
    private Label lbStaff;
    @FXML
    private Label lbBranch;
    @FXML
    private Button btnProduct;
    @FXML
    private Button btnPromotion;
    @FXML
    private Button btnStaff;
    @FXML
    private Button btnBranch;
    @FXML
    private Button btnCancel;
    @FXML
    private TextField txtSearch;
    @FXML
    private ComboBox<Branch> cbBranch;
    @FXML
    private TableView<Object> tbAdminView;

    //Product management
    private Button btnUpdateProduct = new Button("Cập nhật sản phẩm");
    private Button btnAddOldProduct = new Button("Thêm sản phẩm cũ");
    private Button btnAddNewProduct = new Button("Thêm sản phẩm mới");
    private Button btnDeleteProduct = new Button("Xóa khỏi hệ thống");
    private TextField txtNameProduct = new TextField();
    private TextField txtUnitPrice = new TextField();
    private TextField txtOrigin = new TextField();
    //staff management
    private Button btnAddStaff = new Button("Thêm nhân viên");
    private Button btnDeleteStaff = new Button("Xóa nhân viên");
    private Button btnUpdateStaff = new Button("Cập nhật nhân viên");
    private TextField txtNameStaff = new TextField();
    private TextField txtUserName = new TextField();
    private TextField txtPassword = new TextField();
    private TextField txtHomeTown = new TextField();
    private TextField txtPhone = new TextField();
    private TextField txtEmail = new TextField();
    private ComboBox<Branch> cbBranchStaff = new ComboBox<>();
    private ComboBox<Gender> cbGender = new ComboBox<>();
    private ComboBox<Role> cbRole = new ComboBox<>();
    //branch managment
    private Button btnAddBranch = new Button("Thêm chi nhánh");
    private Button btnDeleteBranch = new Button("Xóa chi nhánh");
    private Button btnUpdateBranch = new Button("Cập nhật chi nhánh");
    private TextArea txtAddress = new TextArea();
    //promotion management
    private Button btnAddPromo = new Button("Thêm Khuyến mãi");
    private Button btnDeletePromo = new Button("Xóa khuyến mãi");
    private TextField txtPercentDiscount = new TextField();
    private DatePicker dtpStartDate = new DatePicker();
    private DatePicker dtpEndDate = new DatePicker();
    private ComboBox<Product> cbProductPromo = new ComboBox<>();
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        disableButton();
        setEvent();
        btnBranch.setDisable(false);
        cbBranch.setDisable(false);
        btnPromotion.setDisable(false);
        Staff currentStaff = StaffServices.getCurrentUser();
        lbStaff.setText(currentStaff.toString());
        setItemsForCBBranch(BranchService.getListBranch());
        cbBranch.setOnAction(evt -> {
            if (flag02 == 0) {
            tbAdminView.getColumns().clear();
            btnProduct.setDisable(false);
            btnPromotion.setDisable(false);
            btnStaff.setDisable(false);
            currentBranch = (Branch) (cbBranch.getValue());
            lbBranch.setText(currentBranch.toString());
            }
        });
        this.txtSearch.textProperty().addListener((evt) -> {
            switch (chooseFunction) {
                case 1:
                    if (flag01 == 0) {
                        this.loadDataProduct(currentBranch.getBranchID(), this.txtSearch.getText());
                    } else {
                        this.loadDataOldProduct(currentBranch.getBranchID(), this.txtSearch.getText());
                    }
                    break;
                case 2:
                    this.loadDataStaff(currentBranch.getBranchID(), this.txtSearch.getText());
                    break;
                case 3:
                    this.loadDataBranch(this.txtSearch.getText());
                    break;
                
            }
        });
    }
    
    public void disableButton() {
        cbBranch.setDisable(true);
        btnCancel.setDisable(true);
        btnProduct.setDisable(true);
        btnPromotion.setDisable(true);
        btnStaff.setDisable(true);
        txtSearch.setDisable(true);
        btnBranch.setDisable(true);
    }
    public void setItemsForCBBranch(ArrayList<Branch> listBranch) {
        cbBranch.getItems().clear();
        cbBranch.setItems(FXCollections.observableArrayList(listBranch));
    } 
    public void eventSelected(MouseEvent event) {
        switch (chooseFunction) {
            case 1:
                Product product = (Product) tbAdminView.getSelectionModel().getSelectedItem();
                txtNameProduct.setText(product.getName());
                txtOrigin.setText(product.getOrigin());
                txtUnitPrice.setText(String.format("%.0f", product.getPrice()));
                break;
            case 2:
                btnDeleteStaff.setDisable(false);
                Staff staff = (Staff) tbAdminView.getSelectionModel().getSelectedItem();
                txtNameStaff.setText(staff.getName());
                txtEmail.setText(staff.getEmail());
                txtPhone.setText(staff.getPhone());
                txtHomeTown.setText(staff.getHomeTown());
                txtUserName.setText(staff.getUserName());
                txtPassword.setText(staff.getPassWord());
                cbGender.getSelectionModel().select(staff.getGender() - 1);
                cbRole.getSelectionModel().select(staff.getRole() - 1);
                cbBranchStaff.getSelectionModel().select(BranchService.findBranchByID(staff.getBranch()));
                if (staff.getRole() == 1) {
                    btnDeleteStaff.setDisable(true);
                }
                break;
            case 3:
                Branch branch = (Branch)tbAdminView.getSelectionModel().getSelectedItem();
                txtAddress.setText(branch.getAddress());
                break;
            case 4:
                Promotion promotion = (Promotion)tbAdminView.getSelectionModel().getSelectedItem();
                txtPercentDiscount.setText(promotion.getPercentDiscount() + "");
                dtpStartDate.setValue(promotion.getStartDate().toLocalDate());
                dtpEndDate.setValue(promotion.getEndDate().toLocalDate());
                cbProductPromo.getSelectionModel().select(ProductService.findProductByID(promotion.getProductID()));
                break;
        }
    }
    public void btnCancelClick(ActionEvent event) {
        tbAdminView.getColumns().clear();
        cbBranch.getItems().clear();
        setItemsForCBBranch(BranchService.getListBranch());
        disableButton();
        btnPromotion.setDisable(false);
        btnBranch.setDisable(false);
        pnParent.getChildren().clear();
        lbBranch.setText("Trống");
        btnAddNewProduct.setDisable(false);
        btnUpdateProduct.setDisable(false);
        btnDeleteProduct.setDisable(false);
        txtNameProduct.setDisable(false);
        txtOrigin.setDisable(false);
        txtUnitPrice.setDisable(false);
        cbBranch.setDisable(false);
        flag01 = 0;
    } 
    public void setEvent() {
        btnDeleteProduct.setOnAction(evt -> {
            Product product = (Product) tbAdminView.getSelectionModel().getSelectedItem();
            try {
                if (ProductService.deleteProduct(product.getId()) == true) {
                    Utils.showBox("Xóa thành công!!!", Alert.AlertType.INFORMATION).show();
                    tbAdminView.getColumns().clear();
                    pnParent.getChildren().clear();
                    loadProduct();
                    ProductBranchService.getProductBranch();
                    loadDataProduct(currentBranch.getBranchID(), null);
                } else {
                    Utils.showBox("Xóa thất bại!!!", Alert.AlertType.ERROR).show();
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        btnAddNewProduct.setOnAction(evt -> {
            if (txtNameProduct.getText().isBlank() || txtOrigin.getText().isBlank() || txtUnitPrice.getText().isBlank()) {
                Utils.showBox("Bạn không được để trống thông tin", Alert.AlertType.WARNING).show();
            } else if(Utils.isNumber(txtUnitPrice.getText()) == false){
                        Utils.showBox("Sai định dạng số", Alert.AlertType.WARNING).show();
            }else if (Double.parseDouble(txtUnitPrice.getText()) < 0) {
                Utils.showBox("Không được nhập số âm", Alert.AlertType.WARNING).show();
            } else{
                try {
                    if (ProductService.createNewProduct(txtNameProduct.getText(), txtOrigin.getText(), Double.parseDouble(txtUnitPrice.getText()), currentBranch.getBranchID()) == true) {
                        Utils.showBox("Tạo mới thành công!!!", Alert.AlertType.INFORMATION).show();
                        tbAdminView.getColumns().clear();
                        pnParent.getChildren().clear();
                        loadProduct();
                        ProductService.getProducts();
                        ProductBranchService.getProductBranch();
                        loadDataProduct(currentBranch.getBranchID(), null);
                    } else {
                        Utils.showBox("Tạo mới thất bại!!!", Alert.AlertType.ERROR).show();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        btnAddOldProduct.setOnAction(evt -> {
            flag01 = 1;
            btnAddNewProduct.setDisable(true);
            btnUpdateProduct.setDisable(true);
            btnDeleteProduct.setDisable(true);
            txtNameProduct.setDisable(true);
            txtOrigin.setDisable(true);
            txtUnitPrice.setDisable(true);
            tbAdminView.getColumns().clear();
            pnParent.getChildren().clear();
            loadProduct();
            loadDataOldProduct(currentBranch.getBranchID(), null);
            tbAdminView.getColumns().remove(4);
            TableColumn col5 = new TableColumn();
            col5.setCellFactory((p) -> {
                Button btn = new Button("Thêm");
                btn.setOnAction((e) -> {
                    TableCell cell = (TableCell) ((Button) e.getSource()).getParent();
                    Product product = (Product) cell.getTableRow().getItem();
                    try {
                        if (ProductService.createProductBranch(product.getId(), currentBranch.getBranchID()) == true) {
                            Utils.showBox("Thêm thành công!!!", Alert.AlertType.INFORMATION).show();
                            ProductBranchService.getProductBranch();
                            loadDataOldProduct(currentBranch.getBranchID(), null);
                        } else {
                            Utils.showBox("Thêm thất bại!!!", Alert.AlertType.ERROR).show();
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                
                TableCell cell1 = new TableCell();
                cell1.setGraphic(btn);
                return cell1;
            });
            tbAdminView.getColumns().add(col5);
        });
        btnUpdateProduct.setOnAction(evt -> {
            Product product = (Product) tbAdminView.getSelectionModel().getSelectedItem();
            if (txtNameProduct.getText().isBlank() || txtOrigin.getText().isBlank() || txtUnitPrice.getText().isBlank()) {
                Utils.showBox("Bạn không được để trống thông tin", Alert.AlertType.WARNING).show();
            } else if(Utils.isNumber(txtUnitPrice.getText()) == false){
                 Utils.showBox("Sai định dạng số", Alert.AlertType.WARNING).show();
            }else if (Double.parseDouble(txtUnitPrice.getText()) < 0) {
                Utils.showBox("Không được nhập số âm", Alert.AlertType.WARNING).show();
            }else{
                try {
                    if (ProductService.updateProduct(product.getId(), txtNameProduct.getText(), txtOrigin.getText(), Double.parseDouble(txtUnitPrice.getText())) == true) {
                        Utils.showBox("Cập nhật thành công!!!", Alert.AlertType.INFORMATION).show();
                        tbAdminView.getColumns().clear();
                        pnParent.getChildren().clear();
                        loadProduct();
                        ProductService.getProducts();
                        ProductBranchService.getProductBranch();
                        loadDataProduct(currentBranch.getBranchID(), null);
                    } else {
                        Utils.showBox("Cập nhật thất bại!!!", Alert.AlertType.ERROR).show();
                    }
                    
                } catch (SQLException ex) {
                    Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        btnAddStaff.setOnAction(evt -> {
            if (txtNameStaff.getText().isBlank() || txtUserName.getText().isBlank() || txtPassword.getText().isBlank()
                    || txtHomeTown.getText().isBlank() || txtEmail.getText().isBlank() || txtPhone.getText().isBlank()
                    || cbBranchStaff.getSelectionModel().isEmpty() || cbGender.getSelectionModel().isEmpty()
                    || cbRole.getSelectionModel().isEmpty()) {
                Utils.showBox("Bạn không được để trống thông tin", Alert.AlertType.WARNING).show();
            } else if (StaffServices.checkNotExistUserName(txtUserName.getText()) == false) {
                Utils.showBox("Username đã tồn tại", Alert.AlertType.WARNING).show();
                txtUserName.requestFocus();
            } else {
                try {
                    if (StaffServices.createStaff(txtNameStaff.getText(), txtPhone.getText(), txtEmail.getText(),
                            txtHomeTown.getText(), txtUserName.getText(), txtPassword.getText(), cbRole.getSelectionModel().getSelectedIndex() + 1,
                            cbGender.getSelectionModel().getSelectedIndex() + 1, cbBranchStaff.getSelectionModel().getSelectedItem().getBranchID()) == true) {
                        Utils.showBox("Tạo mới nhân viên thành công!!!", Alert.AlertType.INFORMATION).show();
                        StaffServices.getStaffs();
                        pnParent.getChildren().clear();
                        tbAdminView.getColumns().clear();
                        loadStaff();
                        loadDataStaff(currentBranch.getBranchID(), null);
                    } else {
                        Utils.showBox("Tạo mới nhân viên thất bại!!!", Alert.AlertType.ERROR).show();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        btnDeleteStaff.setOnAction(evt -> {
            Staff staff = (Staff) tbAdminView.getSelectionModel().getSelectedItem();
            try {
                if (StaffServices.deleteStaff(staff.getStaffID()) == true) {
                    Utils.showBox("Xóa thành công!!!", Alert.AlertType.INFORMATION).show();
                    StaffServices.getStaffs();
                    tbAdminView.getColumns().clear();
                    pnParent.getChildren().clear();
                    loadStaff();
                    loadDataStaff(currentBranch.getBranchID(), null);
                } else {
                    Utils.showBox("Xóa thất bại!!!", Alert.AlertType.ERROR).show();
                }
            } catch (SQLException ex) {
                Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        btnUpdateStaff.setOnAction(evt -> {
            Staff staff = (Staff) tbAdminView.getSelectionModel().getSelectedItem();
             if (txtNameStaff.getText().isBlank() || txtUserName.getText().isBlank() || txtPassword.getText().isBlank()
                    || txtHomeTown.getText().isBlank() || txtEmail.getText().isBlank() || txtPhone.getText().isBlank()
                    || cbBranchStaff.getSelectionModel().isEmpty() || cbGender.getSelectionModel().isEmpty()
                    || cbRole.getSelectionModel().isEmpty()) {
                Utils.showBox("Bạn không được để trống thông tin", Alert.AlertType.WARNING).show();
            } else if (StaffServices.checkNotExistUserName(txtUserName.getText(),staff.getStaffID()) == false) {
                Utils.showBox("Username đã tồn tại", Alert.AlertType.WARNING).show();
                txtUserName.requestFocus();
            } else {
                try {
                    if (StaffServices.updateStaff(txtNameStaff.getText(), txtPhone.getText(), txtEmail.getText(),
                            txtHomeTown.getText(), txtUserName.getText(), txtPassword.getText(), cbRole.getSelectionModel().getSelectedIndex() + 1,
                            cbGender.getSelectionModel().getSelectedIndex() + 1, cbBranchStaff.getSelectionModel().getSelectedItem().getBranchID(),staff.getStaffID()) == true) {
                        Utils.showBox("Cập nhật nhân viên thành công!!!", Alert.AlertType.INFORMATION).show();
                        StaffServices.getStaffs();
                        lbStaff.setText(StaffServices.getCurrentUser().getName());
                        pnParent.getChildren().clear();
                        tbAdminView.getColumns().clear();
                        loadStaff();
                        loadDataStaff(currentBranch.getBranchID(), null);
                    } else {
                        Utils.showBox("Cập nhật nhân viên thất bại!!!", Alert.AlertType.ERROR).show();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        btnAddBranch.setOnAction(evt ->{
            if (txtAddress.getText().isBlank()) {
                Utils.showBox("Bạn không được để trống thông tin", Alert.AlertType.WARNING).show();
            }else {
                try {
                    if (BranchService.createBranch(txtAddress.getText()) == true) {
                        Utils.showBox("Tạo mới chi nhánh thành công!!!", Alert.AlertType.INFORMATION).show();
                        BranchService.getBranchs();
                        pnParent.getChildren().clear();
                        tbAdminView.getColumns().clear();
                        flag02 = 1;
                        setItemsForCBBranch(BranchService.getListBranch());  
                        flag02 = 0; 
                        loadBranch();
                        loadDataBranch(null);
                    } else {
                        Utils.showBox("Tạo mới chi nhánh thất bại!!!", Alert.AlertType.ERROR).show();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        btnUpdateBranch.setOnAction(evt ->{
            Branch branch = (Branch) tbAdminView.getSelectionModel().getSelectedItem();
            if (txtAddress.getText().isBlank()) {
                Utils.showBox("Bạn không được để trống thông tin", Alert.AlertType.WARNING).show();
            }else {
                try {
                    if (BranchService.updateBranch(branch.getBranchID(),txtAddress.getText()) == true) {
                        Utils.showBox("Cập nhật chi nhánh thành công!!!", Alert.AlertType.INFORMATION).show();
                        BranchService.getBranchs();
                        pnParent.getChildren().clear();
                        tbAdminView.getColumns().clear();
                        flag02 = 1;
                        setItemsForCBBranch(BranchService.getListBranch());  
                        flag02 = 0; 
                        loadBranch();
                        loadDataBranch(null);
                    } else {
                        Utils.showBox("Cập nhật chi nhánh thất bại!!!", Alert.AlertType.ERROR).show();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        btnDeleteBranch.setOnAction(evt -> {
            Branch branch = (Branch) tbAdminView.getSelectionModel().getSelectedItem();
            if (branch != null) {
                Alert a = Utils.showBox("Nếu thực hiện thao tác sẽ xóa luôn những nhân viên thuộc chi nhánh này !!!", Alert.AlertType.CONFIRMATION);
                a.setHeaderText("Bạn có chắc chắn muốn xóa?");
                a.setHeight(300);
                a.showAndWait();
                if (a.getResult() == ButtonType.OK) {
                    try {
                        if (BranchService.deleteBranch(branch.getBranchID()) == true) {
                            Utils.showBox("Xóa chi nhánh thành công!!!", Alert.AlertType.INFORMATION).show();
                            BranchService.getBranchs();
                            StaffServices.getStaffs();
                            ProductBranchService.getProductBranch();
                            pnParent.getChildren().clear();
                            tbAdminView.getColumns().clear();
                            flag02 = 1;
                            setItemsForCBBranch(BranchService.getListBranch());
                            flag02 = 0;
                            loadBranch();
                            loadDataBranch(null);
                        } else {
                            Utils.showBox("Xóa chi nhánh thất bại!!!", Alert.AlertType.ERROR).show();
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else{
                    Utils.showBox("Bạn đã hủy thao tác", Alert.AlertType.INFORMATION).show();
                }
            }
        });
        btnAddPromo.setOnAction(evt -> {
            if (txtPercentDiscount.getText().isBlank() || cbProductPromo.getSelectionModel().isEmpty() 
                    || dtpStartDate.getValue() == null || dtpEndDate.getValue() == null) {
                Utils.showBox("Bạn không được để trống thông tin", Alert.AlertType.WARNING).show();
            }else if (Utils.isNumber(txtPercentDiscount.getText()) == false){
                Utils.showBox("Sai định dạng số !!!", Alert.AlertType.WARNING).show();
            }else if (Double.parseDouble(txtPercentDiscount.getText()) > 1 || Double.parseDouble(txtPercentDiscount.getText()) < 0) {
                Alert a = Utils.showBox("Phần trăm giảm giảm giá phải trong khoảng từ 0 đến 1 !!!", Alert.AlertType.WARNING);
                a.setHeight(300);
                a.show();
            }else if(dtpStartDate.getValue().isAfter(dtpEndDate.getValue()) ) {
                Utils.showBox("Ngày bắt đầu phải trước ngày kết thúc!!!", Alert.AlertType.WARNING).show();
            } else if (PromotionService.checkValidToCreate(cbProductPromo.getSelectionModel().getSelectedItem().getId(), valueOf(dtpStartDate.getValue())) == false) {
                Alert a = Utils.showBox("Sản phẩm này có trong 1 chương trình giảm giá chưa hết hạn khác!!!", Alert.AlertType.WARNING);
                a.setHeight(300);
                a.show();
            }else{
                try {
                    if (PromotionService.createPromotion(cbProductPromo.getSelectionModel().getSelectedItem().getId(), valueOf(dtpStartDate.getValue()),valueOf(dtpEndDate.getValue()), Double.parseDouble(txtPercentDiscount.getText()))) {
                        Utils.showBox("Tạo mới khuyến mãi thành công!!!", Alert.AlertType.INFORMATION).show();
                        PromotionService.getPromotion();
                        pnParent.getChildren().clear();
                        tbAdminView.getColumns().clear();
                        loadPromotion();
                        loadDataPromo();
                    } else {
                        Utils.showBox("Tạo mới khuyến mãi thất bại!!!", Alert.AlertType.ERROR).show();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }           
        });
   
        btnDeletePromo.setOnAction(evt -> {
            Promotion promotion = (Promotion) tbAdminView.getSelectionModel().getSelectedItem();
            try {
                if (PromotionService.deletePromotion(promotion.getPromoID()) == true) {
                    Utils.showBox("Xóa thành công!!!", Alert.AlertType.INFORMATION).show();
                    PromotionService.getPromotion();
                    tbAdminView.getColumns().clear();
                    pnParent.getChildren().clear();
                    loadPromotion();
                    loadDataPromo();
                } else {
                    Utils.showBox("Xóa thất bại!!!", Alert.AlertType.ERROR).show();
                }
            } catch (SQLException ex) {
                Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    public void logOut(ActionEvent event) throws IOException {
        StaffServices.setCurrentUser(null);
        App.setRoot("FXMLlogin");
    }
    public void loadData() {
        switch (chooseFunction) {
            case 1:
                loadProduct();
                loadDataProduct(currentBranch.getBranchID(), null);
                break;
            case 2:
                loadStaff();
                loadDataStaff(currentBranch.getBranchID(), null);
                break;
            case 3:
                loadBranch();
                loadDataBranch(null);
                break;
            case 4:
                loadPromotion();
                loadDataPromo();
                break;
        }
    }
    
    public void btnProductClick(ActionEvent event) {
        disableButton();
        btnCancel.setDisable(false);
        txtSearch.setDisable(false);
        AdminController.chooseFunction = 1;
        loadData();
    }
    public void loadProduct() {
        TableColumn col1 = new TableColumn("Id");
        col1.setCellValueFactory(new PropertyValueFactory("id"));
        col1.setPrefWidth(50);
        
        TableColumn col2 = new TableColumn("Tên sản phẩm");
        col2.setCellValueFactory(new PropertyValueFactory("name"));
        col2.setPrefWidth(150);
        
        TableColumn col3 = new TableColumn("Xuất xứ");
        col3.setCellValueFactory(new PropertyValueFactory("origin"));
        col3.setPrefWidth(120);
        
        TableColumn col4 = new TableColumn("Đơn giá (VND)");
        col4.setCellValueFactory(new PropertyValueFactory("price"));
        col4.setPrefWidth(120);
        TableColumn col5 = new TableColumn();
        col5.setCellFactory((p) -> {
            Button btn = new Button("Xóa khỏi chi nhánh");
            btn.setOnAction((evt) -> {
                TableCell cell = (TableCell) ((Button) evt.getSource()).getParent();
                Product product = (Product) cell.getTableRow().getItem();
                
                try {
                    if (ProductService.deleteProductBranch(product.getId(), currentBranch.getBranchID()) == true) {
                        Utils.showBox("Xóa thành công!!!", Alert.AlertType.INFORMATION).show();
                        ProductBranchService.getProductBranch();
                        tbAdminView.getColumns().clear();
                        loadProduct();
                        loadDataProduct(currentBranch.getBranchID(), null);
                    } else {
                        Utils.showBox("Xóa thất bại!!!", Alert.AlertType.ERROR).show();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            
            TableCell cell1 = new TableCell();
            cell1.setGraphic(btn);
            return cell1;
        });
        this.tbAdminView.getColumns().addAll(col1, col2, col3, col4, col5);
        txtNameProduct.setPromptText("Tên sản phẩm");
        txtOrigin.setPromptText("Xuất xứ");
        txtUnitPrice.setPromptText("Đơn giá");
        
        btnAddOldProduct.setPrefWidth(150);
        btnDeleteProduct.setPrefWidth(150);
        btnAddNewProduct.setPrefWidth(150);
        btnUpdateProduct.setPrefWidth(150);
        btnAddOldProduct.setStyle("-fx-font-weight: bold");
        btnDeleteProduct.setStyle("-fx-font-weight: bold");
        btnAddNewProduct.setStyle("-fx-font-weight: bold");
        btnUpdateProduct.setStyle("-fx-font-weight: bold");
        
        this.pnParent.getChildren().clear();
        this.pnParent.getChildren().add(btnAddOldProduct);
        btnAddOldProduct.setLayoutY(0);
        this.pnParent.getChildren().add(btnDeleteProduct);
        btnDeleteProduct.setLayoutY(60);
        this.pnParent.getChildren().add(btnAddNewProduct);
        btnAddNewProduct.setLayoutY(30);
        this.pnParent.getChildren().add(btnUpdateProduct);
        btnUpdateProduct.setLayoutY(90);
        this.pnParent.getChildren().add(txtNameProduct);
        txtNameProduct.setLayoutY(120);
        this.pnParent.getChildren().add(txtOrigin);
        txtOrigin.setLayoutY(150);
        this.pnParent.getChildren().add(txtUnitPrice);
        txtUnitPrice.setLayoutY(180);
        
    }
    public void loadDataProduct(String branchID, String keyword) {
        if (keyword == null) {
            this.tbAdminView.setItems(FXCollections.observableArrayList((ProductService.getProductByBranchID(branchID))));
        } else {
            this.tbAdminView.setItems(FXCollections.observableArrayList(ProductService.filterListByKeyword((ProductService.getProductByBranchID(branchID)), keyword)));
        }
    }
    public void loadDataOldProduct(String branchID, String keyword) {
        if (keyword == null) {
            this.tbAdminView.setItems(FXCollections.observableArrayList((ProductService.getOldProductByBranchID(branchID))));
        } else {
            this.tbAdminView.setItems(FXCollections.observableArrayList(ProductService.filterListByKeyword((ProductService.getOldProductByBranchID(branchID)), keyword)));
        }
    }
    
    public void btnStaffClick(ActionEvent event) {
        disableButton();
        btnCancel.setDisable(false);
        txtSearch.setDisable(false);
        AdminController.chooseFunction = 2;
        loadData();
    }
    public void loadStaff() {
        TableColumn col1 = new TableColumn("Id");
        col1.setCellValueFactory(new PropertyValueFactory("staffID"));
        col1.setPrefWidth(50);
        
        TableColumn col2 = new TableColumn("Tên nhân viên");
        col2.setCellValueFactory(new PropertyValueFactory("name"));
        col2.setPrefWidth(120);
        
        TableColumn col3 = new TableColumn("Giới tính");
        col3.setCellValueFactory(new PropertyValueFactory("gender"));
        col3.setPrefWidth(75);
        
        TableColumn col4 = new TableColumn("Quê quán");
        col4.setCellValueFactory(new PropertyValueFactory("homeTown"));
        col4.setPrefWidth(100);
        
        TableColumn col5 = new TableColumn("Email");
        col5.setCellValueFactory(new PropertyValueFactory("email"));
        col5.setPrefWidth(120);
        
        TableColumn col6 = new TableColumn("Số điện thoại");
        col6.setCellValueFactory(new PropertyValueFactory("phone"));
        col6.setPrefWidth(120);
        
        TableColumn col7 = new TableColumn("Username");
        col7.setCellValueFactory(new PropertyValueFactory("userName"));
        col7.setPrefWidth(120);
        
        TableColumn col8 = new TableColumn("Password");
        col8.setCellValueFactory(new PropertyValueFactory("passWord"));
        col8.setPrefWidth(120);
        
        TableColumn col9 = new TableColumn("Vai trò");
        col9.setCellValueFactory(new PropertyValueFactory("role"));
        col9.setPrefWidth(100);
        
        TableColumn col10 = new TableColumn("Chi nhánh");
        col10.setCellValueFactory(new PropertyValueFactory("branch"));
        col10.setPrefWidth(120);
        
        this.tbAdminView.getColumns().addAll(col1, col2, col3, col4, col5, col6, col7, col8, col9, col10);
        pnParent.autosize();
        txtNameStaff.setPromptText("Tên nhân viên");
        txtHomeTown.setPromptText("Quê quán");
        txtEmail.setPromptText("Địa chỉ Email");
        txtPhone.setPromptText("Số điện thoại");
        txtUserName.setPromptText("Tên đăng nhập");
        txtPassword.setPromptText("Mật khẩu");
        
        pnParent.getChildren().add(btnAddStaff);
        btnAddStaff.setLayoutY(0);
        pnParent.getChildren().add(btnUpdateStaff);
        btnUpdateStaff.setLayoutY(30);
        pnParent.getChildren().add(btnDeleteStaff);
        btnDeleteStaff.setLayoutY(60);
        btnAddStaff.setPrefWidth(150);
        btnDeleteStaff.setPrefWidth(150);
        btnUpdateStaff.setPrefWidth(150);
        btnAddStaff.setStyle("-fx-font-weight: bold");
        btnDeleteStaff.setStyle("-fx-font-weight: bold");
        btnUpdateStaff.setStyle("-fx-font-weight: bold");
        
        pnParent.getChildren().add(txtNameStaff);
        txtNameStaff.setLayoutY(90);
        
        pnParent.getChildren().add(cbGender);
        cbGender.setLayoutY(120);
        cbGender.setItems(FXCollections.observableArrayList(Gender.listGender));
        pnParent.getChildren().add(cbRole);
        cbRole.setLayoutY(150);
        cbRole.setItems(FXCollections.observableArrayList(Role.listRole));
        pnParent.getChildren().add(cbBranchStaff);
        cbBranchStaff.setLayoutY(180);
        cbBranchStaff.setPrefWidth(170);
        cbGender.setPrefWidth(170);
        cbRole.setPrefWidth(170);
        cbBranchStaff.setItems(FXCollections.observableArrayList(BranchService.getListBranch()));
        
        pnParent.getChildren().add(txtHomeTown);
        txtHomeTown.setLayoutY(210);
        pnParent.getChildren().add(txtEmail);
        txtEmail.setLayoutY(240);
        pnParent.getChildren().add(txtPhone);
        txtPhone.setLayoutY(270);
        pnParent.getChildren().add(txtUserName);
        txtUserName.setLayoutY(300);
        pnParent.getChildren().add(txtPassword);
        txtPassword.setLayoutY(330);
    }
    public void loadDataStaff(String branchID, String keyword) {
        if (keyword == null) {
            this.tbAdminView.setItems(FXCollections.observableArrayList(StaffServices.getListStaffByBranchID(branchID)));
        } else {
            this.tbAdminView.setItems(FXCollections.observableArrayList(StaffServices.filterListByKeyword(StaffServices.getListStaffByBranchID(branchID), keyword)));
        }
    }
    
    public void btnBranchClick(ActionEvent event) {
        disableButton();
        cbBranch.setDisable(true);
        btnCancel.setDisable(false);
        txtSearch.setDisable(false);
        AdminController.chooseFunction = 3;
        loadData();
    }
    public void loadBranch() {
        TableColumn col1 = new TableColumn("Mã chi nhánh");
        col1.setCellValueFactory(new PropertyValueFactory("branchID"));
        col1.setPrefWidth(150);
        
        TableColumn col2 = new TableColumn("Địa chỉ");
        col2.setCellValueFactory(new PropertyValueFactory("address"));
        col2.setPrefWidth(500);
        this.tbAdminView.getColumns().addAll(col1, col2);
        pnParent.getChildren().add(btnAddBranch);
        btnAddBranch.setLayoutY(0);
        pnParent.getChildren().add(btnUpdateBranch);
        btnUpdateBranch.setLayoutY(30);
        pnParent.getChildren().add(btnDeleteBranch);
        btnDeleteBranch.setLayoutY(60);
        btnAddBranch.setPrefWidth(150);
        btnDeleteBranch.setPrefWidth(150);
        btnUpdateBranch.setPrefWidth(150);
        btnAddBranch.setStyle("-fx-font-weight: bold");
        btnDeleteBranch.setStyle("-fx-font-weight: bold");
        btnUpdateBranch.setStyle("-fx-font-weight: bold");               
        pnParent.getChildren().add(txtAddress);
        txtAddress.setLayoutY(90);
        txtAddress.setPrefWidth(200);
        txtAddress.setPrefHeight(100);
        txtAddress.wrapTextProperty().setValue(Boolean.TRUE);
        txtAddress.setPromptText("Địa chỉ chi nhánh");
    }
    public void loadDataBranch(String keyword) {
        if (keyword == null) {
            this.tbAdminView.setItems(FXCollections.observableArrayList(BranchService.getListBranch()));
        } else {
            this.tbAdminView.setItems(FXCollections.observableArrayList(BranchService.filterListByKeyword(BranchService.getListBranch(), keyword)));
        }
    }
    
    
    public void btnPromotionClick(ActionEvent event) {
        disableButton();
        cbBranch.setDisable(true);
        btnCancel.setDisable(false);;
        AdminController.chooseFunction = 4;
        loadData();
    }
    public void loadPromotion() {
        TableColumn col1 = new TableColumn("ID khuyến mãi");
        col1.setCellValueFactory(new PropertyValueFactory("promoID"));
        col1.setPrefWidth(100);
        
        TableColumn col2 = new TableColumn("Mã sản phẩm");
        col2.setCellValueFactory(new PropertyValueFactory("productID"));
        col2.setPrefWidth(100);
        
        TableColumn col3 = new TableColumn("Ngày bắt đầu");
        col3.setCellValueFactory(new PropertyValueFactory("startDate"));
        col3.setPrefWidth(150);
        
        TableColumn col4 = new TableColumn("Ngày kết thúc");
        col4.setCellValueFactory(new PropertyValueFactory("endDate"));
        col4.setPrefWidth(150);
        
        TableColumn col5 = new TableColumn("% giảm giá");
        col5.setCellValueFactory(new PropertyValueFactory("percentDiscount"));
        col5.setPrefWidth(100);
        
        this.tbAdminView.getColumns().addAll(col1, col2,col3,col4,col5);
        pnParent.getChildren().add(btnAddPromo);
        btnAddPromo.setLayoutY(0);
        pnParent.getChildren().add(btnDeletePromo);
        btnDeletePromo.setLayoutY(30);
        btnAddPromo.setPrefWidth(150);
        btnDeletePromo.setPrefWidth(150);
        btnAddPromo.setStyle("-fx-font-weight: bold");
        btnDeletePromo.setStyle("-fx-font-weight: bold");               
        pnParent.getChildren().add(txtPercentDiscount);
        pnParent.getChildren().add(dtpStartDate);
        pnParent.getChildren().add(dtpEndDate);
        pnParent.getChildren().add(cbProductPromo);
        cbProductPromo.setLayoutY(60);
        dtpStartDate.setLayoutY(90);
        dtpStartDate.setPromptText("Ngày bắt đầu");
        dtpEndDate.setPromptText("Ngày kết thúc");
        dtpEndDate.setLayoutY(120);
        txtPercentDiscount.setLayoutY(150);
        txtPercentDiscount.setPromptText("% giảm giá");
        cbProductPromo.setItems(FXCollections.observableArrayList(ProductService.getListProducts()));
        cbProductPromo.setPrefWidth(200);
        txtPercentDiscount.setPrefWidth(200);
        dtpEndDate.setPrefWidth(200);
        dtpStartDate.setPrefWidth(200);
        
    }
    public void loadDataPromo() {
        this.tbAdminView.setItems(FXCollections.observableArrayList(PromotionService.getListPromotions()));
    }
}
