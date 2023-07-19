package com.i2f.train.starter.common.util;

import com.i2f.train.starter.common.constants.CommonConstants;
import com.i2f.train.starter.common.model.RedisTemplates;
import com.i2f.train.starter.common.model.Result;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * Created with IntelliJ IDEA.
 *
 * @Author: hjx
 * @Date: 2022/02/27/18:36
 * @Description:
 */
public class JwtUtils {

    private static final int TOKEN_TIME_OUT = 3600 * 24;
    private static final String TOKEN_ENCRY_KEY = "disizuchongchongchong";

    public static String createToken(String userId){
        Map<String, Object> claimMaps = new HashMap<>();
        claimMaps.put("userId",userId);
        long currentTime = System.currentTimeMillis();
        JwtBuilder jwtBuilder = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, TOKEN_ENCRY_KEY)
                .setClaims(claimMaps)
                .setIssuedAt(new Date())
                .setExpiration(new Date(currentTime + TOKEN_TIME_OUT * 1000));
        String token = jwtBuilder.compact();
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes ();
        ServletRequestAttributes servletRequestAttributes= (ServletRequestAttributes) requestAttributes;
        HttpServletResponse response = servletRequestAttributes.getResponse ();
        response.setHeader("Authorization", token);
        return token;
    }

    /**
     * 验证token
     *
     * @param token token
     * @return
     */
    public static Result checkToken(String token) {
        Result result = null;
        if ("".equals(token)) {
            return Result.error(CommonConstants.Request.TOKEN_ERROR, CommonConstants.Request.TOKEN_ERROR_MESSAGE);
        }
        try {
            Jwt parse = Jwts.parser().setSigningKey(TOKEN_ENCRY_KEY).parseClaimsJws(token);
            Map map = (Map<String, Object>) parse.getBody();
            String userId = String.valueOf(map.get("userId"));
            if ("".equals(userId)) {
                return Result.error(CommonConstants.Request.TOKEN_ERROR, CommonConstants.Request.TOKEN_ERROR_MESSAGE);
            }
        } catch (Exception e) {
            return Result.error(CommonConstants.Request.TOKEN_ERROR, CommonConstants.Request.TOKEN_ERROR_MESSAGE);
        }
        return null;
    }

    /**
     * 从token中获取userId
     *
     * @return
     */
    public static String getUserId() {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String userId = null;
        String token = request.getHeader("Authorization");
        Jwt parse = Jwts.parser().setSigningKey(TOKEN_ENCRY_KEY).parseClaimsJws(token);
        Map map = (Map<String, Object>) parse.getBody();
        userId = String.valueOf(map.get("userId"));
        return userId;
    }
}
