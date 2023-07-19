package com.i2f.train.account.service.impl;

import com.i2f.train.account.feign.AccountOpenFeign;
import com.i2f.train.account.feign.UserAccountRelationOpenFeign;
import com.i2f.train.account.feign.UserProviderOpenFeign;
import com.i2f.train.account.service.TransferService;
import com.i2f.train.starter.common.constants.CommonConstants;
import com.i2f.train.starter.common.model.Result;
import com.i2f.train.starter.model.Account;
import com.i2f.train.starter.model.TransferRecord;
import com.i2f.train.starter.model.UserAccountRelation;
import com.i2f.train.starter.model.vo.TransferDetailsVO;
import com.i2f.train.starter.model.vo.TransferDetailVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: hjx
 * @Date: 2022/03/30/17:26
 * @Description:
 */
@Service
public class TransferServiceImpl implements TransferService {

    @Resource
    AccountOpenFeign accountOpenFeign;

    @Resource
    UserAccountRelationOpenFeign userAccountRelationOpenFeign;

    @Resource
    UserProviderOpenFeign userProviderOpenFeign;


    @Override
    public Result transferDetail(String inOrOut, String month, String card, String type, Timestamp startTime, Timestamp endTime, String userId) {
        ArrayList<TransferRecord> transferList = new ArrayList<>();
        ArrayList<Object> accountList = new ArrayList<>();
        ArrayList<Object> ids = new ArrayList<>();
        ArrayList<TransferDetailsVO> transferDetailsVOS = new ArrayList<>();
        BigDecimal totalInMoney = new BigDecimal(0);
        BigDecimal outMoney = new BigDecimal(0);
        BigDecimal otherMoney = new BigDecimal(0);
        BigDecimal totalOutMoney = new BigDecimal(0);
        List<UserAccountRelation> userAccountRelations = userAccountRelationOpenFeign.selectAccountByUserId(userId);
        String account = userProviderOpenFeign.selectByUserId(userId).getAccountId();
        for (UserAccountRelation userAccountRelation:userAccountRelations) {
            String accountId = userAccountRelation.getAccountId();
            accountList.add(accountId);
        }
        accountList.add(account);
        if (!StringUtils.isBlank(card)){
            List<TransferRecord> transferRecords = accountOpenFeign.queryTransfer(inOrOut, month, card, type, startTime, endTime);

            for (TransferRecord transferRecord:transferRecords
                 ) {
                TransferDetailsVO transferDetailsVO = new TransferDetailsVO();
                if (CommonConstants.UpdateMoney.TRANSFER_CODE.equals(transferRecord.getType())) {
                    if (card.equals(transferRecord.getInAccountId())) {
                        Account transferAccount = accountOpenFeign.queryAccount(transferRecord.getOutAccountId());
                        transferDetailsVO.setType(CommonConstants.UpdateMoney.IN_CODE);
                        transferDetailsVO.setTransferRecord(transferRecord);
                        transferDetailsVO.setName(transferAccount.getUsername());
                    }
                    if (card.equals(transferRecord.getOutAccountId())) {
                        Account transferAccount = accountOpenFeign.queryAccount(transferRecord.getInAccountId());
                        transferDetailsVO.setType(CommonConstants.UpdateMoney.OUT_CODE);
                        transferDetailsVO.setTransferRecord(transferRecord);
                        transferDetailsVO.setName(transferAccount.getUsername());
                    }
                } else if (CommonConstants.UpdateMoney.SALE_CODE.equals(transferRecord.getType())) {
                    if (card.equals(transferRecord.getInAccountId())) {
                        transferDetailsVO.setType(CommonConstants.UpdateMoney.SALE_CODE);
                        transferDetailsVO.setTransferRecord(transferRecord);
                        transferDetailsVO.setName("赎回");
                    }
                }

                else if (CommonConstants.UpdateMoney.SUBSCRIBE.equals(transferRecord.getType())) {
                    if (card.equals(transferRecord.getInAccountId())) {
                        transferDetailsVO.setType(CommonConstants.UpdateMoney.SUBSCRIBE);
                        transferDetailsVO.setTransferRecord(transferRecord);
                        transferDetailsVO.setName("申购退款");
                    }
                    if (card.equals(transferRecord.getOutAccountId())) {
                        transferDetailsVO.setType(CommonConstants.UpdateMoney.SUBSCRIBE);
                        transferDetailsVO.setTransferRecord(transferRecord);
                        transferDetailsVO.setName("申购");
                    }
                } else {
                    Account transferAccount = accountOpenFeign.queryAccount(transferRecord.getInAccountId());
                    transferDetailsVO.setName(transferAccount.getUsername());
                    transferDetailsVO.setTransferRecord(transferRecord);
                }
                if (card.equals(transferRecord.getInAccountId())){
                    totalInMoney = totalInMoney.add(transferRecord.getMoney());
                }if (card.equals(transferRecord.getOutAccountId())){
                    outMoney = outMoney.add(transferRecord.getMoney());
                    if (transferRecord.getOtherMoney() != null){
                        otherMoney = otherMoney.add(transferRecord.getOtherMoney());
                    }
                    totalOutMoney = outMoney.add(otherMoney);
                }
                transferDetailsVOS.add(transferDetailsVO);
            }
            TransferDetailVO transferDetailVO = new TransferDetailVO(accountList, transferDetailsVOS, totalInMoney, totalOutMoney);
            return Result.success(transferDetailVO);
        }else {
            for (int i = 0; i < accountList.size(); i++) {
                List<TransferRecord> transferRecords = accountOpenFeign.queryTransfer(inOrOut, month, String.valueOf(accountList.get(i)), type, startTime, endTime);
                for (TransferRecord transferRecord:transferRecords) {
                    if (transferList.size() > 0){
                        for (int j = 0; j < transferList.size(); j++) {
                            TransferRecord transfer = transferList.get(j);
                            String id = transfer.getId();
                            ids.add(id);
                        }
                        if (!ids.contains(transferRecord.getId())){
                            transferList.add(transferRecord);
                        }
                    }else {
                        transferList.add(transferRecord);
                    }
                }
            }
            Collections.sort(transferList);
            for (TransferRecord transferRecord:transferList) {
                TransferDetailsVO transferDetailsVO1 = new TransferDetailsVO();
                for (int i = 0; i < accountList.size(); i++) {
                    if (CommonConstants.UpdateMoney.TRANSFER_CODE.equals(transferRecord.getType())) {
                        if (String.valueOf(accountList.get(i)).equals(transferRecord.getInAccountId())) {
                            Account transferAccount = accountOpenFeign.queryAccount(transferRecord.getOutAccountId());
                            transferDetailsVO1.setType(CommonConstants.UpdateMoney.IN_CODE);
                            transferDetailsVO1.setTransferRecord(transferRecord);
                            transferDetailsVO1.setName(transferAccount.getUsername());
                        }
                        if (String.valueOf(accountList.get(i)).equals(transferRecord.getOutAccountId())) {
                            Account transferAccount = accountOpenFeign.queryAccount(transferRecord.getInAccountId());
                            transferDetailsVO1.setType(CommonConstants.UpdateMoney.OUT_CODE);
                            transferDetailsVO1.setTransferRecord(transferRecord);
                            transferDetailsVO1.setName(transferAccount.getUsername());
                        }
                    }
                    else if (CommonConstants.UpdateMoney.SALE_CODE.equals(transferRecord.getType())) {
                        if (String.valueOf(accountList.get(i)).equals(transferRecord.getInAccountId())) {
                            transferDetailsVO1.setType(CommonConstants.UpdateMoney.SALE_CODE);
                            transferDetailsVO1.setTransferRecord(transferRecord);
                            transferDetailsVO1.setName("赎回");
                        }
                    }
                    else if (CommonConstants.UpdateMoney.SUBSCRIBE.equals(transferRecord.getType())) {
                        if (String.valueOf(accountList.get(i)).equals(transferRecord.getInAccountId())) {
                            transferDetailsVO1.setType(CommonConstants.UpdateMoney.SUBSCRIBE);
                            transferDetailsVO1.setTransferRecord(transferRecord);
                            transferDetailsVO1.setName("申购退款");
                        }
                        if (String.valueOf(accountList.get(i)).equals(transferRecord.getOutAccountId())) {
                            transferDetailsVO1.setType(CommonConstants.UpdateMoney.SUBSCRIBE);
                            transferDetailsVO1.setTransferRecord(transferRecord);
                            transferDetailsVO1.setName("申购");
                        }
                    }
                    else {
                        Account transferAccount = accountOpenFeign.queryAccount(transferRecord.getInAccountId());
                        transferDetailsVO1.setName(transferAccount.getUsername());
                        transferDetailsVO1.setType(transferRecord.getType());
                        transferDetailsVO1.setTransferRecord(transferRecord);
                    }
                    if (accountList.get(i).equals(transferRecord.getInAccountId())){
                        totalInMoney = totalInMoney.add(transferRecord.getMoney());
                    }if (accountList.get(i).equals(transferRecord.getOutAccountId())){
                        outMoney = outMoney.add(transferRecord.getMoney());
                        if (transferRecord.getOtherMoney() != null){
                            otherMoney = otherMoney.add(transferRecord.getOtherMoney());
                        }
                        totalOutMoney = outMoney.add(otherMoney);
                    }
                }
                transferDetailsVOS.add(transferDetailsVO1);
            }
            TransferDetailVO transferDetailVO = new TransferDetailVO(accountList, transferDetailsVOS, totalInMoney, totalOutMoney);
            return Result.success(transferDetailVO);
        }
    }

    @Override
    public Result transfer(String id) {
        TransferRecord transferRecord = accountOpenFeign.queryTransferDetail(id);
        String inAccountId = transferRecord.getInAccountId();
        String outAccountId = transferRecord.getOutAccountId();
        Account inAccount = accountOpenFeign.queryAccount(inAccountId);
        Account outAccount = accountOpenFeign.queryAccount(outAccountId);
        String inAccountUsername = inAccount.getUsername();
        String inAccountBankName = inAccount.getBankName();
        String outAccountUsername = outAccount.getUsername();
        String outAccountBankName = outAccount.getBankName();
        TransferDetailVO transferDetailVO = new TransferDetailVO(transferRecord, inAccountBankName, outAccountBankName, inAccountUsername, outAccountUsername);
        return Result.success(transferDetailVO);
    }
}
