package com.i2f.train.user.common.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Map;

/**
 * @program: third_bird
 * @author: Cytosine
 * @create: 2022-04-13 20:24
 **/
public class HttpUtil {

    public static String getFyData(){
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet("https://interface.sina.cn/news/wap/fymap2020_data.d.json");
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            String s = EntityUtils.toString(entity);
            return s;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
