package com.htcapp.utils;

import com.htcapp.properties.JedisProperties;
import org.junit.Test;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Set;

/**
 * 用于操作redis数据库之中的内容
 */
@Component
public class JedisUtils{

    private static final JedisPool POOL;

    static{
        JedisPoolConfig config=new JedisPoolConfig();
        config.setMaxTotal(JedisProperties.total);
        config.setMaxIdle(JedisProperties.ideal);
        POOL=new JedisPool(config,JedisProperties.host,JedisProperties.port);
    }
    public static Jedis getJedis(){
        Jedis jedis= POOL.getResource();
        jedis.auth(JedisProperties.password);
        return jedis;
    }

    /**
     * 向redis中添加元素
     * @param key 添加的key
     * @param value  添加的value
     */
    public static void putVal(String key,String value){
        Jedis jedis=getJedis();
        jedis.set(key,value);
        jedis.close();
    }

    /**
     * 设置元素存活时间
     * @param key 元素的key
     * @param tiemout 元素的超时时间
     */
    public static void setKeyTimeout(String key,Long tiemout){
        Jedis jedis=JedisUtils.getJedis();
        jedis.expire(key, tiemout.intValue());
        jedis.close();
    }
    /**
     *  添加元素并设置存货时间
     * @param key 添加的key
     * @param value  添加的value
     * @param timeOut 存货时间，以秒为单位
     */
    public static void putValTimeout(String key,String value,Long timeOut){
        putVal(key,value);
        setKeyTimeout(key,timeOut);
    }

    /**
     * 根据key获取value值
     * @param key
     * @return
     */
    public static String getVal(String key){
        Jedis jedis=JedisUtils.getJedis();

        String value=jedis.get(key);
        jedis.close();
        return value;
    }
    public static Long ttl(String key){
        Jedis jedis=JedisUtils.getJedis();

        Long time= jedis.ttl(key);
        return time;
    }
    public static void saddTimeOut(String key,String value,Integer time){
        Jedis jedis=JedisUtils.getJedis();

        if (jedis.scard(key)==0){
            jedis.expire(key,time);
        }

        jedis.sadd(key,value);
        jedis.close();
    }
    public  static Set<String> smembers(String key){
        Jedis jedis=JedisUtils.getJedis();

        Set<String> set= jedis.smembers(key);

        jedis.close();
        return  set;
    }
    public static Long del(String  key){
        Jedis jedis=JedisUtils.getJedis();

        Long count=jedis.del(key);

        jedis.close();
        return count;
    }
    @Test
    public void test1(){
        if (JedisUtils.getVal("213")==null){
            System.out.println("OK");
        }
        System.out.println(JedisUtils.ttl("kell"));;
    }

    public static Long srem(String key, String member) {
        Jedis jedis=JedisUtils.getJedis();

        Long count=jedis.srem(key,member);
        jedis.close();
        return  count;
    }
}
