package com.htcapp.mapper;

import com.htcapp.domain.Cars;
import com.htcapp.domain.Users;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.Date;

/*
*  对应数据库的Users
* */
public interface UsersMapper {
    /**
     * 根据手机号查询用户
     * @param phoneNumber
     * @return
     * @throws Exception
     */
    @Select("select * from users where mobile=#{phoneNumber}")
    Users findByPhoneNumber(@Param("phoneNumber") String phoneNumber)throws Exception;

    /**
     * 插入一条新的用户数据
     * @param users
     * @return
     * @throws Exception
     */
    @Insert("insert into users(mobile,name,truename,avatar,nickname,gender,age,email," +
            "password,balance,remember_token,created_at,updated_at)" +
            "values(#{mobile},#{name},#{truename},#{avatar},#{nickname},#{gender},#{age},#{email}," +
            "#{password},#{balance},#{remember_token},#{created_at},#{updated_at})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Integer addUsers(Users users)throws Exception;

    /**
     * 更新用户信息。根据手机号
     * @param mobile
     * @param token
     * @throws Exception
     */
    @Update("update users set remember_token=#{token} where mobile=#{mobile}")
    void updateToken(@Param("mobile") String mobile, @Param("token")String token)throws Exception;


    /**
     * 更新用户信息
     * @param mobile 手机号
     * @param nickname 用户昵称
     * @param gender 用户性别
     * @param age 年龄
     * @param avatar 上传路径
     * @param updated_at 更新时间
     * @return
     * @throws Exception
     */
    @Update("update users set nickname=#{nickname},gender=#{gender},age=#{age},avatar=#{avatar},updated_at=#{updated_at} where mobile=#{mobile}")
    Integer updateByMobile(@Param("mobile") String mobile, @Param("nickname") String nickname, @Param("gender") Integer gender, @Param("age") Integer age, @Param("avatar") String avatar, @Param("updated_at") Date updated_at)throws Exception;

    /**
     * 根据主键查询用户
     * @param uid 用户主键
     * @return
     */
    @Select("select * from users where id=#{uid}")
    Users findUserByUid(@Param("uid") Integer uid);

    /**
     * 更新用户消费信息
     * @param uid
     * @param end
     */
    @Update("update users set balance=#{balance} where id=#{id}")
    void updateMoneyByUid(@Param("id") Integer uid, @Param("balance") BigDecimal end);
}
