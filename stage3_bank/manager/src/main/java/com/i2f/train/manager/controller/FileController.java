package com.i2f.train.manager.controller;

import com.i2f.train.manager.model.queryMap.FileQueryMap;
import com.i2f.train.manager.service.FileService;
import com.i2f.train.starter.common.model.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @program: third_bird
 * @author: Cytosine
 * @create: 2022-04-07 10:56
 **/
@RestController
@RequestMapping("/file")
public class FileController {

    @Resource
    FileService fileService;

    @PostMapping("/list")
    public Result getFileList(@RequestBody FileQueryMap queryMap){
        return fileService.getFilePathList(queryMap);
    }

    @GetMapping("/download")
    public void downLoad(String fileName){
        fileService.downloadMultipartFile(fileName);
    }
}
