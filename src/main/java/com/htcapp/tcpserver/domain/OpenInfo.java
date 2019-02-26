package com.htcapp.tcpserver.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 开闸数据返回信息
 */
public class OpenInfo {
    private Integer operation;
    private String bname;
    private String license;
    private Integer retain;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer time;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String cost;

    public Integer getOperation() {
        return operation;
    }

    public void setOperation(Integer operation) {
        this.operation = operation;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public Integer getRetain() {
        return retain;
    }

    public void setRetain(Integer retain) {
        this.retain = retain;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public static OpenInfo open(String bname,String license,Integer retain){
        return open(bname,license,retain,null,null);
    }
    public static OpenInfo open(String bname,String license,
                                Integer retain,Integer time,String cost){
        OpenInfo openInfo=new OpenInfo();
        openInfo.setBname(bname);
        openInfo.setLicense(license);
        openInfo.setRetain(retain);
        openInfo.setTime(time);
        openInfo.setCost(cost);
        openInfo.setOperation(1);
        return openInfo;
    }
}
