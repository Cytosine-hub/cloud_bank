package com.i2f.train.finance.common.util;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * @program: stage3
 * @author: Cytosine
 * @create: 2022-03-19 14:22
 **/
public class ExcelUtil {

    private static String filePath = "D:/files/";
    /**
     * excel导出  导出指定类型的数据
     *
     * @param list  数据
     * @param title 标题
     * @param sheetName sheet名称
     * @param pojoClass pojo类型
     * @param fileName 文件名
     * @param isCreateHeader  是否创建表头
     * @param response
     */
    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName, boolean isCreateHeader, HttpServletResponse response) {
        ExportParams exportParams = new ExportParams(title, sheetName, ExcelType.XSSF);
        exportParams.setCreateHeadRows(isCreateHeader);
        defaultExport(list, pojoClass, fileName, response, exportParams);
    }


    /**
     *
     * excel导出 默认设置表头
     * @param list 导出数据
     * @param title 标题
     * @param sheetName sheet名称
     * @param pojoClass pojo类型
     * @param fileName 文件名
     * @param response
     */
    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName, HttpServletResponse response){
        ExportParams exportParams = new ExportParams(title, sheetName, ExcelType.XSSF);
        defaultExport(list, pojoClass, fileName,response, exportParams);
    }

    /**
     * 导出不指定类型的数据
     * @param list  数据
     * @param fileName 文件名
     * @param response
     */
    public static void exportExcel(List<Map<String, Object>> list , String fileName, HttpServletResponse response){
        defaultExport(list, fileName);
    }

    /**
     * 默认的excel导出 导出指定类型的数据
     * @param list  excel数据
     * @param pojoClass 导出的数据对应的类型
     * @param fileName 导出的文件名
     * @param response
     * @param exportParams  导出参数
     */
    public static void defaultExport(List<?> list, Class<?> pojoClass, String fileName, HttpServletResponse response, ExportParams exportParams) {
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, list);
        downLoadExcel(fileName, workbook);
    }

    /**
     * 默认的excel导出  导出的数据类型为Map  应该是不设置列名  任何数据都可以导出
     * @param list
     * @param fileName
     */
    public static void defaultExport(List<Map<String, Object>> list, String fileName){
        Workbook workbook = ExcelExportUtil.exportExcel(list, ExcelType.HSSF);
        downLoadExcel(fileName, workbook);
    }
    /**
     * 下载 Excel
     *
     * @param fileName 文件名
     * @param workbook excel表格数据
     */
    private static void downLoadExcel(String fileName, Workbook workbook) {
//        try {
//            //设置编码格式
//            response.setCharacterEncoding("UTF-8");
//            //设置请求头格式
//            response.setHeader("content-type", "application/vnd.ms-excel");
//            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName + "." + ExcelTypeEnum.XLSX.getValue(), "UTF-8"));
//            //输出数据
//            workbook.write(response.getOutputStream());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        //------------------->保存文件到指定目录
        try {
            FileOutputStream outputStream = new FileOutputStream(filePath + fileName + "." + ExcelTypeEnum.XLSX.getValue());
            workbook.write(outputStream);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 根据文件路径导入excel
     * @param filePath  文件路径
     * @param titleRows 表标题的行数
     * @param headerRows 表头行数
     * @param pojoClass excel对应的实体类
     * @param <T>
     * @return
     */

    public static <T> List<T> importExcel(String filePath, Integer titleRows, Integer headerRows, Class<T> pojoClass) {
        //判断文件是否存在
        if (StringUtils.isBlank(filePath)) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(new File(filePath), pojoClass, params);
        } catch (NoSuchElementException e) {
            throw new RuntimeException("模板不能为空");
        } catch (Exception e) {
            e.printStackTrace();

        }
        return list;
    }

    /**
     * 根据接收的excel文件来导入 并封装成实体类
     * @param file 上传的文件
     * @param titleRows 表标题的行数
     * @param headerRows 表头行数
     * @param pojoClass 实体类
     * @param <T>
     * @return
     */
    public static <T> List<T> importExcel(MultipartFile file, Integer titleRows, Integer headerRows, Class<T> pojoClass) {
        if (file == null) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(file.getInputStream(), pojoClass, params);
        } catch (NoSuchElementException e) {
            throw new RuntimeException("excel文件不能为空");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());

        }
        return list;
    }
    /**
     * Excel 类型枚举
     */
    enum ExcelTypeEnum {
        XLS("xls"), XLSX("xlsx");
        private String value;

        ExcelTypeEnum(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }


}
