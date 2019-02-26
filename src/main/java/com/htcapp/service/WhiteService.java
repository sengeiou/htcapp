package com.htcapp.service;

import com.htcapp.domain.White;
import com.htcapp.mapper.WhiteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*
* 白名单相关内容
* */
@Service
public class WhiteService {

    @Autowired
    private WhiteMapper whiteMapper;

    public List<White>  findByPid(Integer pid){
        return this.whiteMapper.findByPid(pid);
    }

    @Transactional
    public void addWhite(White white) {
         this.whiteMapper.add(white);
    }

    public void delete(Integer pid, Integer cid) {
        this.whiteMapper.delete(pid,cid);
    }

    public White findByPidAndCarID(Integer pid, Integer cid) {
        return this.whiteMapper.findByPidAndCarID(pid,cid);
    }
}

