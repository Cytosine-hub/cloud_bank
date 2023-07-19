package com.i2f.train.account.utils;

import java.util.UUID;

public class UUIDUtils {

    /**
     * 获得8个长度的十六进制的UUID
     * @return UUID
     */
    public static String get8UUID(){
        UUID id=UUID.randomUUID();
        String[] idd=id.toString().split("-");
        return idd[0];
    }
    }

