package com.htcapp.tcpserver.domain;

/**
 * Created by Jone on 2018-05-18.
 */
public class AnaResult<T> {

    private Integer type;
    private  T data;


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }

    public static <T> AnaResult build(Integer type,T t){
        AnaResult anaResult=new AnaResult();

        anaResult.setType(type);
        anaResult.setData(t);
        return anaResult;


    }
}
