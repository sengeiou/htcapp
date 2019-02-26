package com.htcapp.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.htcapp.domain.Users;

/**
 * Created by Jone on 2018-06-14.
 */
public class UserResult extends SimpleResult{
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String token;//返回登录成功token
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Users user;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public static UserResult build(Integer status_code, String message, Users users, String token){
        UserResult userResult=new UserResult();
        userResult.setStatus_code(status_code);
        userResult.setMessage(message);
        userResult.setUser(users);
        userResult.setToken(token);
        return userResult;
    }
}
