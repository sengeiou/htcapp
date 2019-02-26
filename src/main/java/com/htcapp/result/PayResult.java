package com.htcapp.result;

import com.htcapp.domain.FundsRecords;

/**
 * 用于存放支付结果的返回数据
 */
public class PayResult implements Result {


    private Object data;
    private FundsRecords order;
    private Integer status_code;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public FundsRecords getOrder() {
        return order;
    }

    public void setOrder(FundsRecords order) {
        this.order = order;
    }

    public Integer getStatus_code() {
        return status_code;
    }

    public void setStatus_code(Integer status_code) {
        this.status_code = status_code;
    }


    public static PayResult build(Integer status_code,Object data,FundsRecords fundsRecords){
        PayResult payResult=new PayResult();
        payResult.setStatus_code(status_code);
        payResult.setData(data);
        payResult.setOrder(fundsRecords);

        return payResult;
    }
}
