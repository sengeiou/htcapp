package com.htcapp.tcpserver.domain;

/*
* 车闸位置定位，对应车闸信息
* */
public class CarLock {
    private String lockId;//道闸ID
    private String IP;//道闸IP
    private String positionX;//道闸位置
    private String positionY;

    public String getLockId() {
        return lockId;
    }

    public void setLockId(String lockId) {
        this.lockId = lockId;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getPositionX() {
        return positionX;
    }

    public void setPositionX(String positionX) {
        this.positionX = positionX;
    }

    public String getPositionY() {
        return positionY;
    }

    public void setPositionY(String positionY) {
        this.positionY = positionY;
    }
}
