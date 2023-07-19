package com.i2f.train.starter.model.vo;

import com.i2f.train.starter.model.TransferRecord;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: hjx
 * @Date: 2022/04/12/23:50
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferDetailsVO {
    private String name;
    private String type;
    private TransferRecord transferRecord;
}
