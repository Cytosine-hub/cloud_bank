package com.i2f.train.starter.web;

import com.i2f.train.starter.common.constants.CommonConstants;
import com.i2f.train.starter.common.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * @author cw
 * @date 2022年03月15日 09:40
 */
@WebFilter
@Slf4j
public class EncryptDecryptFilter implements Filter {
    private static final List<String> DECRYPT_EXCLUDE_LIST = Arrays.asList(
            "/favicon.ico",
            "/user/register"

    );

    private static final List<String> ENCRYPT_EXCLUDE_LIST = Arrays.asList(
            "/favicon.ico",
            "/user/register"
    );
    private static final String KEY_ID_HEADER = "keyId";
    private static final String AES_KEY_PREFIX = "AES_";
    @Resource
    RedisTemplate<String, Object> redisTemplate;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String header = request.getHeader(KEY_ID_HEADER);
        String aesKey = (String) redisTemplate.opsForValue().get(AES_KEY_PREFIX + header);
        String requestURI = request.getRequestURI();
        //如果请求的接口在加解密的列表内 但是没有传keyId 就不处理
        if ((!DECRYPT_EXCLUDE_LIST.contains(requestURI) || !ENCRYPT_EXCLUDE_LIST.contains(requestURI)) && StringUtils.isBlank(header)) {
//            Result<Object> result = Result
//                    .builder()
//                    .code(CommonConstants.Request.PARAMETER_ERROR)
//                    .message(CommonConstants.Request.PARAMETER_ERROR_MESSAGE)
//                    .build();
//            response.getOutputStream().write(result.toString().getBytes(StandardCharsets.UTF_8));
//            response.getOutputStream().flush();
            filterChain.doFilter(request, response);
            return;
        }
        if (!DECRYPT_EXCLUDE_LIST.contains(requestURI)) {
            request = new DecryptHttpServletRequestWrapper(request, aesKey);
        }
        if (!ENCRYPT_EXCLUDE_LIST.contains(requestURI)) {
            response = new EncryptHttpServletResponseWrapper(response, aesKey);
        }
        filterChain.doFilter(request, response);
        return;
    }
}
