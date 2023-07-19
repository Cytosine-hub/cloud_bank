package com.i2f.train.starter.model.vo;

import com.i2f.train.starter.model.TransferRecord;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import com.i2f.train.starter.model.vo.TransferDetailsVO;
/**
 * Created with IntelliJ IDEA.
 *
 * @Author: hjx
 * @Date: 2022/03/31/15:17
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferDetailVO implements Serializable {
    private List<Object> accountList;
    private List<TransferRecord> transferRecords;

    private List<TransferDetailsVO> transferDetailsVOS;

    private TransferRecord transferRecord;
    private String inAccountBankName;
    private String outAccountBankName;
    private String inAccountUsername;
    private String outAccountUsername;
    private BigDecimal totalInMoney;
    private BigDecimal totalOutMoney;
    public TransferDetailVO(TransferRecord transferRecord, String inAccountBankName, String outAccountBankName, String inAccountUsername, String outAccountUsername) {
        this.transferRecord = transferRecord;
        this.inAccountBankName = inAccountBankName;
        this.outAccountBankName = outAccountBankName;
        this.inAccountUsername = inAccountUsername;
        this.outAccountUsername = outAccountUsername;
    }

    public TransferDetailVO(List<TransferRecord> transferRecords, BigDecimal totalInMoney, BigDecimal totalOutMoney, List<Object> accountList) {
        this.transferRecords = transferRecords;
        this.totalInMoney = totalInMoney;
        this.totalOutMoney = totalOutMoney;
        this.accountList = accountList;
    }

    public TransferDetailVO(List<Object> accountList, List<TransferDetailsVO> transferDetailsVOS, BigDecimal totalInMoney, BigDecimal totalOutMoney) {
        this.accountList = accountList;
        this.transferDetailsVOS = transferDetailsVOS;
        this.totalInMoney = totalInMoney;
        this.totalOutMoney = totalOutMoney;
    }

}
