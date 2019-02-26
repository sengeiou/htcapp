package com.htcapp.mapper;

import com.htcapp.domain.Parkings;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import javax.annotation.security.PermitAll;
import java.util.List;

public interface ParkingsMapper {
    /**
     * 根据主键查询停车场信息
     * @param id
     * @return
     */
    @Select("select * from parkings where id=#{id}")
    Parkings findParkingsById(@Param("id") Integer id);

    /**
     * 根据主键更新一个停车场剩余车位信息
     * @param id
     * @param remain
     */
    @Update("update parkings set remainder_parking_spaces=#{remain} where id=#{id}")
    void updateCountById(@Param("id") Integer id, @Param("remain") int remain);

    /**
     * 根据高德云图查询停车场信息
     * @param id
     * @return
     */
    @Select("select * from parkings where api_id=#{id}")
    Parkings findParkingsByApiId(@Param("id") Integer id);

    /**
     *  模糊搜索停车场信息列表数量
     * @param search 模糊匹配停车场信息以及停车位置
     * @return
     */
    @Select("<script>  " +
            "select count(*) from parkings where 1=1 " +
            " <if test='search !=null '> and " +
            "( name like  CONCAT('%',#{search},'%')  " +
            " or location like CONCAT('%',#{search},'%') )  " +
            "</if>  </script>")
    Integer getCountBySearch(@Param("search") String search);

    /**
     *  分页搜索停车场信息
     * @param start
     * @param perValue
     * @param search
     * @return
     */
    @Select("<script>" +
            "select * from parkings where 1=1  " +
            "<if test='search !=null '>and ( name like  CONCAT('%',#{search},'%') " +
            " or location like CONCAT('%',#{search},'%') " +
            ")</if>" +
            "limit #{start},#{perValue}" +
            "</script>")
    List<Parkings> findBySearchAndage(@Param("start") Integer start, @Param("perValue") Integer perValue,
                                      @Param("search") String search);
}
