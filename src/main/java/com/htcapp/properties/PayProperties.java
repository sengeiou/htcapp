package com.htcapp.properties;

//支付相关的状态属性
public class PayProperties {
    //交易类型
    public static final Integer TYPE_CHONGZHI=0;
    public static final Integer TYPE_TINGCHE=1;
    public static final Integer TYPE_GOUWU=2;

    //与支付有关的状态
    public static final Integer STATUS_NO=0;
    public static final Integer STATUS_SUCCESS=1;
    public static final Integer STATUS_CLOSE=2;

    //支付方式
       //微信
    public static final Integer METHOD_W_MA=1;
    public static final Integer METHOD_W_WAP=2;
    public static final Integer METHOD_W_APP=3;
      //支付宝
    public static final Integer METHOD_Z_MA=4;
    public static final Integer METHOD_Z_WAP=5;
    public static final Integer METHOD_Z_APP=6;

    //余额支付
    public static final Integer METHOD_YUER=7;
    //人工支付
    public static final Integer METHOD_USER=8;

    //预约状态
    public static final Integer STATUS_YUYUE_NO=0;
    public static final Integer STATUS_YUYUE_GO=1;
    public static final Integer STATUS_YUYUE_HAD=2;
    public static final Integer STATUS_YUYUE_REMOVE=3;
    public static final Integer STATUS_YUYUE_FAIL=4;
    public static final Integer STATUS_YUYUE_FINISH=10;
    public static final Integer STATUS_YUYUE_SHOUFEI=11;


    //停车场状态
    public static final Integer PARKING_STATUS_NULL=0;//空
    public static final Integer PARKING_STATUS_YUYUE=1;
    public static final Integer PARKING_STATUS_HAD=2;
    public static final Integer PARKING_STATUS_BAD=3;
    public static final Integer PARKING_STATUS_WAIT=4;

    //表示没有停车位空间
    public static final Integer  PARKING_SPACE_NONE=0;
}
