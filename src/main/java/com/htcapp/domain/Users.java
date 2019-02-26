package com.htcapp.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.htcapp.result.Result;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/*
 * 用户
 */
public class Users implements Result{
	private Integer id;
	private String mobile;                //手机号码
	private String name;                  //用户名
	private String truename;              //真实姓名
	private String avatar;                //头像
	private String nickname;              //昵称
	private Integer gender;                   //性别	0：未填写	1：男	 2：女
	private Integer age;                      //年龄：	例：19930203
	private String email;                 //邮箱

	@JsonIgnore//在转为json时候忽略
	private String password;              //密码
	@JsonFormat(pattern = "#.00") //转为json格式化
	private BigDecimal balance;           //用户余额

	@JsonIgnore
	transient private String remember_token;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date created_at;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date updated_at;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<Cars> cars;                    //用户车牌号
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTruename() {
		return truename;
	}
	public void setTruename(String truename) {
		this.truename = truename;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public String getRemember_token() {
		return remember_token;
	}
	public void setRemember_token(String remember_token) {
		this.remember_token = remember_token;
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
	public List<Cars> getCars() {
		return cars;
	}
	public void setCars(List<Cars> cars) {
		this.cars = cars;
	}

	public static Users initUser(String mobile){
		Users users=null;
		users = new Users();

		users.setMobile(mobile);//设置手机号


		users.setBalance(new BigDecimal(0.00));///设置账户


		users.setUpdated_at(new Date());//设置更新时间

		users.setCreated_at(new Date());//设置创建时间

		users.setNickname("昵称");


		users.setAge(19980212);

		users.setGender(1);
		return users;
	}
}
