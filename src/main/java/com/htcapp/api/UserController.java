package com.htcapp.api;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.htcapp.domain.Cars;
import com.htcapp.domain.FundsRecords;
import com.htcapp.properties.PayProperties;
import com.htcapp.result.*;
import com.htcapp.domain.Users;
import com.htcapp.mapper.CarsMapper;
import com.htcapp.service.AuthService;
import com.htcapp.service.FundsRecordsService;
import com.htcapp.service.UserService;
import com.htcapp.utils.StringUtils;
import com.htcapp.utils.TokenUtils;
import com.htcapp.utils.WXPayUtils;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户模块
 * Created by Jone on 2018-05-30.
 */

@RestController
public class UserController {
    private static final org.slf4j.Logger logger= LoggerFactory.getLogger(UserController.class);

    @Value("${ALI_APP_ID}")
    private String ALI_APP_ID;

    @Value("${ALI_PUBLIC_KEY}")
    private String ALI_PUBLIC_KEY;

    @Value("${ALI_PRIVATE_KEY}")
    private String ALI_PRIVATE_KEY;

    @Value("${NOTIFY_URL}")
    private String NOTIFY_URL;

    @Value("${filePath}")
    private String filePath;

    @Value("${fileUrl}")
    private String fileUrl;

    @Value("${rootFilePath}")
    private String picRootPath;
    @Autowired
    private CarsMapper carsMapper;

    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;

    @Autowired
    private FundsRecordsService fundsRecordsService;

    @Autowired
    private WXPayUtils wxPayUtils;
    /**
     * 获取用户信息
     * @param  request 获取登陆验证信息
     * @return 用户信息
     * @throws Exception 获取用户信息异常
     */
    @GetMapping("/api/app/user")
    public Result getUserInfo(HttpServletRequest request) {
        try {
            String mobile = TokenUtils.getMobileByRequest(request);
            Users user = null;
            user = this.userService.findUserInfo(mobile);
            int id = user.getId();
            List<Cars> cars = null;
            cars = carsMapper.findCarsByUid(id);

            user.setCars(cars);
            return user;
        }catch (Exception e){
            logger.error(e.getMessage());
            return SimpleResult.build(500,"服务器内部错误");
        }
    }

