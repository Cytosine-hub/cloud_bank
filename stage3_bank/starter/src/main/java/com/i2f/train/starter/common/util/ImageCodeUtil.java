package com.i2f.train.starter.common.util;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @program: third_bird
 * @author: Cytosine
 * @create: 2022-03-29 22:19
 **/
public class ImageCodeUtil {
    /**
     * 图片尺寸
     */
    private static int width = 150, height = 45;
    /**
     * 干扰线数量
     */
    private static int interfereLineNum = 50;
    /**
     * 运算符字体
     */
    private static String fontString = "楷体";
    /**
     * 验证码字符串大小
     */
    private static int fontSize =35;

    private static final String CAPTCHA_KEY = "CAPTCHA_CODE";

    private static Random random = new Random();


    public static Map<String,Object> generateImage(){
        Map<String, Object> resultMap = new HashMap<>();
        //创建图像
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        //要生成的图形上下文并设置随机颜色
        Graphics2D graphics2D = image.createGraphics();
        graphics2D.setColor(getRandomColor(190, 255));
        //填充矩形
        graphics2D.fillRect(0, 0, width, height);

        //设置字体
        Font font = new Font(fontString, Font.PLAIN, fontSize);
        graphics2D.setFont(font);

        //画点干扰线
        for (int i = 0; i <10; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xx = random.nextInt(200);
            int yy = random.nextInt(180);
            //颜色也随机一下
            graphics2D.setColor(getRandomColor());
            graphics2D.drawLine(x, y, x + xx, y + yy);
        }

        //验证码10以内 + - * 运算
        int num1 = (int) ((Math.random() * 10) + 1);
        int num2 = (int) ((Math.random() * 10) + 1);
        //运算方式随机
        int s = random.nextInt(3);
        String operationName = "";
        //运算的结果
        int result = 0;
        switch (s) {
            case 0:
                operationName = "+";
                result = num1 + num2;
                break;
            case 1:
                operationName = "-";
                result = (num1 - num2) > 0 ? (num1 - num2) : (num2 - num1);
                break;
            case 2:
                operationName = "*";
                result = num1 * num2;
                break;
            default:
                break;
        }
        //写在图片中的字符串验证码运算
        String code = operationName == "-" ?
                ((num1 - num2) > 0 ? (num1 + operationName + num2 + "=？")
                        : (num2 + operationName + num1 + "=？"))
                : (num1 + operationName + num2 + "=？");
        graphics2D.setColor(new Color(22 + random.nextInt(150), 22 + random.nextInt(150), 22 + random.nextInt(150)));

        FontRenderContext context = graphics2D.getFontRenderContext();
        Rectangle2D rectangle2D = font.getStringBounds(code, context);
        //验证码字符串在图片中坐标计算
        double x = (width - rectangle2D.getWidth()) / 2;
        double y = (height - rectangle2D.getHeight()) / 2;
        double upY = -rectangle2D.getY();
        double baseY = y + upY;

        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics2D.drawString(code, (int) x > 0 ? (int) x : 1, (int) baseY);

        //图像生效、释放此图形的上下文并释放它所使用的所有系统资源
        graphics2D.dispose();
        resultMap.put("image", image);
        resultMap.put("value", String.valueOf(result));
        return resultMap;
    }

    /**
     * 生成随机的颜色（根据传入的两个整数1-255值取色，两数差值越小 颜色越亮）
     *
     * @param a
     * @param b
     * @return Color
     */
    private static Color getRandomColor(int a, int b) {
        int col = 255;
        if (a > col) {
            a = col;
        }
        if (b > col) {
            b = col;
        }
        Color color = new Color(a + random.nextInt(b - a), a + random.nextInt(b - a), a + random.nextInt(b - a));
        return color;
    }

    /**
     * 完全随机的颜色
     *
     * @return
     */
    private static Color getRandomColor() {
        return new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

}
