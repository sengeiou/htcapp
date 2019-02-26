package com.htcapp.service;

import com.htcapp.domain.Parkings;
import com.htcapp.mapper.ParkingsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class ParkingsService {
    @Autowired
    private ParkingsMapper parkingsMapper;

    /**
     * 根据id查询停车场信息
     * @param id 停车场id
     * @return
     * @throws Exception
     */
    public Parkings findParkingsById(Integer id)throws Exception {
        return this.parkingsMapper.findParkingsById(id);
    }

    /**
     * 根据id更新停车场剩余车位信息
     * @param id 停车场id
     * @param remain 剩余车位数
     */
    @Transactional
    public void updateCountById(Integer id, int remain) {
        this.parkingsMapper.updateCountById(id,remain);
    }

    /**
     * 根据高德云图id查询信息
     * @param id
     * @return
     */
    public Parkings findParkingsByApiId(Integer id) {
        return this.parkingsMapper.findParkingsByApiId(id);
    }

    /**
     * 根据搜索信息显示匹配的数量
     * @param search 搜索信息
     * @return
     */
    public Integer getCountBySearch(String search) {
        return this.parkingsMapper.getCountBySearch(search);
    }

    /**
     * 根据搜索信息分页显示
     * @param start
     * @param perValue
     * @param search
     * @return
     */
    public List<Parkings> findBySearchAndPage(int start, Integer perValue, String search) {
        return this.parkingsMapper.findBySearchAndage(start,perValue,search);
    }
}
