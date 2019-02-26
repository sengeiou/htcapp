package com.htcapp.mapper;


import com.htcapp.domain.Cars;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CarsMapper {

    /**
     * 插入一个车辆的信息
     * @param cars 插入的车辆
     */
    @Insert("insert into cars(uid,number_plate,created_at,updated_at) " +
            "values(#{uid},#{number_plate},#{created_at},#{updated_at})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addCar(Cars cars);

    /**
     * 根据id查询一个车辆
     * @param id
     * @return
     */
    @Select("select *  from cars where id=#{id}")
    Cars findCarsById(@Param("id") Integer id);

    /**
     * 根据用户id查询车辆信息
     * @param uid
     * @return
     * @throws Exception
     */
    @Select("select * from cars where uid=#{uid}")
    List<Cars> findCarsByUid(@Param("uid") Integer uid)throws Exception;

    /**
     * 根据车牌ID查询车牌信息
     * @param carID
     * @return
     */
    @Select("select * from cars where number_plate=#{carID}")
    Cars findCarsByCarID(String carID);
}
