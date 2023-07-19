package com.i2f.train.starter.common.constants;

import java.math.BigDecimal;

/**
 * @author cw
 * @date 2022年02月24日 17:38
 */
public class CommonConstants {
    public interface Request {
        String SUCCESS = "S000000";
        String SUCCESS_MESSAGE = "操作成功";
        String FAILED="E000000";
        String FAILED_MESSAGE="操作失败";
        String INTERNAL_ERROR = "E000001";
        String INTERNAL_ERROR_MESSAGE = "服务器异常，请稍后再试";
        String HYSTRIX_FALLBACK_ERROR = "E000002";
        String HYSTRIX_FALLBACK_ERROR_MESSAGE = "访问超时,请稍后再试";
        String SENTINEL_BLOCK_ERROR = "E000003";
        String SENTINEL_BLOCK_ERROR_MESSAGE = "服务限流保护中，请稍后再试";
        String PARAMETER_ERROR = "E000004";
        String PARAMETER_ERROR_MESSAGE = "参数不合法，请重试";
        String CODE_FAILED="E000005";
        String CODE_MESSAGE="验证码过期";
        String CODE_FAILED_MESSAGE="验证码错误";
        String REGISTER_FAILED="E000006";
        String REGISTER_FAILED_MESSAGE="注册失败";
        String REGISTER_USER_MESSAGE="用户已存在，请前往登录！";
        String REGISTER_PHONE_MESSAGE="手机号码格式不正确！";
        String REGISTER_PHONENULL_MESSAGE="手机号码不能为空！";
        String REGISTER_PASSWORD_MESSAGE="密码为空，请输入密码！";
        String SIGIN_REPEAT_ERROR="E000007";
        String SIGIN_REPEAT_ERROR_MESSAGE="你已签到，请勿重复签到";
        String BONUSPOINTDRAW_ERROR="E000008";
        String BONUSPOINTDRAW_ERROR_MESSAGE="积分不足";
        String REGISTER_SUCCESS="S000001";
        String REGISTER_SUCCESS_MESSAGE="注册成功";
        String LOGIN_SUCCESS="E000002";
        String LOGIN_SUCCESS_MESSAGE="登录成功";
        String LOGIN_FAILED="E000011";
        String LOGIN_USER_MESSAGE="用户未注册";
        String LOGIN_PASSWORD_MESSAGE="密码错误";
        String PRODUCTION_STOCK_ERROR="E000009";
        String PRODUCTION_STOCK_ERROR_MESSAGE="库存数量不足";
        String PRODUCTION_PRICE_ERROR="E000010";
        String PRODUCTION_PRICE_ERROR_MESSAGE="积分不足";
        String TOKEN_ERROR = "E000011";
        String TOKEN_ERROR_MESSAGE = "token验证失败！";
        String FINANCE_AMOUNT_ERROR="E000012";
        String FINANCE_AMOUNT_ERROR_MESSAGE="赎回份额错误";
        String PAYPASSWORD_ERROR="E000014";
        String PAYPASSWORD_ERROR_MESSAGE="支付密码错误";
        String PRODUCTION_DETAIL_ERROR="E000013";
        String EVALUATE_FAIL = "E000015";
        String EVALUATE_FAIL_MESSAGE = "风险类型评估失败！";
        String WRITE_OFF_FAIL = "E000016";
        String WRITE_OFF_FAIL_MESSAGE = "注销失败";
        String MOBILE_PHONE_MODIFY_FAIL = "E000017";
        String MOBILE_PHONE_MODIFY_FAIL_MESSAGE = "注销失败";

    }

    /**
     * 积分商品
     */
    public interface Production {
        /**
         * 上架stateCode
         */
        String UP_PRODUCTION_STATE_CODE = "0";
    }

    /**
     * 验证码
     */
    public interface VerificationCode {
        /**
         * 验证码已过期CODE
         */
        String NOT_GENERATE_CODE = "0005";
        /**
         * 验证码已过期MESSAGE
         */
        String NOT_GENERATE_MESSAGE ="验证码已过期";
        /**
         * 验证码输入错误CODE
         */
        String IMPUTE_ERROR_CODE = "0006";
        /**
         * 验证码输入错误MESSAGE
         */
        String IMPUTE_ERROR_MESSAGE = "验证码输入错误";
    }

    /**
     * 信息
     */
    public interface Message {
        /**
         * 添加失败CODE
         */
        String ADD_FAILED_CODE = "1001";
        /**
         * 添加失败MESSAGE
         */
        String ADD_FAILED_MESSAGE = "添加失败";
        /**
         * 修改失败CODE
         */
        String CHANGE_FAILED_CODE = "1002";
        /**
         * 修改失败MESSAGE
         */
        String CHANGE_FAILED_MESSAGE = "修改失败";
    }

