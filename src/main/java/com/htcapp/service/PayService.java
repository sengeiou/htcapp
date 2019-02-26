package com.htcapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;


/**
 * 支付相关服务都放在这里面
 */
@Service
public class PayService {
    @Autowired
    private UserService userService;

    /**
     * 调用用户余额进行支付
     * @param uid  用户uid
     * @param end  支付金额
     */
    @Transactional
    public void payByUser(Integer uid, BigDecimal end) {
            userService.pay(uid,end);
    }
}