    /**
     * 修改用户资料
     * @param nickname 昵称
     * @param gender 性别
     * @param age 年龄
     * @param avatar 头像
     * @return 修改后的用户信息
     * @throws Exception 修改用户信息异常
     */
    @PostMapping("/api/app/user")
    public Result editUser(HttpServletRequest request, String nickname, Integer gender, Integer age, String avatar) throws Exception {

        String mobile = TokenUtils.getMobileByRequest(request);
        Users user=null;
        user = this.userService.findUserInfo(mobile);
        String pavatar = user.getAvatar();
        removeAvatarIfNotSave(pavatar);
        try{
            if (!StringUtils.isEmpty(nickname)){
                    user.setNickname(nickname);
            }
            if (gender!=null){
                user.setGender(gender);
            }
            if (age!=null){
                user.setAge(age);
            }
            if(!StringUtils.isEmpty(avatar)){
                user.setAvatar(avatar);
            }
            this.userService.updateByMobile(user.getMobile(),user.getNickname(),
                    user.getGender(),user.getAge(),user.getAvatar());
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return user;
    }

    /**
     * 上传头像
     * @param avatar 头像
     * @return 头像路径、头像访问url
     */
    @PostMapping("/api/app/user/avatar")
    public Result uploadImage(MultipartFile avatar) throws RuntimeException {
        if (avatar == null || avatar.isEmpty()) {
            return SimpleResult.build(230,"未发送文件对象");
        }
        if (avatar.getSize()>5*1048576){
            return SimpleResult.build(400,"图片太大");
        }

        // 获取文件名
        String fileName = avatar.getOriginalFilename();
        logger.info("上传的文件名为：" + fileName);
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        logger.info("上传的后缀名为：" + suffixName);
        if (!(suffixName.trim().equalsIgnoreCase(".jpg")
                ||suffixName.trim().equalsIgnoreCase(".png")
                ||suffixName.trim().equalsIgnoreCase(".gif")
                ||suffixName.trim().equalsIgnoreCase(".jpeg")
                ||suffixName.trim().equalsIgnoreCase(".bmp"))){
            return SimpleResult.build(400,"图片格式不正确");
        }
        // 文件上传后的路径
        //String filePath = "avatar/";
        // 解决中文问题，liunx下中文路径，图片显示问题
        // fileName = UUID.randomUUID() + suffixName;
        String realRootPath=null;
        try {
           realRootPath= ResourceUtils.getFile(picRootPath).getPath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        File dest = new File(realRootPath+File.separator + fileName);


        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            Boolean b = dest.getParentFile().mkdirs();
            if (!b){
                logger.info("文件夹创建失败");

                return PicAddResult.build(null,null,"文件创建失败");

            }
        }
        try {
            avatar.transferTo(dest);
            logger.info("上传成功后的文件路径为：" + filePath+"/" + fileName);
            return PicAddResult.build(filePath+"/"+fileName,fileUrl+"/"+filePath+"/"+fileName,null);
        } catch (IllegalStateException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return SimpleResult.build(500,"文件上传失败");
    }

    /**
     * 删除未保存资料的已上传头像
     * @param avatar 头像路径
     * @return 成功：true  失败：false
     */
    @PostMapping("/api/app/user/avatar_delete")
    public Result removeAvatarIfNotSave(String avatar){
        PicAddResult rt = new PicAddResult();
        rt.setSuccess(false);
        if (avatar == null || avatar.trim().equals("")){
            return rt;
        }

        File file =null;
        String realRootPath=null;
        try {
            realRootPath=ResourceUtils.getFile(picRootPath).getPath();
            realRootPath=realRootPath.substring(0,realRootPath.lastIndexOf("\\"));
            file=new File(realRootPath+File.separator+avatar);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            try {
                boolean b = file.delete();
                rt.setSuccess(b);
            }catch (Exception e){
                logger.error(e.getMessage());
            }
        } else {
            logger.info("文件不存在");
        }
        return rt;
    }

    @PostMapping("/api/app/recharge")
    public Result recharge(HttpServletRequest request,String amount,String pay_method){

        String mobile=TokenUtils.getMobileByRequest(request);
        Users users=null;
        try {
            users=this.authService.findUsersByPhoneNumber(mobile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        BigDecimal bigDecimal=null;
        try {
            bigDecimal = new BigDecimal(amount);
            if (bigDecimal.compareTo(new BigDecimal("0"))<0){
                throw  new Exception("用户充值金额不正确");
            }
        }catch (Exception e){
            return SimpleResult.build(230,"充值金额不正确");
        }

        Integer payMehod=null;
        try{
           payMehod =Integer.valueOf(pay_method);
            PayResult payResult=null;
            if(payMehod==3){//微信支付
                FundsRecords fundsRecords=FundsRecords.buildSimpeFundsRecords(users.getId(),bigDecimal,PayProperties.TYPE_CHONGZHI, PayProperties.STATUS_YUYUE_NO,PayProperties.METHOD_W_APP);

                fundsRecords.setSubject("用户充值");
                fundsRecords.setResult_operation(0);

                Map<String,Object> map=wxPayUtils.makeOrder(request,fundsRecords.getTrade_no(),bigDecimal);


                payResult=PayResult.build(200,map,fundsRecords);

                this.fundsRecordsService.add(fundsRecords);

            }else if (payMehod==6){//支付宝支付

                AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", ALI_APP_ID, ALI_PRIVATE_KEY, "json", "UTF-8", ALI_PUBLIC_KEY, "RSA2");
                //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
                AlipayTradeAppPayRequest req = new AlipayTradeAppPayRequest();
                //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
                AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
                model.setSubject("用户充值");
                model.setTimeoutExpress("30m");
                model.setTotalAmount(bigDecimal.toString());
                model.setProductCode("QUICK_MSECURITY_PAY");
                req.setBizModel(model);
                req.setNotifyUrl(NOTIFY_URL);
                try {

                    AlipayTradeAppPayResponse response = alipayClient.sdkExecute(req);
                    System.out.println(response.getBody());//就是orderString 可以直接给客户端请求，无需再做处理。
                    if (response!=null){
                        String data = response.getBody();
                        FundsRecords order = FundsRecords.buildSimpeFundsRecords(users.getId(),bigDecimal,PayProperties.TYPE_CHONGZHI, PayProperties.STATUS_YUYUE_NO,PayProperties.METHOD_Z_APP);
                        order.setSubject("用户充值");
                        order.setResult_operation(0);
                        this.fundsRecordsService.add(order);
                        //return RechargeResult.build(data,order,200);
                        return PayResult.build(200,data,order);
                    }

                } catch (AlipayApiException e) {
                    e.printStackTrace();
                }


            }else{
                throw new Exception();
            }
            BigDecimal end=users.getBalance();
            end=end.add(bigDecimal);
            this.userService.updateMoneyByUid(users.getId(),end);
            return payResult;
        }catch (Exception e){
            return SimpleResult.build(230,"支付方式不正确");
        }
    }
    @RequestMapping("/api/app/wx_response")
    public String wxResponse(){
        return null;
    }

    @PostMapping("/api/notify")
    public Result Ali_Notify(HttpServletRequest request) throws AlipayApiException {
        //获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map requestParams = request.getParameterMap();
        if (requestParams.size()==0){
            return SimpleResult.build("Convert To Array Error! Invalid Xml!",3,"500");
        }
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        //切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
        //boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
        boolean flag = AlipaySignature.rsaCheckV1(params, ALI_PUBLIC_KEY, "utf-8","RSA2");
        if (flag){
            String trade_no = params.get("out_trade_no");
            int status;
            String raw_data = params.toString();;
            String api_trade_no = params.get("trade_no");
            BigDecimal amount = new BigDecimal(params.get("total_amount"));
            if (params.get("trade_status").trim().equalsIgnoreCase("TRADE_SUCCESS")){
                status = 1;
            }
            else if (params.get("trade_status").trim().equalsIgnoreCase("TRADE_CLOSED")){
                status = 2;
            }
            else if (params.get("trade_status").trim().equalsIgnoreCase("TRADE_FINISHED")){
                status = 1;
            }
            else {
                status = 0;
            }
            Date updated_at = new Date();
            this.fundsRecordsService.update(status,api_trade_no,raw_data,updated_at,trade_no);
            int uid = this.fundsRecordsService.findUid(trade_no);
            userService.recharge(uid,amount);
            System.out.println(status+raw_data+api_trade_no);
            return SimpleResult.build("success");
        }
        return SimpleResult.build("failed");
    }

}