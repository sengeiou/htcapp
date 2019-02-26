package com.htcapp.api;

import com.htcapp.domain.Errors;
import com.htcapp.result.Result;
import com.htcapp.result.SimpleResult;
import com.htcapp.domain.Users;
import com.htcapp.result.UserResult;
import com.htcapp.service.AuthService;
import com.htcapp.utils.AccountValidatorUtil;
import com.htcapp.utils.JedisUtils;
import com.htcapp.utils.SMSUtils;
import com.htcapp.utils.TokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户登录退出模块
 */
@RestController
public class AuthController {
    private static final Logger logger= LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    /**
     * 用户登录/注册
     *     1.  判断账号是否正确
     *     2.  判断验证码是否存在
     *     3.  以上两步顺利通过，通过redis获取用户登录的token
     *          （1）如果token不存在，表示首次登陆，创建一个token与
     *              当前用户关联，token格式 token_+mobile：token值，
     *               将其放入数据库相应字段，并将其放入缓存redis中，默认
     *               有效时间一个月
     *           (2) 如果发现token存活时间小于一星期，重新如上述创建新token
     * @param mobile  用户登录手机号
     * @param verify  用户登录验证码
     * @return  登录结果
     *
     * 成功返回：
     *
     *
     * 失败返回：
     *
     */
    @PostMapping("/api/app/login")
    public Result login(String mobile, String verify){
        try {
            if (mobile == null || mobile.trim().equals("")) {
                return SimpleResult.build(422, "The given data was invalid.", Errors.PHONENONE_ERROR);
            } else if (!AccountValidatorUtil.isMobile(mobile)) {
                return SimpleResult.build(422, "The given data was invalid.", Errors.PHONEFORMAT_ERROR);
            } else {
                String vcode = JedisUtils.getVal(mobile);
                String tokenKey=null;
                String token=null;
                Long ttl;
                if (vcode == null) {
                    return SimpleResult.build(500, "短信验证码不存在或已经过期");
                } else {
                    if (!vcode.trim().equals(verify)) {
                        return SimpleResult.build(500, "短信验证码不正确");
                    } else {
                        tokenKey= TokenUtils.createTokenKey(mobile);

                        token=JedisUtils.getVal(tokenKey);

                        ttl=JedisUtils.ttl(tokenKey);

                        Users users = this.authService.findUsersByPhoneNumber(mobile);


                        if ((token==null)||(ttl>0&&ttl<TokenUtils.ttl)) {//如果token为空的话就创建一个。并保存到数据库当中
                            token = TokenUtils.createToken(mobile);
                            this.authService.updateToken(mobile,token);
                            JedisUtils.putValTimeout(tokenKey,token,TokenUtils.times);
                        }


                        UserResult result= UserResult.build(200,"User Authenticated",users,token);
                        return result;
                    }
                }
            }
        }catch (Exception e) {
            logger.error(e.getMessage());
            return SimpleResult.build(500,"服务器内部错误");
        }
    }

    /**
     * 根据phoneNumber查询用户。
     * 如果用户不存在就新建一个,表示第一次注册
     * 然后发送验证码，在token中存储
     * @param mobile
     * @return
     */
    @PostMapping("/api/app/sendLoginCaptcha")
    public Result findUserByPhoneNumber(String mobile){
        Users users=null;
        try{

        if (mobile==null||mobile.trim().equals("")){
            return SimpleResult.build(422,"The given data was invalid.",Errors.PHONENONE_ERROR);
        }else if(!AccountValidatorUtil.isMobile(mobile)){
            return SimpleResult.build(422,"The given data was invalid.",Errors.PHONEFORMAT_ERROR);
        }
        else {
            users = this.authService.findUsersByPhoneNumber(mobile);

            if (users == null) {

                users=Users.initUser(mobile);

                this.authService.addUsers(users);
            }

            String vcode=SMSUtils.randomCode();

            SMSUtils.sendMessgae(mobile,vcode);

            JedisUtils.putValTimeout(mobile,vcode, (long) 180);

            return SimpleResult.build(200,"短信验证码发送成功");
        }


        }catch (Exception e){
            logger.error(e.getMessage());
            return SimpleResult.build(500,"服务器内部错误");
        }
    }
}
