package com.i2f.train.financeProvider.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Wqsh
 * @version 1.0
 * @date 2022/3/1 23:08
 */
public class todayDateUtil {
    public static String todayDate()  {
        Date d = new Date();
        System.out.println(d);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateNowStr = sdf.format(d);
        return dateNowStr;
    }
}
