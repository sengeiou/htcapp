package com.htcapp.mapper;

import com.htcapp.domain.FundsRecords;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

public interface FundsRecordsMapper {
    /**
     * 获取一个用户的预约停车数量
     * @param id
     * @return
     */
    @Select("select count(*) from funds_records where uid=#{uid}")
    Integer getCount(Integer id);

    /**
     * 分页查询一个用户的预约信息
     * @param start 开始位置
     * @param perValue 总数
     * @param uid 用户主键
     * @return
     */
    @Select("select * from funds_records where uid=#{uid} order by id desc limit #{start},#{perValue}")
    List<FundsRecords> findFuncsRecordsByPageAndUid(@Param("start")Integer start,@Param("perValue")Integer perValue,@Param("uid")Integer uid);

    /**
     *  添加一条预约信息
     * @param fundsRecords
     */
    @Insert("insert into funds_records set uid=#{uid},amount=#{amount}," +
            "type=#{type},status=#{status},result_operation=#{result_operation}," +
            "trade_no=#{trade_no},api_trade_no=#{api_trade_no},pay_method=#{pay_method}," +
            "raw_data=#{raw_data},subject=#{subject},remark=#{remark},created_at=#{created_at}," +
            "updated_at=#{updated_at}")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void add(FundsRecords fundsRecords);

    @Select("select * from funds_records where trade_no=#{trade_no} and uid=#{uid}")
    FundsRecords findFundsRecordsByTradeNoAndUid(@Param("trade_no") String id, @Param("uid") Integer uid);

    @Update("update funds_records set status=#{status},api_trade_no=#{api_trade_no},raw_data=#{raw_data},updated_at=#{updated_at} "+
            "where trade_no=#{trade_no}")
    void updateFundsRecords(int status, String api_trade_no, String raw_data, Date updated_at, String trade_no);

    @Select("select uid from funds_records where trade_no=#{trade_no}")
    int findUidByTradeNo(String trade_no);
}
