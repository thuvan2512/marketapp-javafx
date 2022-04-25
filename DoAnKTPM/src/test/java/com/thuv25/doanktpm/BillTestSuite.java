/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thuv25.doanktpm;

import com.thunv25.pojo.Bill;
import com.thunv25.services.BillService;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BillTestSuite {
    @Test
    public void textAddBill() throws SQLException {
        LocalDate currentDate = LocalDate.now();
        Bill bill = new Bill(UUID.randomUUID().toString(), "A", "B", true, 0.1, 150, java.sql.Date.valueOf(currentDate));
        BillService billService = new BillService();
        Assertions.assertTrue(billService.addBill(bill));
    }
}
