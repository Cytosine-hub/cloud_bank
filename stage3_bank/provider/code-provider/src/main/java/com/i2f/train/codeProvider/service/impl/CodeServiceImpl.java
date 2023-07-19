package com.i2f.train.codeProvider.service.impl;

import com.i2f.train.codeProvider.service.CodeService;
import com.i2f.train.starter.common.constants.CommonConstants;
import com.i2f.train.starter.common.model.RedisTemplates;
import com.i2f.train.starter.common.model.Result;
import com.i2f.train.starter.common.util.ImageCodeUtil;
import com.i2f.train.starter.common.util.SmsTool;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @program: third_bird
 * @author: Cytosine
 * @create: 2022-04-01 09:31
 **/
@Service
public class CodeServiceImpl implements CodeService {

    @Resource
    RedisTemplate redisTemplate;

    @Override
    public void getImageCode(HttpServletResponse response, String id) {
        //页面缓存设置为不缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        Map<String, Object> map = ImageCodeUtil.generateImage();
        //获取验证码的值
        String result = (String) map.get("value");
        //获取验证码的图像
        BufferedImage image = (BufferedImage) map.get("image");

        //将验证码存到redis
        redisTemplate.opsForValue().set(id, String.valueOf(result), 3, TimeUnit.MINUTES);
        try {
            //将图像输出到页面
            ImageIO.write(image, "jpeg", response.getOutputStream());
            response.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    @Override
    public Result getPhoneCode(String mobilePhone) {
        if (StringUtils.isBlank(mobilePhone)) {
            return Result.error(CommonConstants.ValidateCode.PHONE_CODE_ERROR, CommonConstants.ValidateCode.PHONE_CODE_ERROR_MESSAGE);
        }
        try {
            String code = SmsTool.VerificationCode(mobilePhone);
//            String code = "123456";
            redisTemplate.opsForValue().set(mobilePhone, code, 2, TimeUnit.MINUTES);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(CommonConstants.ValidateCode.PHONE_CODE_ERROR, CommonConstants.ValidateCode.PHONE_CODE_ERROR_MESSAGE);
        }
        return Result.success(CommonConstants.Request.SUCCESS, CommonConstants.Request.SUCCESS_MESSAGE);
    }

    @Override
    public Result checkPhoneCode(String mobilePhone, String code) {
        if (StringUtils.isAnyBlank(mobilePhone, code)) {
            return Result.error(CommonConstants.ValidateCode.NOT_NULL_ERROR, CommonConstants.ValidateCode.NOT_NULL_ERROR_MESSAGE);
        }
        String redisCode = (String) redisTemplate.opsForValue().get(mobilePhone);
        if (!code.equals(redisCode)) {
            return Result.error(CommonConstants.ValidateCode.VALIDATE_ERROR, CommonConstants.ValidateCode.VALIDATE_ERROR_MESSAGE);
        }

        return Result.success(CommonConstants.Request.SUCCESS, CommonConstants.Request.SUCCESS_MESSAGE);
    }
}
