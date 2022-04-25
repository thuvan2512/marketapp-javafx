/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thuv25.doanktpm;

import com.thunv25.pojo.Branch;
import com.thunv25.pojo.Product;
import com.thunv25.pojo.Staff;
import com.thunv25.services.BranchService;
import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 *
 * @author thu.nv2512
 */
public class BranchTestSuite {

    @Test
    @DisplayName("Kiểm tra tìm kiếm thành công")
    public void testSearchSuccessful() throws SQLException {
        String kw = "ng";
        ArrayList<Branch> listSearch = BranchService.filterListByKeyword(BranchService.getListBranch(), kw);

        for (Branch p : listSearch) {
            Assertions.assertTrue(p.getAddress().toLowerCase().contains(kw.toLowerCase()));
        }
    }
    @Test
    @DisplayName("Kiểm tra tìm kiếm thất bại")
    public void testSearchFail() throws SQLException {
        String kw = "kgjhlkhjknkl";
        ArrayList<Branch> listSearch = BranchService.filterListByKeyword(BranchService.getListBranch(), kw);

        for (Branch p : listSearch) {
            Assertions.assertFalse(p.getAddress().toLowerCase().contains(kw.toLowerCase()));
        }
    }
    
    @Test
    @DisplayName("Kiểm tra xóa khỏi hệ thống thất bại")
    public void deleteFail() throws SQLException {
        String ID = "alsn9999670eb24-3985-40f9-bab6-a86de52a5c34";
        Assertions.assertFalse(BranchService.deleteBranch(ID));
    }
    
    @Test
    @DisplayName("Kiểm tra xóa khỏi hệ thống thành công")
    public void deleteSuccess() throws SQLException {
        boolean flag = true;
        String ID = "jhsjn9999670eb24-3985-40f9-bab6-a86de52a5c34";
        Assertions.assertTrue(BranchService.deleteBranch(ID));
        BranchService.getBranchs();
        for (int i = 0; i < BranchService.getListBranch().size(); i++) {
            if (BranchService.getListBranch().get(i).getBranchID().equals(ID)) {
                flag = false;
                break;
            }
        }
        Assertions.assertTrue(flag);
    }

    @Test
    @DisplayName("Kiểm tra thêm chi nhánh thành công")
    public void addSuccess() throws SQLException {
        String address = "test branch";
        Assertions.assertTrue(BranchService.createBranch(address));
        BranchService.getBranchs();
        boolean flag = false;
        for (Branch pd : BranchService.getListBranch()) {
            if (pd.getAddress().equals(address)) {
                flag = true;
                break;
            }
        }
        Assertions.assertTrue(flag);
    }
    
    @Test
    @DisplayName("Kiểm tra cập nhật chi nhanh thành công")
    public void updateSuccess() throws SQLException {
        String ID = "115dd543-06f0-417e-941a-1ec75fde5f64";
        String address = "Gò Vấp thành phố Hồ Chí Minh, Việt Nam";
        Assertions.assertTrue(BranchService.updateBranch(ID,address));
        BranchService.getBranchs();
        boolean flag = false;
        for (Branch br : BranchService.getListBranch()) {
            if (br.getBranchID().equals(ID)) {
                if (br.getAddress().equals(address)) {
                    flag = true;
                }
            }
        }
        Assertions.assertTrue(flag);
    }

    @Test
    @DisplayName("Kiểm tra cập nhật chi nhánh thất bại")
    public void updateFail() throws SQLException {
        String ID = "sd15dd543-06f0-417e-941a-1ec75fde5f64";
        String address = "Gò Vấp thành phố Hồ Chí Minh, Việt Nam";
        Assertions.assertFalse(BranchService.updateBranch(ID,address));
    }
}
