package com.htcapp.mapper;

import com.htcapp.domain.ParkingSpaces;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by Jone on 2018-06-03.
 */
public interface ParkingSpacesMapper {
    /**
     *  查询一个停车场某个状态下的停车空间
     * @param id 停车场id
     * @param status  状态
     * @return
     */
    @Select("select * from parking_spaces where pid=#{pid} and status=#{status}")
    List<ParkingSpaces> findByPidAndStatus(@Param("pid") Integer id,@Param("status") Integer status);

    /**
     * 根据id设置一个停车位的停车状态
     * @param id 停车位id
     * @param status 停车位状态
     */
    @Update("update parking_spaces set status=#{status} where id=#{id}")
    void updateStatusById(@Param("id") Integer id,@Param("status") Integer status);

    /**
     * 根据id查询一个停车位
     * @param id
     * @return
     */
    @Select("select * from parking_spaces where id=#{id}")
    ParkingSpaces findById(@Param("id") Integer id);
}
