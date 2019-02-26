package com.htcapp.service;

import com.htcapp.domain.*;
import com.htcapp.mapper.ReservationsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class ReservationsService {

    @Autowired
    private ReservationsMapper reservationsMapper;

    /**
     * 查询不可预约的用户
     *     目前知道有三类用户不可停车
     *      status=0,1,2不可以停车
     * @param id
     * @return
     */
    public Reservations findDontByUid(Integer id) {
        return  this.reservationsMapper.findDontByUid(id);
    }

    /**
     * 添加车位预约信息
     * @param reservations
     */
    @Transactional
    public void addReservations(Reservations reservations) {
        this.reservationsMapper.addReservations(reservations);
    }

    /**
     * 设置停车状态
     * @param status
     */
    @Transactional
    public void setStatusById(Integer id,Integer status) {
        this.reservationsMapper.setStatusById(id,status);
    }

    /**
     * 根据主键设置预约信息
     * @param id
     * @return
     */
    public Reservations getReservationsById(Integer id) {
        return this.reservationsMapper.getReservationsById(id);
    }

    /**
     * 根据主键设置备注
     * @param id
     * @param reason
     */
    @Transactional
    public void setReasonById(Integer id, String reason) {
        this.reservationsMapper.setReasonById(id,reason);
    }

    /**
     * 根据用户id查询最后一次的预约信息
     * @param id
     * @return
     */
    public Reservations findLastMettingByUid(Integer id) {
        return this.reservationsMapper.findLastMettingByUid(id);
    }

    /**
     * 根据uid查询预约总数
     * @param id
     * @return
     */
    public Integer getCountByUid(Integer id ) {
        return this.reservationsMapper.getCountByUid(id);
    }


    /**
     * 分页显示预约信息
     * @param start  分页开始数
     * @param perValue 每页数量
     * @param id  用户id
     * @return
     */
    public List<Reservations> findReservationsByPageAndUid(Integer start, Integer perValue, Integer id) {
        return  this.reservationsMapper.findReservationsByPageAndUid(start,perValue,id);
    }

    /**
     * 更新停车状态以及离开事件
     * @param i //停车状态
     * @param leave //离开时间
     * @param id  //停车表id
     */
    @Transactional
    public void updateStatusAndLeaveByid(int i, Long leave, Integer id) {
        this.reservationsMapper.updateStatusAndLeaveById(i,leave,id);
    }

    /**
     * 查询停车状态信息
     * @param carID 车牌id
     * @param pid  停车场ID
     * @param status  状态
     * @return
     */
    public List<Reservations> findReservationsByCarIDAndParkingIDAndStatus(Integer carID, Integer pid,Integer status) {
        return this.reservationsMapper.findReservationsByCarIDAndParkingID(carID,pid,status);
    }
    /**
	 * 查询预约信息
	 * @param carID 车牌id
	 * @param pid 停车场id
	 * */
    public List<Reservations> findReservationByCarIdAndParkingId(Integer carID,Integer pid){
    	return this.reservationsMapper.getReservationsByNumberPlateIdAndPid(carID,pid);
	}
    /**
     * 更新应收费用信息
     * @param end 应收费用价格
     * @param id 车位预约主键
     */
    @Transactional
    public void updateDueChargesById(BigDecimal end, Integer id) {
        this.reservationsMapper.updateDueChargesById(end,id);
    }

    /**
     * 更新实际收费信息
     * @param end 实际收费价格
     * @param id  主键
     */
    @Transactional
    public void updateChargesById(BigDecimal end, Integer id) {
        this.reservationsMapper.updateChargesById(end,id);
    }


    /**
     * 查询预约信息
     * @param number_plate_id 车牌号
     * */
    public List<Reservations> findReservationsByNumberPlateId(Integer number_plate_id,Integer pid){
        return this.reservationsMapper.getReservationsByNumberPlateIdAndPid(number_plate_id,pid);
    }
    /**
     * 更新停车信息
     * */
    @Transactional
    public void updateParkingsInfoById(Long parking_time, Integer status, Integer id){
        this.reservationsMapper.updateParkingInfoById(parking_time,status,id);
    }

    public Reservations findCanNotPark(int uid) {
        return this.reservationsMapper.findCanNotPark(uid);
    }

    public Reservations findById(Integer rid) {
        return  this.reservationsMapper.findById(rid);
    }
}
