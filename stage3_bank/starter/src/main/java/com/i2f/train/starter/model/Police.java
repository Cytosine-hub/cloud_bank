package com.i2f.train.starter.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program: third_bird
 * @author: Cytosine
 * @create: 2022-03-30 11:28
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Police implements Serializable {
    private String username;
    private String personalId;
}
