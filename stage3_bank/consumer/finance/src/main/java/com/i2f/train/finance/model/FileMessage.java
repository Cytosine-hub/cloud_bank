package com.i2f.train.finance.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @program: third_bird
 * @author: Cytosine
 * @create: 2022-04-07 11:05
 **/
@Data
@AllArgsConstructor
public class FileMessage {
    private String filePath;
    private String fileName;
}
