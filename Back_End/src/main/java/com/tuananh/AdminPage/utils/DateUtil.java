package com.tuananh.AdminPage.utils;

import java.sql.Date;
public class DateUtil {
    public static Date getCurrentDay(){
        java.util.Date currentDate = new java.util.Date();
        return new Date(currentDate.getTime());
    }
}
