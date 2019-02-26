package com.htcapp.utils;

/**
 * 用于字符串的一些操作
 */
public class StringUtils {

    public static boolean isEmpty(String value){
        if (value==null||value.trim().equals("")){
            return true;
        }
        return false;
    }
}