    /**
     * 存在
     */
    public interface Existence{
        /**
         * 已存在CODE
         */
        String EXISTENCE_CODE = "0007";
        /**
         * 已存在MESSAGE
         */
        String EXISTENCE_MESSAGE ="已存在";
        /**
         * 未存在CODE
         */
        String NONEXISTENCE_CODE = "0008";
        /**
         * 未存在MESSAGE
         */
        String NONEXISTENCE_MESSAGE = "未存在";
        /**
         * 不是本人的CODE
         */
        String NOT_OWN_CODE = "0009";
        /**
         * 不是本人的MESSAGE
         */
        String NOT_OWN_MESSAGE ="非本人";
    }

    /**
     * 银行卡
     */
    public interface Account {
        /**
         * 冻结状态
         */
        String FREEZE_CODE ="1";
        /**
         * 主卡标识符
         */
        String IS_MAIN_CODE ="1";
        /**
         * 非主卡标识符
         */
        String IS_NOT_MAIN_CODE ="0";
        /**
         * 银行卡类型为一类卡
         */
        String ACCOUNT_TYPE_FIRST_CODE ="1";
        /**
         * 一类卡CODE
         */
        String FIRST_CODE = "0009";
        /**
         * 一类卡MESSAGE
         */
        String FIRST_MESSAGE = "银行卡为一类卡";
        /**
         * 银行卡为二类卡CODE
         */
        String SECOND_CODE = "0010";
        /**
         * 银行卡为二类卡MESSAGE
         */
        String SECOND_MESSAGE = "银行卡为二类卡";
        /**
         * 银行卡绑定成功CODE
         */
        String BIND_CARD_SUCCESS_CODE = "0011";
        /**
         * 银行卡绑定成功MESSAGE
         */
        String BIND_CARD_SUCCESS_MESSAGE = "该银行卡绑定成功";
        /**
         * 银行卡绑定失败CODE
         */
        String BIND_CARD_FAILED_CODE = "0012";
        /**
         * 银行卡绑定失败MESSAGE
         */
        String BIND_CARD_FAILED_MESSAGE ="该银行卡绑定失败";
        /**
         * 解除银行卡绑定成功CODE
         */
        String REMOVE_CARD_SUCCESS_CODE = "0013";
        /**
         * 解除银行卡绑定成功MESSAGE
         */
        String REMOVE_CARD_SUCCESS_MESSAGE = "解除银行卡绑定成功";
        /**
         * 解除银行卡绑定失败CODE
         */
        String REMOVE_CARD_FAILED_CODE = "0014";
        /**
         * 解除银行卡绑定败MESSAGE
         */
        String REMOVE_CARD_FAILED_MESSAGE ="解除银行卡绑定败";
        /**
         * 支付银行卡卡号为空CODE
         */
        String PAY_ACCOUNT_NULL_CODE = "0015";
        /**
         * 支付银行卡卡号为空MESSAGE
         */
        String PAY_ACCOUNT_NULL_MESSAGE ="支付银行卡卡号为空";
        /**
         * 收款银行卡卡号为空CODE
         */
        String RECEIVER_ACCOUNT_NULL_CODE = "0016";
        /**
         * 收款银行卡卡号为空MESSAGE
         */
        String RECEIVER_ACCOUNT_NULL_MESSAGE ="收款银行卡卡号为空";
    }

