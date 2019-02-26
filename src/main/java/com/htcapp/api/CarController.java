package com.htcapp.api;

import com.htcapp.domain.*;
import com.htcapp.result.*;
import com.htcapp.properties.CarProperties;
import com.htcapp.properties.PayProperties;
import com.htcapp.result.CarResult;
import com.htcapp.result.PageResult;
import com.htcapp.result.ReservationsResult;
import com.htcapp.result.Result;
import com.htcapp.result.SimpleResult;
import com.htcapp.service.*;
import com.htcapp.utils.JedisUtils;
import com.htcapp.utils.StringUtils;
import com.htcapp.utils.TokenUtils;
import org.apache.ibatis.annotations.Delete;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 用户车位模块
 * Created by Jone on 2018-05-30.
 */
@RestController
public class CarController {

    private static final Logger logger= LoggerFactory.getLogger(CarController.class);

    @Value("${baseValue}")
    private Integer value;

    @Value("${perValue}")
    private Integer perValue;//每页的数据量

    @Value("${timeOut}")
    private Integer timeOut;

    @Autowired
    private AuthService authService;

    @Autowired
    private CarsServce carsServce;

    @Autowired
    private ParkingsService parkingsService;

    @Autowired
    private ReservationsService reservationsService;

    @Autowired
    private ParkingSpacesService parkingSpacesService;

    @Autowired
    private WhiteService whiteService;
    /**
     *  添加车牌号
     *   一个车牌号只能被一个人拥有
     *     先判断车牌号是否存在，如果存在返回失败
     *     然后添加
     * @param request  通过request获取请求头
     * @param number_plate 获取添加的车牌号
     * @return  返回添加的结果
     */
    @PostMapping("/api/app/car")
    public Result addCar(HttpServletRequest request, String number_plate){

        if (StringUtils.isEmpty(number_plate)){
            return SimpleResult.build(422,"The given data was invalid.",Errors.NUMBERNONE_ERROR);
        }else if (number_plate.length()!=7|| !CarProperties.getCar(number_plate.charAt(0)+"")){
            return SimpleResult.build(422,"The given data was invalid.", Errors.NUMBERFORMAT_ERROR);
        }
        String mobile= TokenUtils.getMobileByRequest(request);

        Cars cars=null;

        try {
            Cars temp=this.carsServce.findCarsByCarID(number_plate);

            if (temp!=null){
                return SimpleResult.build(422,"车牌号码已经存在");
            }
            Users user=this.authService.findUsersByPhoneNumber(mobile);

            cars=new Cars(null,user.getId(),number_plate,new Date(),new Date());

            this.carsServce.addCars(cars);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return SimpleResult.build(500,"服务器内部错误");
        }
        return CarResult.build(cars);
    }

    /**
     * 逻辑：
     *    0: 判断用户价格是否充足，如果不充足的话返回用户余额不足
     *    1。验证通过后，先查看当前车牌号是否与车主对应
     *    2。验证是否有相应的停车场
     *    3。判断一个用户是否重复停车
     *    4。判断停车场车位是否有空闲
     *    5。 判断是否有每个停车位的锁，如果有的话设置一个被预约
     *    6。可用车位数减一
     * @param request
     * @param id 预约的停车场id
     * @param number_plate_id 预约车牌号的Id
     * @return
     */
    @PostMapping("/api/app/reserve/{id}")
    public Result parkingReservation(HttpServletRequest request, @PathVariable Integer id, Integer number_plate_id){

        if (number_plate_id==null){
            return SimpleResult.build(230,"车牌号ID缺失");
        }
        try {
            String mobile = TokenUtils.getMobileByRequest(request);

            Users user = this.authService.findUsersByPhoneNumber(mobile);

            BigDecimal bigDecimal=user.getBalance();

            if ((bigDecimal.intValue()-value)<0){
                return SimpleResult.build(230,"用户余额不足");
            }

            Cars cars=this.carsServce.findCarsById(number_plate_id);

            if (cars==null||cars.getUid()!=user.getId()){
                return  SimpleResult.build(230,"车牌号不存在");
            }
            Reservations reservations=this.reservationsService.findDontByUid(user.getId());

            if (reservations!=null){
                return SimpleResult.build(500,"不可重复预约，如需要预约请先取消当前预约");
            }
            Parkings parkings = this.parkingsService.findParkingsById(id);

            if (parkings == null) {
                return SimpleResult.build(230, "未找到停车场");
            }
            int remain=parkings.getRemainder_parking_spaces();
            if (remain<=0){
                return SimpleResult.build(230,"没有空闲车位");
            }

            reservations=new Reservations();

            reservations.setUid(user.getId());//用户id

            reservations.setPid(id);//停车场id

            reservations.setAppointment(new Date().getTime()/1000);//设置预约时间,精确到秒

            reservations.setStatus(0);//设置当前状态

            reservations.setPrice(parkings.getPrice());//收费标准是reservation的收费标准

            reservations.setNumber_plate_id(number_plate_id);//设置需要停车的车牌号


            //由于有的停车场没有停车位的内容，这时候只需要计数就可以
            List<ParkingSpaces> list=this.parkingSpacesService.findByPidAndStatus(id, PayProperties.PARKING_STATUS_NULL);
            ParkingSpaces temp=null;
            if (list!=null&&list.size()>0){
                temp=list.get(0);
                this.parkingSpacesService.updateStatusById(temp.getId(),PayProperties.PARKING_STATUS_YUYUE);
                reservations.setRid(temp.getId());
            }else{
                reservations.setRid(PayProperties.PARKING_SPACE_NONE);
            }
            parkings.setRemainder_parking_spaces(parkings.getRemainder_parking_spaces()-1);

            this.parkingsService.updateCountById(id,parkings.getRemainder_parking_spaces());

            this.reservationsService.addReservations(reservations);

            ReservationsResult result= new ReservationsResult(reservations);
            result.setCar(cars);
            result.setParking(parkings);
            result.setParking_spaces(temp);
            result.setTime(new Date().getTime()/1000);//设置时间，精确到秒
            return  result;
        }catch (Exception e){
            logger.error(e.getMessage());
            return  SimpleResult.build(500,"服务器内部错误");
        }
    }

