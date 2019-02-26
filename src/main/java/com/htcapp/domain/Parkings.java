package com.htcapp.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.htcapp.result.Result;

import java.math.BigDecimal;
import java.util.Date;

/*
 * 停车场
 */
public class Parkings implements Result {
	
	private Integer id;
	private String name;                                //停车场名称
	private Integer parking_spaces;                         //车位数
	private Integer remainder_parking_spaces;               //剩余车位数
	private Integer unavailable_parking_spaces;             //不可用车位数
	private BigDecimal price;                           //收费标准（元/时）
	private Integer free_time;                              //免费时长（分钟）
	private Float latitude;                             //纬度
	private Float longitude;                            //经度
	private String location;                            //停车场地址
	private String principal;                           //负责人
	private String contact_information;                 //联系方式
	private Integer api_id;                                 //高德云图id
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date created_at;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date updated_at;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getParking_spaces() {
		return parking_spaces;
	}
	public void setParking_spaces(Integer parking_spaces) {
		this.parking_spaces = parking_spaces;
	}
	public Integer getRemainder_parking_spaces() {
		return remainder_parking_spaces;
	}
	public void setRemainder_parking_spaces(Integer remainder_parking_spaces) {
		this.remainder_parking_spaces = remainder_parking_spaces;
	}
	public Integer getUnavailable_parking_spaces() {
		return unavailable_parking_spaces;
	}
	public void setUnavailable_parking_spaces(Integer unavailable_parking_spaces) {
		this.unavailable_parking_spaces = unavailable_parking_spaces;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Integer getFree_time() {
		return free_time;
	}
	public void setFree_time(Integer free_time) {
		this.free_time = free_time;
	}
	public Float getLatitude() {
		return latitude;
	}
	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}
	public Float getLongitude() {
		return longitude;
	}
	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getPrincipal() {
		return principal;
	}
	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	public String getContact_information() {
		return contact_information;
	}
	public void setContact_information(String contact_information) {
		this.contact_information = contact_information;
	}
	public Integer getApi_id() {
		return api_id;
	}
	public void setApi_id(Integer api_id) {
		this.api_id = api_id;
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

	
}
