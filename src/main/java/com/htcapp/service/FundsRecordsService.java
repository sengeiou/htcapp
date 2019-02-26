package com.htcapp.service;

import com.htcapp.domain.FundsRecords;
import com.htcapp.mapper.FundsRecordsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class FundsRecordsService {
    @Autowired
    private FundsRecordsMapper fundsRecordsMapper;


    /**
     *  根据uid查询一个用户的所有资金变动总数
     * @param id  用户主键
     * @return
     */
    public Integer getCount(Integer id){
        return this.fundsRecordsMapper.getCount(id);
    }

    /**
     * 分页显示用户自己变动记录
     * @param start 开始
     * @param perValue 显示的记录数
     * @param uid 用户主键
     * @return
     */
    public List<FundsRecords> findFundsRecordsByPageAndUid(Integer start,Integer perValue,Integer uid){
        return this.fundsRecordsMapper.findFuncsRecordsByPageAndUid(start,perValue,uid);
    }

    /**
     * 添加资金变动记录
     * @param fundsRecords
     */
    @Transactional
    public void add(FundsRecords fundsRecords) {
        this.fundsRecordsMapper.add(fundsRecords);
    }

    public FundsRecords findFundsRecordsByTradeNoAndUid(String id, Integer uid) {
        return this.fundsRecordsMapper.findFundsRecordsByTradeNoAndUid(id,uid);
    }

    @Transactional
    public void update(int status, String api_trade_no, String raw_data, Date updated_at, String trade_no) {
        this.fundsRecordsMapper.updateFundsRecords(status, api_trade_no, raw_data, updated_at, trade_no);
    }

    public int findUid(String trade_no){
        return this.fundsRecordsMapper.findUidByTradeNo(trade_no);
    }
}
