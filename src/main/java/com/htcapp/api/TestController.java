package com.htcapp.api;

import com.htcapp.service.BarrierGatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class TestController {
    @Autowired
    BarrierGatesService barrierGatesService;

/*    @PostMapping("/goOut")
    public Boolean goutOut(String lockID,String carID){
        CarData carData=new CarData();
        carData.setCarID(carID);
        carData.setLockId(lockID);
        return barrierGatesService.goOut(carData);
    }

    @PostMapping("/goIn")
    public Boolean goIn(String carID,String lockID){
		CarData carData = new CarData();
		carData.setCarID(carID);
		carData.setLockId(lockID);
		return barrierGatesService.goIn(carData);
	}*/
}
