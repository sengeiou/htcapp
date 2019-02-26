package com.htcapp.domain;

/**
 * 
 * @author rqz
 * 停车场道闸
 *
 */

public class BarrierGates {
	
	private Integer id;
	private Integer pid;       //停车场ID
	private String bid;       //道闸编号
	private String bname;         //道闸名称
	private Integer btype;         //1：入口  0:出口
	private String bdescription;       //备注
	
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


	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}

	public Integer getBtype() {
		return btype;
	}

	public void setBtype(Integer btype) {
		this.btype = btype;
	}


	public String getBname() {
		return bname;
	}
	public void setBname(String bname) {
		this.bname = bname;
	}

	public String getBdescription() {
		return bdescription;
	}
	public void setBdescription(String bdescription) {
		this.bdescription = bdescription;
	}

}
