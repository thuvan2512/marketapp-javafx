package com.thunv25.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javafx.scene.control.Alert;
import java.util.UUID;
/**
 *
 * @author thu.nv2512
 */
public class Utils {
    public static Alert showBox(String msg, Alert.AlertType type) {
        Alert a = new Alert(type);
        a.setContentText(msg);
        return a;
    }

    public static String getUUID(){
        return UUID.randomUUID().toString();
    }
    public static boolean isNumber(String kt){
        try {
            double so = Double.parseDouble(kt.strip());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}