package com.htcapp.tcpserver.domain;

/**
 * Created by Jone on 2018-07-10.
 */
public class Confirm {
    private String bname;
    private Integer retain;

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public Integer getRetain() {
        return retain;
    }

    public void setRetain(Integer retain) {
        this.retain = retain;
    }

    public static Confirm build(String bname,Integer retain){
        Confirm confirm=new Confirm();
        confirm.setBname(bname);
        confirm.setRetain(retain);
        return  confirm;
    }
}
