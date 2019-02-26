package com.htcapp.tcpserver.domain;

import com.htcapp.domain.White;

import java.util.List;

/**
 * Created by Jone on 2018-06-18.
 */
public class WhiteList {
    private String bname;
    private Integer count;
    private List<White> list;

    public static WhiteList build(String bname,Integer count,List<White> list){
        WhiteList whiteList=new WhiteList();
        whiteList.setBname(bname);
        whiteList.setCount(count);
        whiteList.setList(list);
        return whiteList;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<White> getList() {
        return list;
    }

    public void setList(List<White> list) {
        this.list = list;
    }
}
