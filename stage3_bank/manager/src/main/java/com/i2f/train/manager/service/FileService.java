package com.i2f.train.manager.service;

import com.i2f.train.manager.model.queryMap.FileQueryMap;
import com.i2f.train.starter.common.model.Result;

import java.util.List;
import java.util.Map;

/**
 * @program: third_bird
 * @author: Cytosine
 * @create: 2022-04-07 10:05
 **/
public interface FileService {

    /**
     * 获取文件列表的路径
     * @return
     */
    Result getFilePathList(FileQueryMap queryMap);

    /**
     * 下载Excel文件
     * @param fileName
     */
    void downloadExcelFile(String fileName);

    /**
     * 下载文件
     * @param fileName
     * @return
     */
    Result downloadMultipartFile(String fileName);

    /**
     * 统计文件数量
     * @return
     */
    public Map<String, Integer> countFile();

}
