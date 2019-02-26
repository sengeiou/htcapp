package com.htcapp.service;


import com.htcapp.domain.Cars;
import com.htcapp.domain.Users;
import com.htcapp.mapper.UsersMapper;
import com.htcapp.utils.JedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    @Autowired
    private UsersMapper usersMapper;

    /**
     * 根据电话号码查询用户信息
     * @param phoneNumber  用户电话号码
     * @return
     * @throws Exception
     */
    public Users findUsersByPhoneNumber(String phoneNumber)throws Exception{
        return this.usersMapper.findByPhoneNumber(phoneNumber);
    }

    /**
     * 添加新用户
     * @param users
     * @return
     * @throws Exception
     */
    @Transactional
    public Integer addUsers(Users users)throws Exception{
        return  this.usersMapper.addUsers(users);
    }

    /**
     * 更新用户token
     * @param mobile
     * @param token
     * @throws Exception
     */
    @Transactional
    public void updateToken(String mobile,String token) throws Exception {
        this.usersMapper.updateToken(mobile,token);
    }
}
