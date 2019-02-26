package com.htcapp.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/*
 * 停车场车位表
 */
public class ParkingSpaces {
	
	private Integer id;
	private Integer pid;                      //停车场ID
	private String rid;                   //停车场ID
	private String parking_lock;          //地锁编号
	private Double latitude;              //地锁编号
	private Double longitude;             //经度
	private String remark;                //备注
	private Integer status;                   //状态：	0：空	  1：已预约	2：已停车	3：不可用
	private String number_plate;          //当前停车车牌号
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date created_at;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date updated_at;
	private String lock_name;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public String getRid() {
		return rid;
	}
	public void setRid(String rid) {
		this.rid = rid;
	}
	public String getParking_lock() {
		return parking_lock;
	}
	public void setParking_lock(String parking_lock) {
		this.parking_lock = parking_lock;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
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
	public String getNumber_plate() {
		return number_plate;
	}
	public void setNumber_plate(String number_plate) {
		this.number_plate = number_plate;
	}
	public Date getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}
	public Date getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}
	public String getLock_name() {
		return lock_name;
	}
	public void setLock_name(String lock_name) {
		this.lock_name = lock_name;
	}

}
