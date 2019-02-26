package com.htcapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by Jone on 2018-06-18.
 */
public class White {//白名单
    public static final Integer ADD=1;
    public static final Integer REMOVE=0;
    private  Integer id;
    //停车场id
    @JsonIgnore
    private Integer cid;
    @JsonIgnore
    private Integer pid;
    private String license;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer  carstatus;

    public Integer getCarstatus() {
        return carstatus;
    }

    public void setCarstatus(Integer carstatus) {
        this.carstatus = carstatus;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public static White build(Integer id,String license,Integer carstatus){
        White white=new White();
        white.setId(id);
        white.setLicense(license);
        white.setCarstatus(carstatus);
        return white;
    }
}
