package com.i2f.train.finance.common.util;

import java.text.DecimalFormat;
import java.util.Random;

public class RandomRate {

    public static String getRandomRate(Double min , Double max){

        Random r = new Random();

        int minTemp = min.intValue();
        int maxTemp = max.intValue();

        double temp1 = Math.random() * minTemp;
        double temp2 = Math.random() * maxTemp;

        minTemp = (minTemp > 0) ? minTemp : -minTemp;

        double result;

        int temp = r.nextInt(minTemp + maxTemp);
        if (temp > minTemp){
            result = temp2;
        } else if (temp < minTemp){
            result = temp1;
        } else {
            result = 0;
        }


        DecimalFormat df = new DecimalFormat( "0.00" );

        String resultString = df.format(result);

        return resultString;
    }

}
