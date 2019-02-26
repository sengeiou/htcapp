package com.htcapp.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by Jone on 2018-06-11.
 */
public class JsonUtils {
    private static final Logger logger=LoggerFactory.getLogger(JsonUtils.class);
    private static final ObjectMapper mapper=new ObjectMapper();
    public static String objectToString(Object o){
        String value= null;
        try {
            value = mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(value);
            return value;
    }

    public static String  getJsonValueByName(String values,String name) throws Exception {
        JsonNode jsonNode= null;
        try {
            System.out.println(values);
            jsonNode = mapper.readTree(values);
            String value=jsonNode.get(name).toString();
            return value;
        } catch (Exception e) {
            throw new Exception("json有误，解析失败");
        }
    }

    public static String getJsonNodeValue(String values,String name){
        JsonNode jsonNode= null;
        String result=null;
        try {
           jsonNode= mapper.readTree(values);
           jsonNode = jsonNode.findValue(name);

          result=  jsonNode.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static <T> T stringToObject(String value,Class<T> clazz) throws Exception {
        T result= null;
        try {
            result = mapper.readValue(value,clazz);
            return  result;
        } catch (Exception e) {
           throw  new Exception("json串有误");
        }
    }
}
