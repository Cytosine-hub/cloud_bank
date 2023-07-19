package com.i2f.train.starter.common.util;

import java.util.regex.Pattern;

/**
 * @author: 仁
 * 2022/3/30/ 13:44
 */
public class RegexUtil {
    public static boolean validateMobilePhone(String in) {
        Pattern pattern=Pattern.compile("^[1]\\d{10}$");
        return pattern.matcher(in).matches();
    }
}
