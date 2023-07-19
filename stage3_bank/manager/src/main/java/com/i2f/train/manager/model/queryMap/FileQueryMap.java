package com.i2f.train.manager.model.queryMap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: third_bird
 * @author: Cytosine
 * @create: 2022-04-09 20:54
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileQueryMap {
    private String type;
    private int page;
    private int pageSize;
}
