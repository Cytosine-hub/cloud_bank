package com.i2f.train.manager.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.i2f.train.manager.common.constants.ManagerConstants;
import com.i2f.train.manager.common.model.PageMessage;
import com.i2f.train.manager.common.util.ExcelUtil;
import com.i2f.train.manager.common.util.PagedListUtil;
import com.i2f.train.manager.model.FileMessage;
import com.i2f.train.manager.model.queryMap.FileQueryMap;
import com.i2f.train.manager.service.FileService;
import com.i2f.train.starter.common.constants.CommonConstants;
import com.i2f.train.starter.common.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: third_bird
 * @author: Cytosine
 * @create: 2022-04-07 10:21
 **/
@Service
@Slf4j
public class FileServiceImpl implements FileService {

    private static String path = "D:/files/";
//    private static String path = "/usr/local/bank/files/";

    @Override
    public Result getFilePathList(FileQueryMap queryMap) {
        List<FileMessage> fileList = new ArrayList<>();
        PageHelper.startPage(queryMap.getPage(), queryMap.getPageSize());
        scanFile(path, fileList);
        //根据类型返回文件列表
        if (StringUtils.isNotBlank(queryMap.getType())) {
            List<FileMessage> typedList = new ArrayList<>();
            for (FileMessage fileMessage : fileList) {
                if (queryMap.getType().equals(fileMessage.getType())) {
                    typedList.add(fileMessage);
                }
            }
            PageMessage pageMessage = PagedListUtil.pageList(typedList, queryMap.getPage(), queryMap.getPageSize());
            return Result.success(pageMessage, CommonConstants.Request.SUCCESS, CommonConstants.Request.SUCCESS_MESSAGE);
        }
        PageMessage pageMessage = PagedListUtil.pageList(fileList, queryMap.getPage(), queryMap.getPageSize());
        return Result.success(pageMessage, CommonConstants.Request.SUCCESS, CommonConstants.Request.SUCCESS_MESSAGE);
    }

    @Override
    public void downloadExcelFile(String fileName) {
        File file = null;
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        HttpServletResponse response = servletRequestAttributes.getResponse();

        List<FileMessage> fileList = new ArrayList<>();
        scanFile(path, fileList);
        for (FileMessage fileMessage : fileList) {
            if (fileName.equals(fileMessage.getFileName())) {
                //获取要下载的文件
                file = new File(fileMessage.getFilePath());
                break;
            }
        }
        if (file == null) {
            Result<Object> non = Result.builder().code("").message("文件不存在").build();
            try {
                response.getWriter().write(JSON.toJSONString(non));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //传入文件路径 表头和标题的行数 以及文件对应的model类读取文件内的数据
        //List<UserExportVo> userExportVos = ExcelUtil.importExcel(fileName, Integer.valueOf(1), Integer.valueOf(1), UserExportVo.class);
        //传入数据 文件名 sheet页名以及数据对应的model类下载excel文件
        //ExcelUtil.exportExcel(userExportVos, file.getName(), "员工信息", UserExportVo.class, file.getName(), response);
    }

    @Override
    public Result downloadMultipartFile(String fileName) {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        HttpServletResponse response = servletRequestAttributes.getResponse();
        //先获取文件
        File file = null;
        List<FileMessage> fileList = new ArrayList<>();
        scanFile(path, fileList);
        for (FileMessage fileMessage : fileList) {
            if (fileName.equals(fileMessage.getFileName())) {
                //获取要下载的文件
                file = new File(fileMessage.getFilePath());
                break;
            }
        }
        if (!file.exists()) {
            return Result.error(ManagerConstants.Code.FILE_NOT_FOUND, ManagerConstants.Message.FILE_NOT_FOUND_MESSAGE);
        }
        //设置请求头格式
        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        //设置文件长度
        response.setContentLength((int) file.length());
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes(StandardCharsets.UTF_8)));
        }

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));) {
            byte[] buff = new byte[1024];
            OutputStream os = response.getOutputStream();
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
        } catch (IOException e) {
            return Result.error(ManagerConstants.Code.FILE_FAILED, ManagerConstants.Message.FILE_FAILED_MESSAGE);
        }
        return Result.success(CommonConstants.Request.SUCCESS, CommonConstants.Request.SUCCESS_MESSAGE);
    }

    public void scanFile(String path, List<FileMessage> filesList) {
        //获取存放文件的根目录
        File root = new File(path);
        log.info("扫描文件路径：" + path);
        if (root == null){
            log.info("文件路径错误：" + path + "找不到");
        }
        File[] files = root.listFiles();
        //递归遍历文件列表
        for (File file : files) {
            if (file.isDirectory()) {
                scanFile(path + file.getName() + "/", filesList);
            } else {
                //记录文件信息
                FileMessage fileMessage = new FileMessage(file.getPath(), file.getName(), null);
                String fileName = file.getName();

                if (fileName.contains(CommonConstants.FileType.SALE)) {
                    fileMessage.setType(CommonConstants.FileType.SALE);
                }
                if (fileName.contains(CommonConstants.FileType.SALE_BACK)) {
                    fileMessage.setType(CommonConstants.FileType.SALE_BACK);
                }
                if (fileName.contains(CommonConstants.FileType.SUBSCRIBE)) {
                    fileMessage.setType(CommonConstants.FileType.SUBSCRIBE);
                }
                if (fileName.contains(CommonConstants.FileType.SUBSCRIBE_BACK)) {
                    fileMessage.setType(CommonConstants.FileType.SUBSCRIBE_BACK);
                }
                filesList.add(fileMessage);
            }
        }
    }

    @Override
    public Map<String, Integer> countFile() {
        List<FileMessage> list = new ArrayList<>();
        Map<String, Integer> typeMap = new HashMap<>();
        typeMap.put(CommonConstants.FileType.SALE, 0);
        typeMap.put(CommonConstants.FileType.SALE_BACK, 0);
        typeMap.put(CommonConstants.FileType.SUBSCRIBE, 0);
        typeMap.put(CommonConstants.FileType.SUBSCRIBE_BACK, 0);

        scanFile(path, list);
        //统计数量
        for (FileMessage fileMessage : list) {
            String type = fileMessage.getType();
            if (CommonConstants.FileType.SALE.equals(type)) {
                Integer integer = typeMap.get(CommonConstants.FileType.SALE) + 1;
                typeMap.put(CommonConstants.FileType.SALE, integer);
            }
            if (CommonConstants.FileType.SALE_BACK.equals(type)) {
                Integer integer = typeMap.get(CommonConstants.FileType.SALE_BACK) + 1;
                typeMap.put(CommonConstants.FileType.SALE_BACK, integer);
            }
            if (CommonConstants.FileType.SUBSCRIBE.equals(type)) {
                Integer integer = typeMap.get(CommonConstants.FileType.SUBSCRIBE) + 1;
                typeMap.put(CommonConstants.FileType.SUBSCRIBE, integer);
            }
            if (CommonConstants.FileType.SUBSCRIBE_BACK.equals(type)) {
                Integer integer = typeMap.get(CommonConstants.FileType.SUBSCRIBE_BACK) + 1;
                typeMap.put(CommonConstants.FileType.SUBSCRIBE_BACK, integer);
            }
        }
        return typeMap;
    }
}
