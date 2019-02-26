package com.htcapp.api;

import com.htcapp.result.Result;
import com.htcapp.result.PageResult;
import com.htcapp.domain.Parkings;
import com.htcapp.result.SimpleResult;
import com.htcapp.service.ParkingsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 停车管理模块
 * Created by Jone on 2018-05-30.
 */
@RestController
public class ParkController {
    private static final Logger logger= LoggerFactory.getLogger(ParkController.class);

    @Value("${perParkingValue}")
    private Integer perParkingValue;
    @Autowired
    private ParkingsService parkingsService;

    /**
     *  根据id获取停车场信息
     * @param id 停车场id
     * @return
     */
    @GetMapping("/api/app/parking/{id}")
    public Result getParkingSpaces(@PathVariable("id")Integer id){
        try {

            Parkings parkings= this.parkingsService.findParkingsById(id);
            if (parkings==null){
                return SimpleResult.build(500,"没有相应停车位");
            }
            return parkings;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return SimpleResult.build(500,"服务器内部错误");
        }
    }

    /**
     * 根据高德ID获取停车场信息
     * @param id  需要的id
     * @return
     */
    @GetMapping("/api/app/parking/amap/{id}")
    public Result getParkingSpacesByGao(@PathVariable("id") Integer id){
        try {
            Parkings parking=this.parkingsService.findParkingsByApiId(id);
            if (parking==null){
                return SimpleResult.build(500,"没有相应停车位");
            }
            return parking;
        }catch (Exception e){
            logger.error(e.getMessage());
            return SimpleResult.build(500,"服务器内部错误");
        }
    }

    /**
     * 获取停车场信息列表
     * @return
     */
    @GetMapping("/api/app/parking")
    public  Result parkingInfo(HttpServletRequest request,String search, String with, Integer page){

        String url="http://"+request.getServerName()+":"
                             +request.getServerPort()
                             +request.getRequestURI();
        if (request.getQueryString()!=null){
            url+=("?"+request.getQueryString());
        }

        if (page==null||page<=0){
            page=1;
        }
        if (search!=null)
        search=search.trim();
        int start=(page-1)*perParkingValue;

        Integer count=this.parkingsService.getCountBySearch(search);

        List<Parkings>list=this.parkingsService.findBySearchAndPage(start,perParkingValue,search);

        return PageResult.build(perParkingValue,page,url,"null",list,count);
    }
}
