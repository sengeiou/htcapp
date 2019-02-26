package com.htcapp.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.htcapp.domain.*;

/**
 * 用于保存停车记录的返回结果
 */
public class ReservationsResult extends Reservations implements Result {
    public ReservationsResult() {
    }

    public ReservationsResult(Reservations reservations) {
        super(reservations);
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Cars car;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long time;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Parkings parking;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ParkingSpaces parking_spaces;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Users user;

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Cars getCar() {
        return car;
    }

    public void setCar(Cars car) {
        this.car = car;
    }

    public Parkings getParking() {
        return parking;
    }

    public void setParking(Parkings parking) {
        this.parking = parking;
    }

    public ParkingSpaces getParking_spaces() {
        return parking_spaces;
    }

    public void setParking_spaces(ParkingSpaces parking_spaces) {
        this.parking_spaces = parking_spaces;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
