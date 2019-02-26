package com.htcapp.service;

import com.htcapp.domain.Cars;
import com.htcapp.domain.Parkings;
import com.htcapp.mapper.CarsMapper;
import com.htcapp.mapper.ParkingsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CarsServce {
    @Autowired
    private CarsMapper carsMapper;

    /**
     * 添加车牌号
     *
     * @param car 添加的停车信息
     * @throws Exception
     */
    @Transactional
    public void addCars(Cars car) throws Exception {
        this.carsMapper.addCar(car);
    }

    /**
     * 根据主键查询车牌号
     *
     * @param number_plate_id
     * @return
     */
    public Cars findCarsById(Integer number_plate_id) {
        return this.carsMapper.findCarsById(number_plate_id);
    }

    /**
     * 根据车牌号查询车牌号所有信息
     *
     * @param carID 车牌号
     * @return
     */
    public Cars findCarsByCarID(String carID) {
        return this.carsMapper.findCarsByCarID(carID);
    }

}
