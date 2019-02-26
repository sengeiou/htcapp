package com.htcapp.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.htcapp.domain.*;
import org.apache.catalina.User;

import java.io.Serializable;
import java.util.List;

/**
 * 用于保存状态
 */
public class SimpleResult implements Result,Serializable{
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;//返回的状态
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer status_code;//相应的状态码


    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Errors errors;//返回错误信息，如果错误信息不存在则被过滤
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String token;//返回登录成功token

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String code;

    public Errors getErrors() {
        return errors;
    }

    public void setErrors(Errors errors) {
        this.errors = errors;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus_code() {
        return status_code;
    }

    public void setStatus_code(Integer status_code) {
        this.status_code = status_code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static SimpleResult build(Integer status_code, String message, Errors errors){
        SimpleResult result=new SimpleResult();
        result.setStatus_code(status_code);
        result.setMessage(message);
        result.setErrors(errors);
        return  result;
    }

    public static SimpleResult build(Integer status_code, String message){
        return SimpleResult.build(status_code,message,null);
    }

    public static SimpleResult build(String message, Integer status_code, String code){
        SimpleResult result = new SimpleResult();
        result.setStatus_code(status_code);
        result.setMessage(message);
        result.setCode(code);
        return result;
    }

    public static SimpleResult build(String message){
        SimpleResult result = new SimpleResult();
        result.message = message;
        return result;
    }

}

