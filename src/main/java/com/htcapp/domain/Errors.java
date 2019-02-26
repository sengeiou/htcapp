package com.htcapp.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * 保存错误信息，用于在Result中使用
 */
public class Errors implements Serializable {

    public static Errors  PHONEFORMAT_ERROR=new Errors(new String[]{"手机号码格式不正确"},1);

    public static Errors  PHONENONE_ERROR=new Errors(new String[]{"缺少手机号码"},1);

    public static Errors NUMBERFORMAT_ERROR=new Errors(new String[]{"车牌号格式不正确"},3);

    public static Errors NUMBERNONE_ERROR=new Errors(new String[]{"请填写车牌号"},3);

    public static Errors CARDNONE_USER_ERROR=new Errors(new String[]{"缺少银行卡用户名"},2);

    public static Errors CARDNONE_NUMBER_ERROR=new Errors(new String[]{"缺少银行卡卡号"},2);

    public static Errors BARRIERGATE_EXIST_ERROR = new Errors(new String[]{"道闸已存在"}, 4);


    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String[] mobile;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String[] bank;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String[] number_plate;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String[] barrierGateId;


    public Errors(String[] values,int tag) {
        switch (tag) {
            case 1:
                this.mobile = values;
                break;
            case 2:
                this.bank = values;
                break;
            case 3:
                this.number_plate = values;
                break;
            case 4:
                this.barrierGateId = values;
        }
    }

    public String[] getMobile() {
        return mobile;
    }

    public void setMobile(String[] mobile) {
        this.mobile = mobile;
    }

    public String[] getBank() {
        return bank;
    }

    public void setBank(String[] bank) {
        this.bank = bank;
    }

    public String[] getNumber_plate() {
        return number_plate;
    }

    public void setNumber_plate(String[] number_plate) {
        this.number_plate = number_plate;
    }

    public String[] getBarrierGateId() {
        return barrierGateId;
    }

    public void setBarrierGateId(String[] barrierGateId) {
        this.barrierGateId = barrierGateId;
    }
}
