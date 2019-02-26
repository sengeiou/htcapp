package com.htcapp.service;

import com.htcapp.domain.FundsRecords;
import com.htcapp.domain.UserBankcards;
import com.htcapp.mapper.CardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class CardService {
    @Autowired
    private CardMapper cardMapper;

    /**
     * 添加一条新的银行卡信息
     * @param userBankcards 用户新银行卡
     * @return
     * @throws Exception
     */
    @Transactional
    public Integer addUserBankCards(UserBankcards userBankcards)throws Exception{
        return this.cardMapper.addUserBankCards(userBankcards);
    }

    /**
     * 通过银行卡号主键删除一条银行卡信息
     * @param id 银行卡主键
     * @return
     * @throws Exception
     */
    @Transactional
    public int deleteUserBankCardsByIdAndUid(int id,Integer uid)throws Exception{
        return cardMapper.deleteUserBankCardsByIdAndUid(id,uid);
    }

    /**
     * 根据用户主键更新银行卡信息
     * @param userBankcards
     * @return
     * @throws Exception
     */
    @Transactional
    public Integer updateUserBankCards(UserBankcards userBankcards)throws Exception{
        return cardMapper.updateUserBankCards(userBankcards);
    }

    /**
     * 查询一个用户所有银行卡信息
     * @param uid
     * @return
     * @throws Exception
     */
    public UserBankcards[] findAllUserBankCards(Integer uid)throws Exception{
        return cardMapper.findAllUserCards(uid);
    }

    /**
     * 根据主键查询银行卡信息
     * @param id
     * @return
     * @throws Exception
     */
    public UserBankcards findUserBankCardsById(Integer id)throws Exception{
        return cardMapper.findUserBankCardsById(id);
    }

    public FundsRecords findFundsRecordsByTradeNoAndUid(String id, Integer uid) {
        return cardMapper.findFundsRecordsByTradeNoAndUid(id,uid);
    }
}
