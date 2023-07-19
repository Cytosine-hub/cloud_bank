package com.i2f.train.manager.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @program: third_bird
 * @author: Cytosine
 * @create: 2022-04-07 11:05
 **/
@Data
@AllArgsConstructor
public class FileMessage implements Serializable {
    private String filePath;
    private String fileName;
    private String type;
}
