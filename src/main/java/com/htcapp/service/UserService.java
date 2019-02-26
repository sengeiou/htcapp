package com.htcapp.service;

import com.htcapp.domain.Users;
import com.htcapp.mapper.UsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class UserService {

    @Autowired
    private UsersMapper usersMapper;

    /**
     * 查询用户信息根据手机号
     * @param mobile 手机号
     * @return
     * @throws Exception
     */
    public Users findUserInfo(String mobile) throws Exception {
        return this.usersMapper.findByPhoneNumber(mobile);
    }

    @Transactional
    public Integer updateByMobile(String mobile, String nickname, Integer gender, Integer age, String avatar) throws Exception {
        Date updated_at = new Date();
        return this.usersMapper.updateByMobile(mobile,nickname,gender,age,avatar,updated_at);
    }
    @Transactional
    public void pay(Integer uid, BigDecimal end) {
        Users user=this.usersMapper.findUserByUid(uid);

        end=user.getBalance().subtract(end);
        user.setBalance(end);
        this.usersMapper.updateMoneyByUid(uid,end);
    }

    public void updateMoneyByUid(Integer id, BigDecimal bigDecimal) {
        this.usersMapper.updateMoneyByUid(id,bigDecimal);
    }

    @Transactional
    public void recharge(Integer uid, BigDecimal amount) {
        Users user=this.usersMapper.findUserByUid(uid);

        amount=user.getBalance().add(amount);
        user.setBalance(amount);
        this.usersMapper.updateMoneyByUid(uid,amount);
    }
}
