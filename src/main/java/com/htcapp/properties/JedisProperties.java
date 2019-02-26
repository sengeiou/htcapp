package com.htcapp.properties;

/**
 *  用静态类来保存redis想关属性，目的是对于JedisUtils
 *  中静态变量通过@Value赋值为空的解决方式
 *   by:zhangqi
 * */
public class JedisProperties {

    public static final Integer total=20;

    public static final Integer ideal=10;

    public static final Integer maxwait=10000;

    public static final Integer timeout=10000;

    public static final String host="47.94.18.185";

    public static final String password="Zhangqi123*";

    public static final Integer port=6379;

}
