package com.htcapp.mapper;

import com.htcapp.domain.FundsRecords;
import com.htcapp.domain.UserBankcards;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface CardMapper {
    /*
    * 增删改查
    * */
    @Insert("insert into user_bankcards(uid,name,card,bank,created_at,updated_at) values(#{uid},#{name},#{card},#{bank},#{created_at},#{updated_at})")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    Integer addUserBankCards(UserBankcards userBankcards)throws Exception;

    @Delete("delete from user_bankcards where id=#{id} and uid=#{uid}")
    Integer deleteUserBankCardsByIdAndUid(@Param("id") int id,@Param("uid") Integer uid)throws  Exception;

    @Update("update user_bankcards set uid=#{uid}," +
            "name=#{name}," +
            "card=#{card}," +
            "bank=#{bank}," +
            "created_at=#{created_at}," +
            "updated_at=#{updated_at} " +
            "where id=#{id}")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    Integer updateUserBankCards(UserBankcards userBankcards)throws Exception;

    @Select("select * from user_bankcards where uid=#{uid}")
    UserBankcards[] findAllUserCards(@Param("uid")Integer uid)throws Exception;

    @Select("select * from user_bankcards where id=#{id}")
    UserBankcards findUserBankCardsById(@Param("id") Integer id)throws Exception;

    @Select("select * from user_banlcards where trade_no=#{tran} and uid=#{uid}")
    FundsRecords findFundsRecordsByTradeNoAndUid(@Param("tran") String id, @Param("uid") Integer uid);
}
