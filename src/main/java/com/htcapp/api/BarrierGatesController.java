package com.htcapp.api;

import com.htcapp.domain.BarrierGates;
import com.htcapp.domain.Errors;
import com.htcapp.domain.Parkings;
import com.htcapp.mapper.BarrierGatesMapper;
import com.htcapp.result.Result;
import com.htcapp.result.SimpleResult;
import com.htcapp.service.ParkingsService;
import com.htcapp.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BarrierGatesController {
    private static final Logger logger= LoggerFactory.getLogger(BarrierGatesController.class);
    @Autowired
    private BarrierGatesMapper barrierGatesMapper;

    @Autowired
    private ParkingsService parkingsService;
    //
    @PostMapping("/api/app/barriergate/add")
    public Result addResult(Integer pid, String bid, String bname, Integer btype, String bdescription){

        if (pid==null|| StringUtils.isEmpty(bid)){
            return SimpleResult.build(230,"车闸信息不完整");
        }
        if (StringUtils.isEmpty(bname)){
            bname=bid;
        }
        if (btype==null){
            btype=1;
        }
        Parkings parkings=null;
        try {
            parkings=this.parkingsService.findParkingsById(pid);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        if (parkings==null){
            return SimpleResult.build(230,"停车场不存在");
        }

        BarrierGates b = barrierGatesMapper.findBarrierGatesByLock(bid);
        if (b != null){
            return SimpleResult.build(500,"添加失败", Errors.BARRIERGATE_EXIST_ERROR);
        }
        int change = barrierGatesMapper.addBarrierGatesInfo(pid,bid,bname,btype,bdescription);
        if (change == 1){
            return SimpleResult.build(200,"添加成功");
        }
        return SimpleResult.build(500,"添加失败");
    }
}
