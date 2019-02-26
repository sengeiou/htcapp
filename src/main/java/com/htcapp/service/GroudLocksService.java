package com.htcapp.service;

import com.htcapp.domain.GroundLocks;
import com.htcapp.mapper.GroundLockMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GroudLocksService {

    @Autowired
    private GroundLockMapper groundLockMapper;

    public GroundLocks getGroundLocks(int lock_ID) {
        return this.groundLockMapper.findlocks(lock_ID);
    }

    @Transactional
    public Integer updateGroundLocks(int lock_ID, int lockstate) {
        return this.groundLockMapper.updateLocks(lock_ID,lockstate);
    }

}