    /**
     * 资金变动
     */
    public interface UpdateMoney {
        /**
         * 手续费存储账号
         */
        String BANK_ACCOUNT = "0000000000000000000";
        /**
         * 收款卡号错误CODE
         */
        String RECEIVER_CARD_ERROR_CODE = "0055";
        /**
         * 收款卡号错误MESSAGE
         */
        String RECEIVER_CARD_ERROR_MESSAGE = "收款卡号错误";
        /**
         * 收款人姓名错误CODE
         */
        String RECEIVER_NAME_ERROR_CODE = "0056";
        /**
         * 收款人姓名错误MESSAGE
         */
        String RECEIVER_NAME_ERROR_MESSAGE = "收款人姓名错误";
        /**
         * 支付密码错误CODE
         */
        String PAY_PASSWORD_ERROR_CODE = "0057";
        /**
         * 支付密码错误MESSAGE
         */
        String PAY_PASSWORD_ERROR_MESSAGE = "支付密码错误";
        /**
         * 支付卡号已经被冻结CODE
         */
        String PAY_FREEZE_ERROR_CODE = "0058";
        /**
         * 支付卡号已经被冻结MESSAGE
         */
        String PAY_FREEZE_ERROR_MESSAGE = "支付卡号已经被冻结";
        /**
         * 余额不足CODE
         */
        String BALANCE_NOT_ENOUGH_CODE = "0059";
        /**
         * 余额不足MESSAGE
         */
        String BALANCE_NOT_ENOUGH_MESSAGE = "余额不足";
        /**
         * 充值CODE
         */
        String RECHARGE_CODE = "1";
        /**
         * 充值MESSAGE
         */
        String RECHARGE_MESSAGE = "充值";
        /**
         * 提现CODE
         */
        String WITHDRAW_CODE = "2";
        /**
         * 提现MESSAGE
         */
        String WITHDRAW_MESSAGE = "提现";
        /**
         * 转账CODE
         */
        String TRANSFER_CODE = "3";
        /**
         * 转账MESSAGE
         */
        String TRANSFER_MESSAGE = "转账";
        /**
         * 转账收入
         */
        String IN_CODE = "3-0";
        /**
         * 转账支出
         */
        String OUT_CODE = "3-1";
        /**
         * 赎回CODE
         */
        String SALE_CODE = "4";
        /**
         * 赎回MESSAGE
         */
        String SALE_MESSAGE = "赎回";
        /**
         * 申购
         */
        String SUBSCRIBE = "5";
        /**
         * 申购MESSAGE
         */
        String SUBSCRIBE_MESSAGE = "申购";
        /**
         * 当日限额不足CODE
         */
        String DAILY_MONEY_NOT_ENOUGH_CODE = "0060";
        /**
         * 当日限额不足MESSAGE
         */
        String DAILY_MONEY_NOT_ENOUGH_MESSAGE = "当日交易限额不足";
        /**
         * 收款卡号为二类卡CODE
         */
        String RECEIVER_CARD_SECOND_CODE = "0061";
        /**
         * 收款卡号为二类卡MESSAGE
         */
        String RECEIVER_CARD_SECOND_MESSAGE = "收款卡号为二类卡";
        /**
         * 支付卡号为二类卡CODE
         */
        String PAY_CARD_SECOND_CODE = "0062";
        /**
         * 支付卡号为二类卡MESSAGE
         */
        String PAY_CARD_SECOND_MESSAGE = "支付卡号为二类卡";
    }

    /**
     * 操作记录表类型
     */
    public interface OperateType {
        /**
         * 银行卡绑定CODE
         */
        String BIND_CARD = "0013";
        /**
         * 转账
         */
        String TRANSFER_CARD = "0014";
        /**
         * 充值
         */
        String RECHARGE_CARD = "0015";
        /**
         * 提现
         */
        String WITHDRAW_CARD = "0016";
        /**
         * 银行卡开户
         */
        String Open_Account="0001";
        /**
         * 申购
         */
        String SUBSCRIBE = "0051";
    }

    /**
     * 操作记录表状态
     */
    public interface OperateStatus {
        /**
         * 可疑CODE
         */
        String SUSPICIOUS = "2";
        /**
         * 成功CODE
         */
        String SUCCESS = "1";

        String SUCCESS_MESSAGE = "申购成功";
        /**
         * 失败CODE
         */
        String FAILED = "0";

        String FAILED_MESSAGE = "申购失败";
    }

    public interface Parameter {
        String SUCCESS = "S000000";
        String SUCCESS_MESSAGE = "操作成功";
        String INTERNAL_ERROR = "E000001";
        String INTERNAL_ERROR_MESSAGE = "服务器异常，请稍后再试";
        String PARAMETER_ERROR = "E000004";
        String PARAMETER_ERROR_MESSAGE = "参数不合法，请重试";
        String CODE_FAILED = "E000002";
        String CODE_MESSAGE = "验证码过期";
        String CODE_FAILED_MESSAGE = "验证码错误";
        String TRANSFER_ERROR = "E000003";
        String TRANSFER_ERROR_MESSAGE = "转账失败";
        String MONEY_ERROR = "E000004";
        String MONEY_ERROR_MESSAGE = "金额输入错误";
        String PARAMETER_LACK_ERROR = "E000005";
        String PARAMETER_LACK_MESSAGE = "参数缺失";

    }

    public interface MediaType {
        String APPLICATION_JSON_UTF8 = "application/json;charset=UTF-8";
        String APPLICATION_JSON = "application/json";
        String TEXT_PLAIN = "text/plain";
        String TEXT_PLAIN_UTF8 = "text/plain; charset=utf-8";
    }


    /**
     * 开户状态
     */
    public interface OpenAccount{
        //用户状态
        String userStatus="0";
        //表示二类户
        String accountType="2";
        //默认余额
        String MONEY="0.00";
        //每日支付限额
        BigDecimal DAILY_MONEY=new BigDecimal("10000");

        /**
         * 开户行名字
         */
        String BANK_NAME="先飞雄鸡银行";

        String SUCCESS = "A000000";
        String SUCCESS_MESSAGE = "开户成功";

