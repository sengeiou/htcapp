package com.htcapp.result;

import com.htcapp.domain.Cars;

/**
 * Created by Jone on 2018-06-14.
 */
public class CarResult implements Result {
    private Cars car=null;

    public Cars getCar() {
        return car;
    }

    public void setCar(Cars car) {
        this.car = car;
    }

    public static CarResult build(Cars cars){
        CarResult carResult=new CarResult();
        carResult.setCar(cars);
        return carResult;
    }
}
