package com.htcapp.service;

import com.htcapp.domain.*;
import com.htcapp.mapper.BarrierGatesMapper;
import com.htcapp.properties.ParkingSpacesProperties;
import com.htcapp.properties.PayProperties;
import com.htcapp.properties.ReservationsProperties;
import com.htcapp.tcpserver.domain.*;
import com.htcapp.utils.JedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * 与道闸相关的服务
 */
@Service
public class BarrierGatesService {

    private static final Logger logger= LoggerFactory.getLogger(BarrierGates.class);

    @Autowired
    private ReservationsService reservationsService;

    @Value("${waitTime}")
    private Integer waitTime;

    @Autowired
    private ParkingsService parkingsService;

    @Autowired
	private BarrierGatesMapper barrierGatesMapper;

    @Autowired
    private CarsServce carsServce;

    @Autowired
    private PayService payService;//支付

    @Autowired
    private FundsRecordsService fundsRecordsService;

    @Autowired
    private ParkingSpacesService parkingSpacesService;

    @Autowired
    private WhiteService whiteService;
    /**
     * 进入做的操作：
     *  1.查询车牌号是否存在
     *      查询用户是否预约：
     *          已经预约：
     *          更新停车空间数据库表，修改停车位状态为进入状态
     *          同时在redis中存储订单信息
     *      未预约：查询停车场车位是否充足
     *               如果充足，修改停车位状态为进入状态
     *               同时在redis中存储订单信息
     *  2.不存在：
     *      查询车位是否充足
     *          充足：插入数据库表并绑定0号用户，推送数据。并修改停车位状态
     *          同时在redis中存储订单信息
     *          为进入状态
     *          不充足：向前端推送数据
     *
     *  返回数据同时开一个线程进行计时。计时时间到后如果相应车位的状态还是未确认状态，
     *  则将相应数据置位失败
     * */
    @Transactional
    public TcpResult goIn(ParkInfo parkInfo) {
        try {
            String carID = parkInfo.getLicense();//获取车牌号

            String lockID = parkInfo.getBname();//获取锁编号



            Cars cars = this.carsServce.findCarsByCarID(carID);//获取车对应的用户

            if(cars!=null){
                Reservations reservations= this.reservationsService.findCanNotPark(cars.getUid());
                if (reservations!=null){
                    return TcpResult.build(2110,"已经停车");
                }
            }else {
                //应该先将cars数据插入cars数据库表，然后获取id
                cars = new Cars(null, 0, carID, new Date(), new Date());
            }


            BarrierGates barrierGates = barrierGatesMapper.findBarrierGatesByLock(lockID);
            if(barrierGates==null) return TcpResult.build(2120,"车闸信息有误");
            Parkings parkings = null;
            parkings = this.parkingsService.findParkingsById(barrierGates.getPid());

            //可用的所有停车场信息


            if (parkings.getRemainder_parking_spaces()<=0){
                return TcpResult.build(2130,"停车场空间不足");
            }
            ParkingSpaces randomParkingSpaces=null;
            List<ParkingSpaces> parkingSpaces = parkingSpacesService.findByPidAndStatus(
                    parkings.getId(),
                    ParkingSpacesProperties.STATUS_EMPTY
            );
			if (parkingSpaces!=null&&parkingSpaces.size()>0) {
                //获取随机车位
                Random r = new Random();
                int randomPosition = r.nextInt(parkingSpaces.size());
                randomParkingSpaces = parkingSpaces.get(randomPosition);
            }
            Reservations reservations=null;
			//调用根据车id，停车场id获取预约信息的方法，然后根据预约信息是否为空
			if (cars!=null){
				//通过车牌号和停车场id获取预约信息
				List<Reservations> reservationsList =
                        this.reservationsService.findReservationsByCarIDAndParkingIDAndStatus(cars.getId(),parkings.getId(),PayProperties.STATUS_YUYUE_NO);
				if (reservationsList!=null&&reservationsList.size()>0){
					reservations = reservationsList.get(0);//表示已经预约
					//需要修改的字段
					//reservations:parking_time	status=2
					//parking remainder_parking_spaces-1
					Long parking_time = new Date().getTime()/1000;
					reservationsService.updateParkingsInfoById(
					        parking_time,
                            ReservationsProperties.STATUS_ENTER,
                            reservations.getId());
//				parkingsService.updateCountById(parkings.getId(),parkings.getRemainder_parking_spaces()-1);
					parkingSpacesService.updateStatusById(reservations.getRid(),PayProperties.PARKING_STATUS_WAIT);
					//向前端推送消息
//					JiGuangPush.jiGuangPush(reservations.getUid(), JiGuangPushData.build(reservations.getUid(),1));
				}else{
					//表示没有预约
//					Cars cars_add = new Cars();
//					cars_add.setNumber_plate(carID);
//					cars_add.setUid(0);
//					cars_add.setCreated_at(new Date());
//					carsServce.addCars(cars_add);
						//添加预约信息
						//需要添加的字段
						//uid,pid,rid,number_plate_id,appointment,status


                    reservations =Reservations.buildSimple(
                            cars.getUid(),
                            parkings.getId(),
                            randomParkingSpaces==null?PayProperties.PARKING_SPACE_NONE:
                            randomParkingSpaces.getId(),
                            cars.getId(),
                            new Date().getTime()/1000,
                            ReservationsProperties.STATUS_ENTER_NO
                            );
						reservationsService.addReservations(reservations);
						Long  parking_time = new Date().getTime()/1000;
						reservationsService.updateParkingsInfoById(parking_time,ReservationsProperties.STATUS_ENTER_NO,reservations.getId());
						if (randomParkingSpaces!=null)
						parkingSpacesService.updateStatusById(randomParkingSpaces.getId(),PayProperties.PARKING_STATUS_WAIT);
                    //	parkingsService.updateCountById(parkings.getId(),parkings.getRemainder_parking_spaces()-1);
//						JiGuangPush.jiGuangPush(reservations.getUid(), JiGuangPushData.build(reservations.getUid(),1));

                }

			}else{

				carsServce.addCars(cars);

				reservations = Reservations.buildSimple(0,parkings.getId(),
                        randomParkingSpaces==null?PayProperties.PARKING_SPACE_NONE:randomParkingSpaces.getId(),
                        cars.getId(),new Date().getTime()/1000,ReservationsProperties.STATUS_ENTER_NO);

                    /*
                    * 已经预约，但是未确认更新
                    * */
				    /*if (randomParkingSpaces!=null) {
                        parkingSpacesService.updateStatusById(randomParkingSpaces.getId(), PayProperties.STATUS_YUYUE_HAD);
                    }*/
                    reservationsService.addReservations(reservations);
                    reservationsService.updateParkingsInfoById(new Date().getTime()/1000,ReservationsProperties.STATUS_ENTER_NO,reservations.getId());

                    /*停车数未减少*/
                    //parkingsService.updateCountById(parkings.getId(),parkings.getRemainder_parking_spaces()-1);
//					JiGuangPush.jiGuangPush(reservations.getUid(), JiGuangPushData.build(reservations.getUid(),1));

			}
            Reservations finalReservations = reservations;
            Parkings finalParkings = parkings;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(waitTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Reservations reservationsTemp= reservationsService.findById(finalReservations.getId());

                   if(reservationsTemp.getStatus()==ReservationsProperties.STATUS_ENTER_NO||
                            reservationsTemp.getStatus()==ReservationsProperties.STATUS_ENTER){
                        if (!(reservationsTemp.getRid()==PayProperties.PARKING_SPACE_NONE)){
                            parkingSpacesService.updateStatusById(
                                    reservationsTemp.getRid(),
                                    ParkingSpacesProperties.STATUS_EMPTY
                                    );
                        }
                       reservationsService.updateStatusAndLeaveByid(
                               ReservationsProperties.STATUS_FAIL,
                               new Date().getTime()/1000,
                               reservationsTemp.getId()
                       );
                    }

                    JedisUtils.del(lockID);

                    if (reservationsTemp.getStatus()==ReservationsProperties.STATUS_ENTER){
                       parkingsService.updateCountById(reservationsTemp.getPid(), finalParkings.getRemainder_parking_spaces()+1);
                    }
                }
            }).start();
            JedisUtils.putValTimeout(lockID,reservations.getId()+"",(long)300);
            return TcpResult.build(200,"OK",OpenInfo.open(lockID,carID,parkings.getRemainder_parking_spaces()));
        } catch (Exception e) {
			logger.error(e.getMessage());
            return TcpResult.build(500,"服务器内部错误");
        }
    }

    /**
     * 出来做的操作：
     *    1. 计算时间
     *    2.  将相应信息存入缓存中，缓存格式  keys:道闸id- value: 订单号id？停车时间（分钟为单位）
     * @param parkInfo
     * @return
     */
    @Transactional
    public TcpResult goOut(ParkInfo parkInfo) {

        try {
            int flag=0;
            String carID = parkInfo.getLicense();//获取车牌号

            String lockID = parkInfo.getBname();//获取锁ID


            Cars cars = this.carsServce.findCarsByCarID(carID);//获取车对应的用户

            if (cars==null){
                TcpResult.build(2210,"车牌信息不存在");
            }


            BarrierGates barrierGates = barrierGatesMapper.findBarrierGatesByLock(lockID);
            Parkings parkings = null;
            parkings = this.parkingsService.findParkingsById(barrierGates.getPid());

            List<Reservations> reservationsList = this.reservationsService.findReservationsByCarIDAndParkingIDAndStatus(cars.getId(), parkings.getId(), PayProperties.STATUS_YUYUE_HAD);
            Reservations reservations=null;
            if (reservationsList!=null&&reservationsList.size()>0){
                reservations=reservationsList.get(0);
            }else {
                return TcpResult.build(2220,"未找到停车记录");
            }
            Long leave = new Date().getTime() / 1000;

            long result = leave - reservations.getParking_time();//获取总停车时间
            //假设只有
            BigDecimal end = getEndValue(result, parkings.getPrice());//获得最后价格

           // FundsRecords fundsRecords = null;
/*            if (reservations.getUid() == 0) {//如果uid小于0的话，推送需要支付信息
                fundsRecords = FundsRecords.buildSimpeFundsRecords(reservations.getUid(), end, PayProperties.TYPE_TINGCHE, PayProperties.STATUS_NO,//未支付
                        PayProperties.METHOD_USER//现场交易
                );

                this.reservationsService.updateStatusAndLeaveByid(PayProperties.STATUS_YUYUE_FINISH, leave, reservations.getId());
                this.reservationsService.updateDueChargesById(end, reservations.getId());
                flag=0;
            } else {
                this.payService.payByUser(reservations.getUid(), end);//进行支付
                //生成订单，向前端推送
                fundsRecords = FundsRecords.buildSimpeFundsRecords(reservations.getUid(), end, PayProperties.TYPE_TINGCHE, PayProperties.STATUS_SUCCESS, PayProperties.METHOD_YUER);
                this.reservationsService.updateStatusAndLeaveByid(PayProperties.STATUS_YUYUE_SHOUFEI, leave, reservations.getId());
                this.reservationsService.updateChargesById(end, reservations.getId());
                flag=1;
            }
            if (!reservations.getRid().equals(PayProperties.PARKING_SPACE_NONE)) {
                this.parkingSpacesService.updateStatusById(reservations.getRid(), PayProperties.PARKING_STATUS_NULL);
            }
            this.fundsRecordsService.add(fundsRecords);
            this.parkingsService.updateCountById(parkings.getId(), parkings.getRemainder_parking_spaces() + 1);
   */         //JiGuangPush.jiGuangPush(reservations.getUid(), JiGuangPushData.build(reservations.getUid(),2,fundsRecords.getId()));

            result=((result%60)==0)?(result/60):(result/60+1);
            JedisUtils.putValTimeout(lockID+"-",reservations.getId()+"?"+result, (long) 300);
            //白名单车辆不扣钱
            White white=this.whiteService.findByPidAndCarID(parkings.getId(),cars.getId());
            return TcpResult.build(200,"OK",OpenInfo.open(lockID,
                    carID,
                    parkings.getRemainder_parking_spaces(),
                    white==null?(int)result:0,
                    end.toString()
                    ));
        }catch (Exception e){
            logger.error(e.getMessage());
            return TcpResult.build(500,"服务器内部错误");
        }
    }

    /**
     * 用户计费
     * 计费规则：五分钟之内不收费
     *           五分钟至半个小时按照半个小时收费
     *           半个小时之后按照一个小时收费
     * @param result
     * @param per 每分钟价格
     * @return
     */
    private BigDecimal getEndValue(long result,BigDecimal per) {
        result/=60;
        double count=result/60;
        long   resultTime=result%60;

        if (resultTime>30){
            count+=1;

        }else if(resultTime>=5){
            count+=0.5;
        }
        BigDecimal allSum=new BigDecimal(count);

        allSum=allSum.multiply(per);
        allSum.setScale(2);
        return allSum;
    }

    public List<White> findWhiteListByLockInfo(LockInfo lockInfo) {
        String bname=lockInfo.getBname();
        BarrierGates barrierGates=this.barrierGatesMapper.findBarrierGatesByLock(bname);

        if (barrierGates==null){
            return null;
        }

        List<White>list=this.whiteService.findByPid(barrierGates.getPid());
        White temp=null;
        Cars cars=null;
        if (list!=null&& list.size()>0) {
            for (int i = 0; i < list.size(); i++) {
                temp = list.get(i);
                cars = this.carsServce.findCarsById(temp.getCid());
                temp.setLicense(cars.getNumber_plate());
            }
        }
        return list;
    }
    /*
    * 确认车辆进入
    *   从缓存中读取数据并删除，相应的车位信息进行更新
    * */
    public TcpResult confirmGoIn(AnaResult anaResult) {
        try {
            String bname = (String) anaResult.getData();
            //获取道闸信息
            BarrierGates barrierGates = this.barrierGatesMapper.findBarrierGatesByLock(bname);
            //获取停车场信息
            Parkings parkings = this.parkingsService.findParkingsById(barrierGates.getPid());

            String idValue=JedisUtils.getVal(bname);

            JedisUtils.del(bname);

            Integer id=Integer.valueOf(idValue);

            Reservations reservations=this.reservationsService.findById(id);


            if (reservations.getStatus()==ReservationsProperties.STATUS_ENTER_NO){
                reservations.setStatus(ReservationsProperties.STATUS_PAR);
                this.reservationsService.updateParkingsInfoById(
                        reservations.getParking_time(),
                        reservations.getStatus(),
                        reservations.getId());
                this.parkingsService.updateCountById(parkings.getId(),parkings.getRemainder_parking_spaces()-1);
            }else{
                reservations.setStatus(ReservationsProperties.STATUS_PAR);
                this.reservationsService.updateParkingsInfoById(
                        reservations.getParking_time(),
                        reservations.getStatus(),
                        reservations.getId()
                );
            }
            Integer rid=reservations.getRid();
            if (rid!=PayProperties.PARKING_SPACE_NONE){
                this.parkingSpacesService.updateStatusById(rid,PayProperties.PARKING_STATUS_HAD);
            }

            Confirm confirm=Confirm.build(bname,parkings.getRemainder_parking_spaces()-1);
            return TcpResult.build(200,"OK",confirm);
        }catch (Exception e){
            e.printStackTrace();
            return TcpResult.build(500,"服务器内部错误");
        }
    }
    /*
    * 确认车辆出来
    *    读出缓存中的信息并删除，并更新车位信息
    * */
    @Transactional
    public TcpResult confirmGoOut(AnaResult anaResult) {
        try {
            String bname = (String) anaResult.getData();
            BarrierGates barrierGates = this.barrierGatesMapper.findBarrierGatesByLock(bname);


            String endValue = JedisUtils.getVal(bname + "-");


            JedisUtils.del(bname + "-");

            String[] result = endValue.split("\\?");

            Integer rid = Integer.valueOf(result[0]);

            Long times = Long.valueOf(result[1]);

            Reservations reservations = this.reservationsService.findById(rid);

            Parkings parkings = null;

            parkings = this.parkingsService.findParkingsById(barrierGates.getPid());

            FundsRecords fundsRecords = null;

            Long leave = new Date().getTime() / 1000;


            BigDecimal end = getEndValue(times, parkings.getPrice());
            if (reservations.getUid() == 0) {//如果uid小于0的话，推送需要支付信息
                fundsRecords = FundsRecords.buildSimpeFundsRecords(reservations.getUid(), end, PayProperties.TYPE_TINGCHE, PayProperties.STATUS_NO,//未支付
                        PayProperties.METHOD_USER//现场交易
                );

                this.reservationsService.updateStatusAndLeaveByid(PayProperties.STATUS_YUYUE_FINISH, leave, reservations.getId());
                this.reservationsService.updateDueChargesById(end, reservations.getId());
            } else {
                this.payService.payByUser(reservations.getUid(), end);//进行支付
                //生成订单，向前端推送
                fundsRecords = FundsRecords.buildSimpeFundsRecords(reservations.getUid(), end, PayProperties.TYPE_TINGCHE, PayProperties.STATUS_SUCCESS, PayProperties.METHOD_YUER);
                this.reservationsService.updateStatusAndLeaveByid(PayProperties.STATUS_YUYUE_SHOUFEI, leave, reservations.getId());
                this.reservationsService.updateChargesById(end, reservations.getId());
                //JiGuangPush.jiGuangPush(reservations.getUid(), JiGuangPushData.build(reservations.getUid(),2,fundsRecords.getId()));
            }
            if (!reservations.getRid().equals(PayProperties.PARKING_SPACE_NONE)) {
                this.parkingSpacesService.updateStatusById(reservations.getRid(), PayProperties.PARKING_STATUS_NULL);
            }
            this.fundsRecordsService.add(fundsRecords);
            this.parkingsService.updateCountById(parkings.getId(), parkings.getRemainder_parking_spaces() + 1);
            //JiGuangPush.jiGuangPush(reservations.getUid(), JiGuangPushData.build(reservations.getUid(),2,fundsRecords.getId()));
            return TcpResult.build(200, "OK", Confirm.build(bname, parkings.getRemainder_parking_spaces() + 1));
        }catch (Exception e){
            logger.error(e.getMessage());
            return TcpResult.build(500,"服务器内部错误");
        }
    }

    /**
     * 获取24小时内添加修改的白名单
     * @param anaResult
     * @return
     */
    public List<White> findChangeWhiteInfo(AnaResult anaResult) {
        LockInfo lockInfo= (LockInfo) anaResult.getData();
        String bname=lockInfo.getBname();

        BarrierGates barrierGates=this.barrierGatesMapper.findBarrierGatesByLock(bname);

        Integer pid=barrierGates.getPid();

        Set<String>addSet=JedisUtils.smembers(pid+"+");

        Set<String>removeSet=JedisUtils.smembers(pid+"-");

        List<White> list=new LinkedList<>();
        Cars tempCar=null;
        White tempWhite=null;
        Integer flag=0;
        for (String addCars:addSet) {
            list.add(White.build(++flag,addCars,White.ADD));
        }
        flag=0;
        for (String removeCars:removeSet) {
            list.add(White.build(++flag,removeCars,White.REMOVE));
        }
        return list;
    }

    public Integer findEmptyCarCount(AnaResult anaResult) {
        String bname= (String) anaResult.getData();
        BarrierGates barrierGates=this.barrierGatesMapper.findBarrierGatesByLock(bname);
        Parkings parkings=null;
        try {
            parkings = this.parkingsService.findParkingsById(barrierGates.getPid());
            return  parkings.getRemainder_parking_spaces();
        }catch (Exception e){
            logger.error(e.getMessage());
            return 0;
        }
    }
}
