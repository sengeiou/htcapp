package com.htcapp.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/*
 * 用户车牌号码
 */
public class Cars {
	
	private Integer id;
	private Integer uid;       //用户ID
    private String number_plate;        //车牌号
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date created_at;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date updated_at;

	public Cars() {
	}
	public Cars(Integer id, Integer uid, String number_plate, Date created_at, Date updated_at) {
		this.id = id;
		this.uid = uid;
		this.number_plate = number_plate;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
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


}