    /**
     * 取消预约的车位
     *  1. 将预约信息更新
     *  2. 将停车位信息更新
     *  3. 将停车场的剩余车位数更新
     * @param id  需要取消的车位id
     * @return
     */
    //TO-DO； 多个接口的问题
    @PostMapping("/api/app/cancel_reservation/{id}")
    public Result cancelReservation(HttpServletRequest request, @PathVariable Integer id, String reason){

        Reservations reservations=this.reservationsService.getReservationsById(id);

        try {
            if (reservations == null || reservations.getStatus() != 0) {
                return SimpleResult.build(230, "当前预约状态不可取消");
            }
            String mobile=TokenUtils.getMobileByRequest(request);
            Users users=this.authService.findUsersByPhoneNumber(mobile);
            if (users.getId()!=reservations.getUid()){
                return SimpleResult.build(230,"当前预约状态不可取消");
            }


            ReservationsResult reservationsResult = new ReservationsResult(reservations);
            this.reservationsService.setStatusById(id, 3);//设置成取消预约的状态
            this.reservationsService.setReasonById(id,reason);
            ParkingSpaces parkingSpaces=null;
            if (!reservationsResult.getRid().equals(PayProperties.PARKING_SPACE_NONE)) {
                parkingSpaces = this.parkingSpacesService.findById(reservations.getRid());

                this.parkingSpacesService.updateStatusById(parkingSpaces.getId(), 0);//将相关车位取消预约
            }
            Parkings parkings = this.parkingsService.findParkingsById(reservations.getPid());
            reservationsResult.setParking_spaces(parkingSpaces);

            parkings.setRemainder_parking_spaces(parkings.getRemainder_parking_spaces()+1);

            this.parkingsService.updateCountById(parkings.getId(),parkings.getRemainder_parking_spaces());
            reservationsResult.setParking(parkings);
            reservationsResult.setUser(users);
            return reservationsResult;
        }catch (Exception e){
            logger.error(e.getMessage());
            return SimpleResult.build(500,"服务器内部错误");
        }
    }
    /**
     * 获取预约信息://获取预约最后一条信息
     */
    @GetMapping("/api/app/reserve")
    public Result getLastMetting(HttpServletRequest request){
        return getLastMetting(request,null);
    }

    /**
     * 根据id获取预约信息
     * @param request
     * @param id
     * @return
     */
    @GetMapping(value = "/api/app/reserve/{id}")
    public Result getLastMetting(HttpServletRequest request, @PathVariable("id")Integer id){
        try {
            String mobile = TokenUtils.getMobileByRequest(request);

            Users user = this.authService.findUsersByPhoneNumber(mobile);

            Reservations reservations=null;
            if (id == null) {
                reservations=this.reservationsService.findLastMettingByUid(user.getId());
            }else{
                reservations=this.reservationsService.getReservationsById(id);
            }
            if(reservations==null){
                return null;
            }
            ReservationsResult reservationsResult=null;

            if(reservations.getUid()!=user.getId()){
                return null;
            }

            if (reservations!=null){
                Parkings parkings=this.parkingsService.findParkingsById(reservations.getPid());

                ParkingSpaces parkingSpaces=this.parkingSpacesService.findById(reservations.getRid());

                reservationsResult=new ReservationsResult(reservations);

                reservationsResult.setUser(user);

                reservationsResult.setParking(parkings);

                reservationsResult.setParking_spaces(parkingSpaces);

                reservationsResult.setTime(new Date().getTime()/1000);
            }
            return reservationsResult;
        }catch (Exception e){
         logger.error(e.getMessage());
            return SimpleResult.build(500,"服务器内部错误");
        }
    }

