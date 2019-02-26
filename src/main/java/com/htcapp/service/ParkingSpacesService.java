package com.htcapp.service;

import com.htcapp.domain.ParkingSpaces;
import com.htcapp.mapper.ParkingSpacesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ParkingSpacesService {

    @Autowired
    private ParkingSpacesMapper parkingSpacesMapper;

    /**
     *  根据停车场id与状态查询匹配的停车场记录信息
     * @param id  停车场id
     * @param status 状态
     * @return
     */
    public List<ParkingSpaces> findByPidAndStatus(Integer id,Integer status) {
        return this.parkingSpacesMapper.findByPidAndStatus(id,status);
    }

    /**
     *  根据主键更新状态信息
     * @param id 停车位主键主键
     * @param status 状态
     */
    @Transactional
    public void updateStatusById(Integer id,Integer status) {
        this.parkingSpacesMapper.updateStatusById(id,status);
    }

    /**
     * 根据id查询停车场信息
     * @param id
     * @return
     */
    public ParkingSpaces findById(Integer id) {
        return  this.parkingSpacesMapper.findById(id);
    }
}
