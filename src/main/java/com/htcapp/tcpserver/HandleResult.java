package com.htcapp.tcpserver;

import com.htcapp.domain.GroundLocks;
import com.htcapp.domain.White;
import com.htcapp.service.BarrierGatesService;
import com.htcapp.service.GroudLocksService;
import com.htcapp.tcpserver.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 用于处理解析的所有结果并
 * 向硬件端回传数据
 */
@Component
public class HandleResult {

    @Autowired
    private BarrierGatesService barrierGatesService;

    @Autowired
    private GroudLocksService groudLocksService;

    public TcpResult handle(AnaResult anaResult) {

        if (anaResult.getType()==1){
           return  handleCarInfo(anaResult);
        }else if (anaResult.getType()==2){
            return blackWhiteInfo(anaResult);
        }else if (anaResult.getType()==11){//确认车辆进入
            return confirmGoIn(anaResult);
        }else if (anaResult.getType()==12){
            return confirmGoOut(anaResult);
        }else if (anaResult.getType()==3) {
            return changeWhiteInfoList(anaResult);
        }else if (anaResult.getType()==4){
            return emptyCarCount(anaResult);
        }else if (anaResult.getType() == 5){
            return changeGroundLockState(anaResult);
        }
        return TcpResult.build(500,"请求类型"+anaResult.getType()+"不存在");
    }

    private TcpResult emptyCarCount(AnaResult anaResult) {
        Integer count=this.barrierGatesService.findEmptyCarCount(anaResult);
        String bname= (String) anaResult.getData();
        return TcpResult.build(200,"OK",Confirm.build(bname,count));
    }

    private TcpResult changeWhiteInfoList(AnaResult anaResult) {
         List<White>list=   this.barrierGatesService.findChangeWhiteInfo(anaResult);

         LockInfo lockInfo= (LockInfo) anaResult.getData();

         return TcpResult.build(200,"OK",WhiteList.build(
                 lockInfo.getBname(),
                 list==null?0:list.size(),
                 list
                 ));

    }

    private TcpResult confirmGoOut(AnaResult anaResult) {
        TcpResult tcpResult=this.barrierGatesService.confirmGoOut(anaResult);
        return tcpResult;
    }

    private TcpResult confirmGoIn(AnaResult anaResult) {
        TcpResult tcpResult= this.barrierGatesService.confirmGoIn(anaResult);
        return  tcpResult;
    }

    private TcpResult blackWhiteInfo(AnaResult anaResult) {
        LockInfo lockInfo= (LockInfo) anaResult.getData();
        List<White> list=this.barrierGatesService.findWhiteListByLockInfo(lockInfo);
        WhiteList whiteList=null;
        if (list!=null&&list.size()>0){
            whiteList=WhiteList.build(lockInfo.getBname(),list.size(),list);
        }else{
            whiteList=WhiteList.build(lockInfo.getBname(),0,null);
        }
        return TcpResult.isOK(whiteList);
    }

    private TcpResult changeGroundLockState(AnaResult anaResult) {
        GroundLocks groundLocks = (GroundLocks) anaResult.getData();
        int lockid = groundLocks.getLock_ID();
        int lockstate = groundLocks.getLock_state();
        int state = 0;
        GroundLocks g = this.groudLocksService.getGroundLocks(lockid);
        if (g == null){
            return TcpResult.build(500,"没有该地锁");
        }
        else {
            if (this.groudLocksService.updateGroundLocks(lockid,lockstate)==1)
                return TcpResult.build(200,"OK");
        }
        return TcpResult.build(500,"服务器内部错误");

    }

/*    private void handleCarLock(AnaResult anaResult) {//上传道闸信息
            CarLock carLock= (CarLock) anaResult.getData();
            this.barrierGatesService.addCarLock(carLock);
            anaResult.setProtocolData(ProtocolData.successed);
    }*/

    private TcpResult handleCarInfo(AnaResult anaResult) {
        try {
            ParkInfo parkInfo= (ParkInfo) anaResult.getData();
            TcpResult tcpResult=null;
            if (parkInfo.getDirection()==1){
                 tcpResult=barrierGatesService.goIn(parkInfo);
            }else {
                 tcpResult= barrierGatesService.goOut(parkInfo);
            }
            return tcpResult;
        }catch (Exception e){
            return TcpResult.build(500,"内部错误");
        }

    }
}
