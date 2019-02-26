package com.htcapp.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.htcapp.result.SimpleResult;
import com.htcapp.utils.JedisUtils;
import com.htcapp.utils.TokenUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    private  static  final  ObjectMapper objectMapper=new ObjectMapper();
    /**
     * 实现思路：
     * （1）如果遇到请求的url登录和发送验证码的直接放行
     * （2）如果是其他的URL，
     *      获取请求头中的token，
     *      然后根据token解析出来手机号，
     *      然后通过手机号生成key，
     *      通过这个key去Redis里面查找token，
     *      最后比对一下，通过就放行
     * */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        try {
            StringBuffer url = request.getRequestURL();
            if (request.getRequestURL().toString().indexOf("/api/app/login") != -1
                    || request.getRequestURL().toString().indexOf("/api/app/sendLoginCaptcha") != -1
                    ||request.getRequestURI().toString().indexOf("/api/app/parking")!=-1) {
                return true;
            }
            String authorization = request.getHeader("Authorization");
            String[] split = authorization.split(" ");
            final String token = split[1];
            String mobile = TokenUtils.getMobileByToken(token);
            String key = TokenUtils.createTokenKey(mobile);
            String token_catch = JedisUtils.getVal(key);
            if (token != null && token_catch != null && token.equals(token_catch)) {
                return true;
            }
        }catch (Exception e){
            sendJsonMessage(response,"无法从请求头中获取token");
            return false;
        }
        sendJsonMessage(response,"验证有误");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }

    private static void sendJsonMessage(HttpServletResponse response,String message){
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();

            SimpleResult result= SimpleResult.build(500,message);

            writer.write(objectMapper.writeValueAsString(result));
            writer.close();
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
