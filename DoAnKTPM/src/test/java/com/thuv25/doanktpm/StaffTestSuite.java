/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thuv25.doanktpm;

import com.thunv25.pojo.Product;
import com.thunv25.pojo.ProductBranch;
import com.thunv25.pojo.Staff;
import com.thunv25.services.StaffServices;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 *
 * @author thu.nv2512
 */
public class StaffTestSuite {
    @Test
    @DisplayName("Kiểm tra đăng nhập hợp lệ")
    public void checkValidLogin() throws SQLException {
        String id = "a27a1a63-456d-49cb-a34e-88f1d11e6f74";
        String username = "admin";
        String password = "123";
        Assertions.assertEquals(StaffServices.checkLogin(username, password).getStaffID(),id);
    }

    @Test
    @DisplayName("Kiểm tra trùng username")
    public void testUserNameUnique() throws SQLException {
        ArrayList<Staff> listStaff = StaffServices.getListStaff();

        List<String> listUserName = listStaff.stream().flatMap(staff -> Stream.of(staff.getUserName())).collect(Collectors.toList());
        Set<String> userNameSet = new HashSet<>(listUserName);
        Assertions.assertEquals(listUserName.size(), userNameSet.size());
    }

    @Test
    @DisplayName("Kiểm tra tìm kiếm thành công")
    public void testSearchSuccessful() throws SQLException {
        String kw = "thu";
        ArrayList<Staff> listSearch = StaffServices.filterListByKeyword(StaffServices.getListStaff(), kw);

        for (Staff p : listSearch) {
            Assertions.assertTrue(p.getName().toLowerCase().contains(kw.toLowerCase())
                    || p.getHomeTown().toLowerCase().contains(kw.toLowerCase()));
        }
    }

    @Test
    @DisplayName("Kiểm tra tìm kiếm thất bại")
    public void testSearchFail() throws SQLException {
        String kw = "fhghjh";
        ArrayList<Staff> listSearch = StaffServices.filterListByKeyword(StaffServices.getListStaff(), kw);

        for (Staff p : listSearch) {
            Assertions.assertFalse(p.getName().toLowerCase().contains(kw.toLowerCase())
                    || p.getHomeTown().toLowerCase().contains(kw.toLowerCase()));
        }
    }

    @Test
    @DisplayName("Kiểm tra xóa khỏi hệ thống thất bại")
    public void deleteFail() throws SQLException {
        String ID = "alsn9999670eb24-3985-40f9-bab6-a86de52a5c34";
        Assertions.assertFalse(StaffServices.deleteStaff(ID));
    }

    @Test
    @DisplayName("Kiểm tra xóa nhân viên khỏi hệ thống thành công")
    public void deleteSuccess() throws SQLException {
        boolean flag = true;
        String ID = "jhsjn9999670eb24-3985-40f9-bab6-a86de52a5c34";
        Assertions.assertTrue(StaffServices.deleteStaff(ID));
        StaffServices.getStaffs();
        for (int i = 0; i < StaffServices.getListStaff().size(); i++) {
            if (StaffServices.getListStaff().get(i).getStaffID().equals(ID)) {
                flag = false;
                break;
            }
        }
        Assertions.assertTrue(flag);
    }

    @Test
    @DisplayName("Kiểm tra nhân viên thêm thành công")
    public void addSuccess() throws SQLException {
        String branchID = "gdfb6f1b-c178-4e3f-a8a6-73c85d266d91";
        String userName = "username";
        Assertions.assertTrue(StaffServices.createStaff("a", "a", "a", "a", userName, "123", 2, 1, branchID));
        StaffServices.getStaffs();
        boolean flag = false;
        for (Staff pd : StaffServices.getListStaffByBranchID(branchID)) {
            if (pd.getUserName().equals(userName)) {
                flag = true;
                break;
            }
        }
        Assertions.assertTrue(flag);
    }

    @Test
    @DisplayName("Kiểm tra cập nhật nhân viên thành công")
    public void updateSuccess() throws SQLException {
        String staffID = "reyc95d1-9763-4908-b1ba-cd031d0cce43";
        String name = "Update test";
        Assertions.assertTrue(StaffServices.updateStaff(name, "phone", "email", "home", "usernamee", "123", 2, 1, "gdfb6f1b-c178-4e3f-a8a6-73c85d266d91", staffID));
        StaffServices.getStaffs();
        boolean flag = false;
        for (Staff staff : StaffServices.getListStaff()) {
            if (staff.getStaffID().equals(staffID)) {
                if (staff.getName().equals(name)) {
                    flag = true;
                }
            }
        }
        Assertions.assertTrue(flag);
    }

    @Test
    @DisplayName("Kiểm tra cập nhật nhân viên thất bại")
    public void updateFail() throws SQLException {
        String staffID = "reyc95d1-9763-4908-b1ba-cd031d0cce43";
        Assertions.assertFalse(StaffServices.updateStaff("name", "phone", "email", "home", "usernamee", "123", 2, 1, "gdfb6f1b-c178-4e3f-a8a6-73c85d266d91", staffID));
    }
}
