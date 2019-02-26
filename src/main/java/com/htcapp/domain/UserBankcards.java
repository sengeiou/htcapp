package com.htcapp.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.htcapp.result.Result;

import java.util.Date;

/*
 * 用户银行卡
 */
public class UserBankcards implements Result{

	private Integer id;               
	private Integer uid;			  //用户ID 
	private String name;          //持卡人姓名
	private String card;		  //银行卡号
	private String bank;          //开户行
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date created_at;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date updated_at;

	public UserBankcards() {
	}

	public UserBankcards(Integer id, Integer uid, String name, String card, String bank, Date created_at, Date updated_at) {
		this.id = id;
		this.uid = uid;
		this.name = name;
		this.card = card;
		this.bank = bank;
		this.created_at = created_at;
		this.updated_at = updated_at;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCard() {
		return card;
	}
	public void setCard(String card) {
		this.card = card;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
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
