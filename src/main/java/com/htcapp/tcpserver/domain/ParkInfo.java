package com.htcapp.tcpserver.domain;

/**
 * Created by Jone on 2018-06-18.
 */
public class ParkInfo {

    private String bname;//车闸编号
    private String license;//车牌号
    private Integer direction;//方向
    private Integer carType;//车牌类型

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

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    public Integer getCarType() {
        return carType;
    }

    public void setCarType(Integer carType) {
        this.carType = carType;
    }
}
