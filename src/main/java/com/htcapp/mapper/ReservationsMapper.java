package com.htcapp.mapper;

import com.htcapp.domain.Reservations;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Jone on 2018-06-03.
 */
public interface ReservationsMapper {
    /*
    * 查询一个用户停车状态状态为
    *     0  1  2的停车信息
    *  作用：可以用它来查询一个不可能继续停车的用户
    * */
    @Select("select * from reservations where uid=#{id} and status in(0,1,2)")
    Reservations findDontByUid(Integer id);

    /*
    *   插入一条预约数据
    *         包括：  uid，pid，rid，number_plate_id，status，appointment
    * */
    @Insert("insert into reservations (uid,pid,rid,number_plate_id,status,appointment) " + "values(#{uid},#{pid},#{rid},#{number_plate_id},#{status},#{appointment})")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    void addReservations(Reservations reservations);
    /*
    *  根据预约表的主键更新状态
    * */
    @Update("update reservations set status=#{status} where id=#{id}")
    void setStatusById(@Param("id") Integer id, @Param("status") Integer status);

    /**
     *  根据id查询停车状态
     * @param id
     * @return
     */
    @Select("select * from reservations where id=#{id}")
    Reservations getReservationsById(@Param("id") Integer id);
    /**
     *  根据number_plate_id查询停车状态
     * */
    @Select("select * from reservations where number_plate_id=#{number_plate_id} and pid=#{pid}")
    List<Reservations> getReservationsByNumberPlateIdAndPid(@Param("number_plate_id")Integer number_plate_id,@Param("pid")Integer pid);
    /**
     *  根据id更新一个remark信息
     * @param id
     * @param reason
     */
    @Update("update reservations set remark=#{remark} where id=#{id}")
    void setReasonById(@Param("id") Integer id, @Param("remark") String reason);

    /**
     * 根据uid查询一个用户的全部信息
     * @param id 用户主键
     * @return
     */
    @Select("select * from  reservations where uid=#{uid} order by id desc limit 1")
    Reservations findLastMettingByUid(@Param("uid") Integer id);

    /**
     * 根据uid查询一个用户的所有停车记录数
     * @param uid
     * @return
     */
    @Select("select count(*) from reservations where uid=#{uid}")
    Integer getCountByUid(Integer uid);

    /**
     *  根据uid分页查询用户停车记录信息
     * @param start start 分页开始
     * @param perValue  每页查询记录数
     * @param id  用户主键
     * @return
     */
    @Select("select * from reservations where uid=#{uid} order by id desc  limit #{start},#{perValue}")
    List<Reservations> findReservationsByPageAndUid(@Param("start") Integer start, @Param("perValue") Integer perValue, @Param("uid") Integer id);

    /**
     * 根据id更新状态以及离开时间
     * @param i  状态
     * @param leave  离开事件
     * @param id  停车id
     */
    @Update("update reservations set status=#{status},`leave`=#{leave} where id=#{id}")
    void updateStatusAndLeaveById(@Param("status") Integer i, @Param("leave") Long leave, @Param("id") Integer id);

    /**
     * 根据车牌号id，停车场id，用户状态更新停车信息
     * @param carID 车牌号id
     * @param pid 停车场id
     * @param status 用户状态
     * @return
     */
    @Select("select * from reservations where number_plate_id=#{carID} and pid=#{pid} and status=#{status}")
    List<Reservations> findReservationsByCarIDAndParkingID(@Param("carID") Integer carID, @Param("pid") Integer pid, @Param("status") Integer status);

    /**
     * 根据id更新应该消费信息
     * @param end  应该消费的记录
     * @param id  主键
     */
    @Update("update reservations set due_charges=#{end} where id=#{id}")
    void updateDueChargesById(@Param("end") BigDecimal end, @Param("id") Integer id);

    /**
     * 根据id更新真正消费信息
     * @param end  总消费信息
     * @param id  主键
     */
    @Update("update reservations set charges=#{end} where id=#{id}")
    void updateChargesById(@Param("end") BigDecimal end, @Param("id") Integer id);

    /**
     * 根据id更新停车信息
     * */
    @Update("update reservations set parking_time=#{parking_time},status=#{status} where id=#{id}")
    void updateParkingInfoById(@Param("parking_time")Long parking_time,@Param("status")Integer status,@Param("id")Integer id);

    @Select("select * from reservations where uid=#{id} and status in(1,12,2)")
    Reservations findCanNotPark(int uid);

    @Select("select * from reservations where id=#{id}")
    Reservations findById(@Param("id") Integer rid);
}
