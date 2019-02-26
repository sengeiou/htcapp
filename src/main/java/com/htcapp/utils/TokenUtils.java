package com.htcapp.utils;

import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Random;
import java.util.UUID;

/**
 * 生成用户token的工具
 * 注意：redis中数据的存储如下方式：
 *     token_手机号:token_value
 */
public class TokenUtils {

    private static final Base64.Decoder decoder=Base64.getDecoder();

    private static final Base64.Encoder encoder=Base64.getEncoder();

    public static final Long ttl=Long.valueOf(604800);

    public static final Long times=Long.valueOf(2592000);

    /**
     * 创建token，token由两部分组成.
     *     head: 加密的手机号
     *     con:  加密的随机数
     * @param phone 需要被加密的手机号
     * @return
     */
    public static String createToken(String phone){

        StringBuilder stringBuilder=new StringBuilder();

        String head=encoder.encodeToString(phone.getBytes());
        String con=UUID.randomUUID().toString();
        con=encoder.encodeToString(con.getBytes());
        stringBuilder.append(head)
                     .append(".")
                     .append(con);
        return  stringBuilder.toString();
    }

    /**
     * 从token中获取手机号
     * @param token  必须为一个非空的字符串
     * @return  如果正确返回被解析正确的手机号
     */
    public static String getMobileByToken(String token){
        if (token==null||token.trim().equals("")){
            return null;
        }


        int end=token.indexOf(".");
        if (end<0){
            return null;
        }
        String head=token.substring(0,end);
        return new String(decoder.decode(head));
    }

    /**
     * 创建tokenKey
     * @param phone 作为key一部分的phone
     * @return  返回被创建成功的主键
     */
    public static String createTokenKey(String phone){
        return "token_"+phone;
    }

    /**
     * 通过请求获取token，
     * 通过token获取mobile
     * @param request
     * @return
     */
    public static String getMobileByRequest(HttpServletRequest request){
        String value=request.getHeader("Authorization");


        String token=value.split(" ")[1];

        String mobile= TokenUtils.getMobileByToken(token);

        return mobile;
    }
}
