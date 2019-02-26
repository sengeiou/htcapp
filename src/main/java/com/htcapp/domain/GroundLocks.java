package com.htcapp.domain;

public class GroundLocks {

    private int id;
    private int lock_ID;    //地锁编号
    private int  lock_state;    //0表示车位无车辆，1表示车位有车辆
    private int car_type;
    private String Ble_Name;
    private String Ble_MACAddr;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLock_ID() {
        return lock_ID;
    }

    public void setLock_ID(int lock_ID) {
        this.lock_ID = lock_ID;
    }

    public int getLock_state() {
        return lock_state;
    }

    public void setLock_state(int lock_state) {
        this.lock_state = lock_state;
    }

    public int getCar_type() {
        return car_type;
    }

    public void setCar_type(int car_type) {
        this.car_type = car_type;
    }

    public String getBle_Name() {
        return Ble_Name;
    }

    public void setBle_Name(String ble_Name) {
        Ble_Name = ble_Name;
    }

    public String getBle_MACAddr() {
        return Ble_MACAddr;
    }

    public void setBle_MACAddr(String ble_MACAddr) {
        Ble_MACAddr = ble_MACAddr;
    }
}
