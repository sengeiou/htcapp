package com.htcapp.properties;

public class ReservationsProperties {
	//预约状态
	public static final Integer STATUS_NO =0;//已预约未停车
	public static final Integer STATUS_ENTER =1;//已预约进入停车场
	public static final Integer STATUS_ENTER_NO=12;//未预约进入停车场
	public static final Integer STATUS_PAR =2;//已停车
	public static final Integer STATUS_CANCEL =3;//取消预约
	public static final Integer STATUS_FAIL =4;//停车失败
	public static final Integer STATUS_SUCCESS =10;//已完成
	public static final Integer STATUS_PAIED =11;//已收费
}
