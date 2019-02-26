package com.htcapp.tcpserver.utils;

import com.htcapp.domain.GroundLocks;
import com.htcapp.tcpserver.domain.AnaResult;
import com.htcapp.tcpserver.domain.ImageInfo;
import com.htcapp.tcpserver.domain.LockInfo;
import com.htcapp.tcpserver.domain.ParkInfo;
import com.htcapp.utils.FileUtils;
import com.htcapp.utils.JsonUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/*
* 作用:解析所有的报文(根据报文数据)
*    如果解析失败或者验证失败，将status置为false
*                           否则将status置为true
*
* */
public class AnalysisUtils {

    public static AnaResult analysis(String values,String path) throws Exception {

        AnaResult anaResult =null;


        String type=JsonUtils.getJsonValueByName(values,"type");

        if (type.equals("1")){

            String con= JsonUtils.getJsonValueByName(values,"parkInfo");

            ParkInfo parkInfo=JsonUtils.stringToObject(con,ParkInfo.class);

            anaResult=AnaResult.build(1,parkInfo);
        }else if (type.equals("2")){
            String con= JsonUtils.getJsonValueByName(values,"lockInfo");

            LockInfo lockInfo=JsonUtils.stringToObject(con,LockInfo.class);

            anaResult=AnaResult.build(2,lockInfo);
        }else if (type.equals("11")||type.equals("12")){//确认车辆进入

            Integer types=Integer.valueOf(type);
            String bname=JsonUtils.getJsonNodeValue(values,"bname");
            bname=bname.replaceAll("\\\"","");
            anaResult=AnaResult.build(types,bname);
        }else if (type.equals("3")){
            Integer types=Integer.valueOf(type);
            String con= JsonUtils.getJsonValueByName(values,"lockInfo");
            LockInfo lockInfo=JsonUtils.stringToObject(con,LockInfo.class);
            anaResult=AnaResult.build(3,lockInfo);
        }
        else if(type.equals("4")){
            Integer types=Integer.valueOf(type);
            String bname=JsonUtils.getJsonValueByName(values,"bname");
            bname=bname.replaceAll("\\\"","");
            anaResult=AnaResult.build(types, bname);
        }
        else if(type.equals("5")){
            String con = JsonUtils.getJsonValueByName(values,"groundlock");
            GroundLocks groundLocks = JsonUtils.stringToObject(con,GroundLocks.class);
            anaResult = AnaResult.build(5,groundLocks);
        }
        else{
            throw  new Exception("请求类型"+type+"不存在");
        }

        return anaResult;
    }
}