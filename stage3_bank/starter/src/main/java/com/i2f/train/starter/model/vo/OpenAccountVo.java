package com.i2f.train.starter.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Wqsh
 * @version 1.0
 * @date 2022/3/30 17:34
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpenAccountVo implements Serializable {
    private String username;
    private String personalId;
    private String payPassword;
}