        String ERROR = "A000001";
        String ERROR_MESSAGE = "开户失败";

        String PASSWORD_ERROR = "A000002";
        String PASSWORD_ERROR_MESSAGE = "用户密码不能为空";

        String STATUS_ERROR = "A000003";
        String STATUS_ERROR_MESSAGE = " 您当前的账号状态异常 ， 请确定状态可用";

        String EXIST_ERROR = "A000004";
        String EXIST_ERROR_MESSAGE = "该用户已开户";

        String USER_NO_EXIST_ERROR = "A000005";
        String USER_NO_EXIST_ERROR_MESSAGE = "该用户不存在";

        String PERSONAL_MESSAGE_ERROR = "A000006";
        String PERSONAL_MESSAGE_ERROR_MESSAGE = "个人信息校验失败";

        String NOT_EXIST_ERROR = "A000007";
        String NOT_ERROR_MESSAGE = "该用户未开户";
    }

    public interface ValidateCode{
        String PHONE_CODE_ERROR="C000001";
        String PHONE_CODE_ERROR_MESSAGE="手机验证码获取失败";
        String NOT_NULL_ERROR = "C000002";
        String NOT_NULL_ERROR_MESSAGE = "参数不能为空";
        String VALIDATE_ERROR = "C000003";
        String VALIDATE_ERROR_MESSAGE = "验证码错误";

    }

    public interface Subscribe{
        String SUBSCRIBE_SUCCESS = "S000000";
        String SUBSCRIBING_SUCCESS_MESSAGE = "申购申请已受理，请等待结果";
        String SUBSCRIBE_SUCCESS_MESSAGE = "申购成功";
        String USER_NO_EXIST_ERROR = "S000001";
        String USER_NO_EXIST_ERROR_MESSAGE = "用户不存在";
        String USER_ERROR = "S000002";
        String USER_ERROR_MESSAGE = "用户状态异常，请稍候再试";
        String SUBSCRIBE_MSG_ERROR = "S000003";
        String SUBSCRIBE_MSG_ERROR_MESSAGE = "申购信息有误，请核实后再试";
        String ITEM_ERROR = "S000004";
        String ITEM_ERROR_MESSAGE = "当前理财产品不存在，请重新选择";
        String MONEY_ERROR = "S000005";
        String MONEY_ERROR_MESSAGE = "当前输入金额有误，请重新输入";
        String ACCOUNT_ERROR = "S000006";
        String ACCOUNT_ERROR_MESSAGE = "当前账户状态异常， 请稍候再试";
        String PASSWORD_ERROR = "S000007";
        String PASSWORD_ERROR_MESSAGE = "密码输入错误，请重新输入";
        String SHARE_ERROR = "S000008";
        String SHARE_ERROR_MESSAGE = "当前理财产品份额不足，请重新选择";
        String SUBSCRIBE_FAILED_ERROR = "S000009";
        String SUBSCRIBE_FAILED_MESSAGE = "申购失败";
    }

    public interface FinanceOrder{
        //申购
        String ORDER_SUBSCRIBE_TYPE="1";
        //赎回
        String ORDER_REDEEM_TYPE="2";
        //订单处理中
        String ORDER_STATUS="2";

        String SUCCESS_FLAG="1";
        String SUCCESS_FLAG_MESSAGE="基金订单受理成功";

        String FAIL_FLAG="0";
        String FAIL_FLAG_MESSAGE="基金订单受理失败";

        String NOT_REASON="2";
        String NOT_REASON_MESSAGE="基金订单未受理";


    }

    public interface FileType{
        //赎回文件
        String SALE = "sale";
        //申购文件
        String SUBSCRIBE = "subscribe";
        //赎回回盘文件
        String SALE_BACK = "sale-back";
        //已购文件
        String SUBSCRIBE_BACK = "subscribe-back";
    }

    public interface unbound{
        String ACCOUNT_ERROR = "U000001";
        String ACCOUNT_ERROR_MESSAGE = "不能解绑二类卡";
        String PASSWORD_ERROR = "U000002";
        String PASSWORD_ERROR_MESSAGE = "密码错误";
    }

    public interface FinanceItem{
        /**
         * 上架
         */
        String ISONSALE = "1";
        /**
         * 下架
         */
        String NOT_ISONSALE = "0";
        /**
         * 已删除
         */
        String ISDELETED = "1";
        /**
         * 未删除
         */
        String NOT_ISDELETED = "0";
        /**
         * 稳健理财
         */
        String LOW_LEVEL = "1";
        /**
         *激进理财
         */
        String HIGH_LEVEL = "2";
    }

    public interface FinanceHold{
        String HOLD = "1";
        String NOT_HOLD = "2";
    }


}
