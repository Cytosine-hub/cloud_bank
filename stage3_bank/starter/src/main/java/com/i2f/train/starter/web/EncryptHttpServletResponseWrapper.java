package com.i2f.train.starter.web;

import com.i2f.train.starter.common.util.CryptoUtil;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

/**
 * @author cw
 * @date 2022年03月15日 13:11
 */
public class EncryptHttpServletResponseWrapper extends HttpServletResponseWrapper {
    private ServletOutputStreamWrapper servletOutputStreamWrapper;
    private PrintWriterWrapper printWriterWrapper;
    private String aesKey;


    public EncryptHttpServletResponseWrapper(HttpServletResponse response, String aesKey) {
        super(response);
        servletOutputStreamWrapper = new ServletOutputStreamWrapper(response);
        printWriterWrapper = new PrintWriterWrapper(new StringWriter(), response);
        this.aesKey = aesKey;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return servletOutputStreamWrapper;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return printWriterWrapper;
    }

    private class PrintWriterWrapper extends PrintWriter {
        private StringWriter writer;
        private HttpServletResponse response;
        private boolean flashed = false;

        public PrintWriterWrapper(StringWriter writer, HttpServletResponse response) {
            super(writer);
            try {
                this.writer = writer;
                this.response = response;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void flush() {
            try {
                if (!flashed) {
                    String data = CryptoUtil.aesEncrypt(writer.toString(), aesKey);
                    response.setContentLengthLong(-1);
                    response.getWriter().write(data);
                    response.getWriter().flush();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void close() {
            try {
                this.flush();
                super.close();
                response.getWriter().close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class ServletOutputStreamWrapper extends ServletOutputStream {
        private HttpServletResponse response;
        private boolean flushed = false;
        ByteArrayOutputStream byteArrayOutputStream;

        public ServletOutputStreamWrapper(HttpServletResponse response) {
            try {
                this.response = response;
                this.byteArrayOutputStream = new ByteArrayOutputStream();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public boolean isReady() {
            try {
                return response.getOutputStream().isReady();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        public void setWriteListener(WriteListener writeListener) {
            try {
                response.getOutputStream().setWriteListener(writeListener);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void write(int b) throws IOException {
            byteArrayOutputStream.write(b);
        }

        @Override
        public void flush() throws IOException {
            if (!this.flushed) {
                String data = CryptoUtil.aesEncrypt(new String(byteArrayOutputStream.toByteArray(), StandardCharsets.UTF_8), aesKey);
                response.setContentLengthLong(-1);//重置Response中大小，预防客户端不完全接受返回信息
                response.getOutputStream().write(data.getBytes(StandardCharsets.UTF_8));
                response.getOutputStream().flush();
                flushed = true;
            }
        }

        @Override
        public void close() throws IOException {
            this.flush();
            response.getOutputStream().close();
            byteArrayOutputStream.close();
        }
    }
}
