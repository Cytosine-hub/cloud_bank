package com.i2f.train.codeProvider.controller;

import com.i2f.train.codeProvider.service.CodeService;
import com.i2f.train.starter.common.model.Result;
import com.i2f.train.starter.common.util.ImageCodeUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @Description 验证码一:个位数+-*运算
 * @Author lu yuan xin
 * @Date 2022/3/29-15:11
 * @Copyright Home
 */
@RestController
@RequestMapping("/code")
@CrossOrigin
public class ValidateCodeController {

    @Resource
    CodeService codeService;

    @RequestMapping("/getImageCode")
    protected void doGet(HttpServletResponse resp, String imageId) {
        codeService.getImageCode(resp, imageId);
    }

    @GetMapping("/getPhoneCode")
    public Result getPhoneCode(String mobilePhone) {
        return codeService.getPhoneCode(mobilePhone);
    }

    @GetMapping("/checkPhoneCode")
    public Result checkPhoneCode(String mobilePhone, String code) {
        return codeService.checkPhoneCode(mobilePhone, code);
    }
}