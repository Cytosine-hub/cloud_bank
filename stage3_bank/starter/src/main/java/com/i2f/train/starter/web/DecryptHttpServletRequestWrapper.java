package com.i2f.train.starter.web;

import com.alibaba.cloud.commons.io.IOUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.i2f.train.starter.common.util.CryptoUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author cw   //全报文加密
 * @date 2022年03月15日 10:02
 */
public class DecryptHttpServletRequestWrapper extends HttpServletRequestWrapper {
    private String body = "这是一段密文";
    private final Map<String, String[]> map = new HashMap<>();
    private final String aesKey;

    public DecryptHttpServletRequestWrapper(HttpServletRequest request, String aesKey) {
        super(request);
        this.aesKey = aesKey;
        initialBody(request);
        initialMap(request);
    }

    /**
     * 全报文加密是 {data:密文} 还是 密文
     * 如果是 【1】 那么解密的时候会出问题
     * 如果是【2】 那么解密之后就是json字符串
     * 这一步实际上只是解析了request里的内容  并没有对request进行包装或者修改？
     * 那么在initialMap的时候 还是原来的request对象
     *
     *
     * 实际上是初始化了post请求 请求体里面的参数
     * @param request
     */
    private void initialBody(HttpServletRequest request) {
        try {
            ServletInputStream inputStream = request.getInputStream();
            body = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            //JSON字符串 明文-明文
            body = CryptoUtil.aesDecrypt(body, aesKey);
//            //-------->非全报文加密 body是一个JSON字符串
//            //key-value   明文key-密文值
//            JSONObject jsonObject = JSON.parseObject(body);
//            Set<Map.Entry<String, Object>> entries = jsonObject.entrySet();
//            for (Map.Entry<String, Object> entry : entries) {
//                String value = (String) entry.getValue();
//                entry.setValue(CryptoUtil.aesDecrypt(value, aesKey));
//            }
//            //明文-明文  JSONObject
//            body = jsonObject.toJSONString();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从原始的request解析出报文  ？？  那么如果是全报文加密  {data:密文}  与initialBody的 【1】 冲突
     * 如果  不冲突
     * 实际上 解析出密文中的键值对之后 需要进行字典排序？
     * 然后将 数据存储在map中  controller 取参数的时候 调用getP 和一些 getPM getPV 和getPN方法？
     *
     * 实际上是初始化了get请求的参数
     * @param request 原始request
     */
    private void initialMap(HttpServletRequest request) {
        //密文
        String parameter = request.getParameter("data");
        if (StringUtils.isBlank(parameter)){
            return;
        }

        //明文-明文  JSON字符串
        String decrypt = CryptoUtil.aesDecrypt(parameter,aesKey);
        JSONObject jsonObject = JSON.parseObject(decrypt);
        Set<String> strings = jsonObject.keySet();
        Object o = jsonObject.get("");
        Set<String> strings1 = jsonObject.keySet();
        for (String s : strings1) {
            try {
                //???FastJSON 在转JSON时是转第一册还是转所有层
                map.put(s, new String[]{(String) jsonObject.get(s)});
                continue;
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                map.put(s, (String[]) jsonObject.getJSONArray(s).toArray());
                continue;
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                map.put(s, new String[]{jsonObject.getJSONObject(s).toJSONString()});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public ServletInputStream getInputStream() {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes(StandardCharsets.UTF_8));
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int readLine(byte[] b, int off, int len) throws IOException {
                return super.readLine(b, off, len);
            }

            @Override
            public int read() {
                return byteArrayInputStream.read();
            }

            @Override
            public int read(byte[] b) throws IOException {
                return byteArrayInputStream.read(b);
            }

            @Override
            public int read(byte[] b, int off, int len) {
                return byteArrayInputStream.read(b, off, len);
            }

            @Override
            public long skip(long n) {
                return byteArrayInputStream.skip(n);
            }

            @Override
            public int available() {
                return byteArrayInputStream.available();
            }

            @Override
            public void close() throws IOException {
                byteArrayInputStream.close();
            }

            @Override
            public synchronized void mark(int readlimit) {
                byteArrayInputStream.mark(readlimit);
            }

            @Override
            public synchronized void reset() {
                byteArrayInputStream.reset();
            }

            @Override
            public boolean markSupported() {
                return byteArrayInputStream.markSupported();
            }
        };
    }

    @Override
    public String getParameter(String name) {
        String[] strings = map.get(name);
        if (strings != null && strings.length >= 1) {
            ///???
            return strings[0];
        }
        return "";
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return map;
    }

    @Override
    public Enumeration<String> getParameterNames() {
        Vector<String> vector = new Vector<>(map.keySet());
        return vector.elements();
    }

    @Override
    public String[] getParameterValues(String name) {
        return map.get(name);
    }

}