    /**
     * 用户预约信息分页显示
     * @param request  请求用户信息
     * @param page  分页页码
     * @return
     */

    @GetMapping("/api/app/reserves")
    public Result getReserves(HttpServletRequest request, Integer page){

        String reserversPath="http://"+request.getServerName() +":"
                +request.getServerPort()
                +request.getRequestURI();

        String mobile=TokenUtils.getMobileByRequest(request);
        if (page==null||page<=0)page=1;
        try {

           Users user= this.authService.findUsersByPhoneNumber(mobile);

           Integer count=this.reservationsService.getCountByUid(user.getId());//通过id获取记录个数

           Integer start=(this.perValue)*(page-1);//获取开始页

           List<Reservations> list=this.reservationsService
                                    .findReservationsByPageAndUid(start,perValue,user.getId());//获取预约记录  参数分别为开始页页号，每一页有多少行数据，通过什么查询

           List<ReservationsResult> realList=new LinkedList<>();
           if (list!=null){
               for (int i=0;i<list.size();i++){
                   Reservations temp=list.get(i);

                   Parkings parking=this.parkingsService.findParkingsById(temp.getPid());

                   ParkingSpaces parkingSpace=this.parkingSpacesService.findById(temp.getRid());



                   Cars car=this.carsServce.findCarsById(temp.getNumber_plate_id());

                    ReservationsResult reservationsResult=new ReservationsResult(temp);

                    reservationsResult.setParking(parking);

                    reservationsResult.setParking_spaces(parkingSpace);

                    reservationsResult.setUser(user);

                    reservationsResult.setCar(car);
                    realList.add(reservationsResult);
               }
           }


           PageResult pageResult=PageResult.build(perValue,page,reserversPath,null,realList,count);


           return pageResult;
        }catch (Exception e){
            logger.error(e.getMessage());

            return SimpleResult.build(500,"服务器内部错误");
        }
    }

    /**
     * 向白名单中添加车辆
     *    key为停车场 “id+”
     *   同时在缓存中设置添加相应车牌号id以及车牌号
     * @param pid 需要添加的停车场ID
     * @param carID  车牌号
     * @return
     */
    @PostMapping("/api/app/whitelist")
    public Result  addWhite(String pid,String carID){
        if (StringUtils.isEmpty(pid)||StringUtils.isEmpty(carID)){
            return SimpleResult.build(230,"数据不完整");
        }
        Parkings parkings=null;
        Cars cars=null;
        try {
            parkings=this.parkingsService.findParkingsById(Integer.valueOf(pid));
            cars=this.carsServce.findCarsByCarID(carID);
            if (parkings==null){
                return SimpleResult.build(230,"停车场信息不存在");
            }
            if (cars==null){
                return SimpleResult.build(230,"车牌号未与用户绑定");
            }
            /*
            * 添加车辆
            * */
            White white=new White();
            white.setLicense(carID);
            white.setCid(cars.getId());
            white.setPid(Integer.valueOf(pid));

            this.whiteService.addWhite(white);

            JedisUtils.srem(pid+"-",carID);

            JedisUtils.saddTimeOut(pid+"+",carID,timeOut);
            return SimpleResult.build(200,"添加成功");
        } catch (Exception e) {
            return SimpleResult.build(500,"服务器内部错误");
        }

    }

    /**
     * 白名单车辆信息删除
     *   在数据库中删除对应的白名单信息
     *    并在redis中缓存，一天
     * @param pid  停车场id
     * @return
     */
    @PostMapping("/api/app/whitelist/remove")
    public Result deleteWhite(String pid,String carID){
        if (StringUtils.isEmpty(pid)||StringUtils.isEmpty(carID)){
            return SimpleResult.build(230,"数据不完整");
        }

        Cars cars=this.carsServce.findCarsByCarID(carID);
        if (cars==null){
            return SimpleResult.build(230,"车牌号未绑定用户");
        }
        White white=this.whiteService.findByPidAndCarID(Integer.valueOf(pid),cars.getId());
        this.whiteService.delete(Integer.valueOf(pid),cars.getId());


        JedisUtils.srem(pid+"+",carID);
        JedisUtils.saddTimeOut(pid+"-",carID,timeOut);
        return SimpleResult.build(200,"删除成功");
    }
}
