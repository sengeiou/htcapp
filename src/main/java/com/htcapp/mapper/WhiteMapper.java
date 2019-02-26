package com.htcapp.mapper;

import com.htcapp.domain.White;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by Jone on 2018-06-18.
 */
public interface WhiteMapper {
    @Select("select * from white_list where pid=#{pid}")
    List<White> findByPid(@Param("pid") Integer pid);

    @Insert("insert into white_list(cid,pid)values(#{cid},#{pid})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void add(White white);

    @Delete("delete from white_list where pid=#{pid} and cid=#{cid}")
    void delete(@Param("pid") Integer pid,@Param("cid") Integer cid);
    @Select("select * from white_list where pid=#{pid} and cid=#{cid}")
    White findByPidAndCarID(@Param("pid") Integer pid, @Param("cid") Integer cid);
}
