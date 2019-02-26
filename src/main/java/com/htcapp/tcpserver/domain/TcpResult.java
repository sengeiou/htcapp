package com.htcapp.tcpserver.domain;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Jone on 2018-06-18.
 */
public class TcpResult {

    private Integer status;
    private String msg;
    private Object data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
    static class Response{
        private Integer operation;
        private String license;

        public Integer getOperation() {
            return operation;
        }

        public void setOperation(Integer operation) {
            this.operation = operation;
        }

        public String getLicense() {
            return license;
        }

        public void setLicense(String license) {
            this.license = license;
        }
    }

    public static Response buildResponse(Integer operation,String license) throws UnsupportedEncodingException {
            Response response=new Response();
            response.setOperation(operation);
            response.setLicense(license);
            return response;
    }
    public static Response openForCar(String license) throws UnsupportedEncodingException {
        return buildResponse(1,license);
    }

    public static TcpResult isOK(Object data){
        return TcpResult.build(200,"OK",data);
    }

    public static TcpResult build(Integer status,String msg,Object data){
        TcpResult tcpResult=new TcpResult();
        tcpResult.setStatus(status);
        tcpResult.setMsg(msg);
        tcpResult.setData(data);
        return  tcpResult;
    }
    public static TcpResult build(Integer status,String msg){
        return TcpResult.build(status,msg,null);
    }
}
