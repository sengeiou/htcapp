package com.htcapp.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/*
 * 资金变动记录
 */
public class FundsRecords {

	private Integer id;
	private Integer uid;                    //用户ID

	private BigDecimal amount;          //金额
	private Integer type;                   //交易类型：0：充值	1：缴纳停车费	2：购物
	private Integer status;                 //支付状态	0：未支付	1：支付成功  	2：交易关闭
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer result_operation;       //支付成功后是否操作成功
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String trade_no;            //订单号
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String api_trade_no;        //API方 交易流水号
	private Integer pay_method;             //支付方式	微信支付：	1：扫码	2：wap	3：app	支付宝支付：	4：网站	5：wap	6：app
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String raw_data;            //异步通知收到的原始数据json格式
	private String subject;             //订单标题
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String remark;              //备注
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date created_at;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date updated_at;

	public FundsRecords() {
	}

	public FundsRecords(FundsRecords fundsRecords){
		this.id = fundsRecords.getId();
		this.uid = fundsRecords.getUid();
		this.amount = fundsRecords.getAmount();
		this.type = fundsRecords.getType();
		this.status = fundsRecords.getStatus();
		this.result_operation = fundsRecords.getResult_operation();
		this.trade_no = fundsRecords.getTrade_no();
		this.api_trade_no =fundsRecords.getApi_trade_no();
		this.pay_method = fundsRecords.getPay_method();
		this.raw_data = fundsRecords.getRaw_data();
		this.subject = fundsRecords.getSubject();
		this.remark = fundsRecords.getRemark();
		this.created_at = fundsRecords.getCreated_at();
		this.updated_at = fundsRecords.getUpdated_at();
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
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getResult_operation() {
		return result_operation;
	}
	public void setResult_operation(int result_operation) {
		this.result_operation = result_operation;
	}
	public String getTrade_no() {
		return trade_no;
	}
	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}
	public String getApi_trade_no() {
		return api_trade_no;
	}
	public void setApi_trade_no(String api_trade_no) {
		this.api_trade_no = api_trade_no;
	}
	public Integer getPay_method() {
		return pay_method;
	}
	public void setPay_method(Integer pay_method) {
		this.pay_method = pay_method;
	}
	public String getRaw_data() {
		return raw_data;
	}
	public void setRaw_data(String raw_data) {
		this.raw_data = raw_data;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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

	/**
	 *
	 * @param uid 订单所属用户
	 * @param count 订单价格
	 * @param type 订单交易类型
	 *              0：充值
	 *              1：缴纳车费
	 *              2：购物
	 * @param status  支付状态：
	 *                     0：未支付
	 *                     1：支付成功
	 *                     2： 交易关闭
	 * @param payMethod 支付方式
	 *                   1. 微信扫码 2.微信wap 3.微信app 4.支付宝网站
	 *                   5. 支付宝wap 6. 支付宝app 7. 余额
	 * @return
	 */
    public static FundsRecords buildSimpeFundsRecords(Integer uid,BigDecimal count,
													  Integer type,Integer status,Integer payMethod) {

		FundsRecords fundsRecords=new FundsRecords();

		fundsRecords.setUid(uid);//设置订单所属用户
		fundsRecords.setType(type);//设置订单类型
		fundsRecords.setAmount(count);//设置金额
		fundsRecords.setStatus(status);//设置支付状态

		fundsRecords.setResult_operation(1);//设置支付成功后的操作

		fundsRecords.setTrade_no(createNumber());//设置订单号

		fundsRecords.setResult_operation(1);//支付成功操作成功

		fundsRecords.setPay_method(payMethod);//设置支付方式
		fundsRecords.setCreated_at(new Date());
		fundsRecords.setUpdated_at(new Date());
		return fundsRecords;
    }

	/**
	 * 通过它来创建订单号
	 * @return
	 */
	private static final Random random=new Random();
	private static String createNumber(){
		Date date=new Date();

		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMddhhmmss"
		);
		String value=simpleDateFormat.format(date);
		StringBuilder stringBuilder=new StringBuilder(value);

		for (int i=0;i<6;i++){//生成六位随机数
			stringBuilder.append(random.nextInt(10));
		}
		return stringBuilder.toString();
	}
}
