package com.htcapp.domain;

import java.math.BigDecimal;
import java.util.Date;

/*
 * 车位预约记录
 */
public class Reservations {

	private Integer id;
	private Integer uid;                        //用户ID
	private Integer pid;                        //停车场ID
	private Integer rid;                        //车位ID
	private Integer number_plate_id;            //车牌号ID
	private String bid;                     //道闸ID
	private Long appointment;                //预约时间
	private Long parking_time;               //入库时间
	private Integer leave;						//出库时间
	private String remark;                  //备注
	private Integer status;                     //预约状态：0：已预约未停车	1：已进入停车场	2：已停车	3：取消预约	4：停车失败	10：已完成	11：已收费
	private BigDecimal price;               //收费标准（元/时）
	private BigDecimal due_charges;         //应收费用
	private BigDecimal charges;             //实收费用
	private BigDecimal discount_id;         //优惠ID
	private Integer free_time;                  //免费时长（分钟）
	private Long parking_times;              //停车时长（分钟）

	public Reservations() {
	}
	public static Reservations buildSimple(Integer uid, Integer pid, Integer rid, Integer number_plate_id,Long date, Integer status){
		Reservations reservations=new Reservations();
		reservations.setUid(uid);
		reservations.setPid(pid);
		reservations.setRid(rid);
		reservations.setNumber_plate_id(number_plate_id);
		reservations.setAppointment(date);
		reservations.setStatus(status);
		return reservations;

	}
	public Reservations(Reservations reservations) {
		this.id = reservations.getId();
		this.uid = reservations.getUid();
		this.pid = reservations.getPid();
		this.rid = reservations.getRid();
		this.number_plate_id = reservations.getNumber_plate_id();
		this.bid = reservations.getBid();
		this.appointment = reservations.getAppointment();
		this.parking_time = reservations.getParking_time();
		this.leave = reservations.getLeave();
		this.remark = reservations.getRemark();
		this.status = reservations.getStatus();
		this.price = reservations.getPrice();
		this.due_charges = reservations.getDue_charges();
		this.charges = reservations.getCharges();
		this.discount_id = reservations.getDiscount_id();
		this.free_time = reservations.getFree_time();
		this.parking_times = reservations.getParking_times();
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public Integer getRid() {
		return rid;
	}
	public void setRid(Integer rid) {
		this.rid = rid;
	}
	public Integer getNumber_plate_id() {
		return number_plate_id;
	}
	public void setNumber_plate_id(Integer number_plate_id) {
		this.number_plate_id = number_plate_id;
	}
	public String getBid() {
		return bid;
	}
	public void setBid(String bid) {
		this.bid = bid;
	}
	public Long getAppointment() {
		return appointment;
	}
	public void setAppointment(Long appointment) {
		this.appointment = appointment;
	}
	public Long getParking_time() {
		return parking_time;
	}
	public void setParking_time(Long parking_time) {
		this.parking_time = parking_time;
	}
	public Integer getLeave() {
		return leave;
	}
	public void setLeave(Integer leave) {
		this.leave = leave;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getDue_charges() {
		return due_charges;
	}
	public void setDue_charges(BigDecimal due_charges) {
		this.due_charges = due_charges;
	}
	public BigDecimal getCharges() {
		return charges;
	}
	public void setCharges(BigDecimal charges) {
		this.charges = charges;
	}
	public BigDecimal getDiscount_id() {
		return discount_id;
	}
	public void setDiscount_id(BigDecimal discount_id) {
		this.discount_id = discount_id;
	}
	public Integer getFree_time() {
		return free_time;
	}
	public void setFree_time(Integer free_time) {
		this.free_time = free_time;
	}
	public Long getParking_times() {
		return parking_times;
	}
	public void setParking_times(Long parking_times) {
		this.parking_times = parking_times;
	}
	
	
}
