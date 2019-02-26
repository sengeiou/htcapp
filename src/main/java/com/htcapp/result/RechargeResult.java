package com.htcapp.result;

import com.htcapp.domain.FundsRecords;

public class RechargeResult implements Result {
    private String data;
    private FundsRecords order;
    private int status_code;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public FundsRecords getOrder() {
        return order;
    }

    public void setOrder(FundsRecords order) {
        this.order = order;
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }
    public static RechargeResult build(String data, FundsRecords order, int status_code){
        RechargeResult result = new RechargeResult();
        result.data = data;
        result.order = order;
        result.status_code = status_code;
        return result;
    }
}
