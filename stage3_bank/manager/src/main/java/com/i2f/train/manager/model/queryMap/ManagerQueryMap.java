package com.i2f.train.manager.model.queryMap;

import com.i2f.train.manager.model.Manager;
import lombok.Data;

import java.io.Serializable;

/**
 * @program: third_bird
 * @author: Cytosine
 * @create: 2022-03-31 15:28
 **/
@Data
public class ManagerQueryMap implements Serializable {
    private String name;
    private String level;
    private int page;
    private int pageSize;

    public Manager toManager(){
        Manager manager = new Manager();
        manager.setName(name);
        manager.setLevel(level);
        return manager;
    }
}
