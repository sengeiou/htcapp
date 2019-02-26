package com.htcapp.mapper;

import com.htcapp.domain.GroundLocks;
import com.htcapp.domain.Users;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface GroundLockMapper {

    @Select("select * from ground_locks where lock_ID=#{lock_ID}")
    GroundLocks findlocks(@Param("lock_ID") int lock_ID);

    @Update("update ground_locks set lockstate=#{lockstate} where lock_ID=#{lock_ID}")
    Integer updateLocks(@Param("lock_ID") int lock_ID, @Param("lockstate") int lockstate);
}
