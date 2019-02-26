package com.htcapp.mapper;

import com.htcapp.domain.BarrierGates;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 车闸管理的 信息
 */
public interface BarrierGatesMapper {
    /**
     * 根据道闸编号查询道闸信息
     * @param bid
     * @return
     */
    @Select("select * from barrier_gates where bid=#{bid}")
     BarrierGates findBarrierGatesByLock(@Param("bid") String bid);

    @Insert("insert into barrier_gates(pid,bid,bname,btype) value(#{pid},#{bid},#{bname},#{btype})")
    public Integer addBarrierGates(@Param("pid") String pid, @Param("bid") String bid, @Param("bname") String bname);

    @Insert("insert into barrier_gates(pid,bid,bname,btype,bdescription) value(#{pid},#{bid},#{bname},#{btype},#{bdescription})")
    public Integer addBarrierGatesInfo(@Param("pid") int pid, @Param("bid") String bid, @Param("bname") String bname, @Param("btype") Integer btype, @Param("bdescription") String bdescription);

}
