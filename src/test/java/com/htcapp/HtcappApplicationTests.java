/*package com.htcapp;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;
import com.htcapp.service.push.JiGuangPush;
import com.htcapp.service.push.JiGuangPushData;
import com.htcapp.utils.JedisUtils;
import com.htcapp.utils.JsonUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

*//*@RunWith(SpringRunner.class)
@SpringBootTest*//*

public class HtcappApplicationTests {

	@Test
	public void contextLoads() {
		Jedis jedis=JedisUtils.getJedis();
		String value=jedis.get("AllCount");
		System.out.println(value);
	}
	@Test
	public void test1(){
		Date date=new Date();
		Long value=date.getTime();
		System.out.println(value);
	}

	@Test
	public void test2(){
		DecimalFormat df=new DecimalFormat("#.00");
		String val=df.format(new BigDecimal(30.00));
		System.out.println(val);
	}

	@Test
	public void test3(){
		Long times=new Long("14210502749701271");

		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date temp=new Date(times);

		String value=simpleDateFormat.format(temp);

		System.out.println(value);


		Date date=new Date();

		System.out.println(date.getTime());;
	}

	@Test
	public void test4(){
		String secret="45f47d18151cce993099e98f";

		String APPKEY="ef81de0949281b61ba7eb720";
		JPushClient jpushClient=new JPushClient(secret,APPKEY,null, ClientConfig.getInstance());
		PushPayload payload = buildPushObject_android_tag_alertWithTitle();

		try {
			PushResult result = jpushClient.sendPush(payload);

		} catch (APIConnectionException e) {
			// Connection error, should retry later

		} catch (APIRequestException e) {
			// Should review the error, and fix the request
		}

	}

	public static PushPayload buildPushObject_android_tag_alertWithTitle() {
		return PushPayload.newBuilder()
				.setPlatform(Platform.android())
				.setAudience(Audience.tag("tag1"))
				.setNotification(Notification.android("OK", "123", null))
				.build();
	}

	@Test
	public void test6(){
		JiGuangPushData jiGuangPushData=JiGuangPushData.build(2,3);
		String value=JsonUtils.objectToString(jiGuangPushData);
		System.out.println(value);
	}

	@Test
	public void test7(){
		long start=new Date().getTime()/1000;

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long  end =new Date().getTime()/1000;

		System.out.println(end-start);
		BigDecimal per=new BigDecimal(2.0);

		BigDecimal sum=new BigDecimal(end-start);

		sum=sum.multiply(per);
		sum.setScale(2);

		System.out.println(sum.toString());
	}

	@Test
	public void test8(){
		Long time=new Date().getTime();
		time=time/1000;
		System.out.println(time);
	}
	@Autowired
	private JiGuangPush jiGuangPush;
	@Test
	public void test9(){
		jiGuangPush.jiGuangPush("hello",JiGuangPushData.build(2,333));
	}
	@Test
	public void tets10(){
		String name="鲁";
	}
	@Test
    public void tets11(){
	    Integer b=new Integer(0);

        System.out.println(new Integer(0).equals(b));
    }
}*/
