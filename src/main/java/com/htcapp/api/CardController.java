package com.htcapp.api;

import com.htcapp.domain.*;
import com.htcapp.result.*;
import com.htcapp.service.AuthService;
import com.htcapp.service.CardService;
import com.htcapp.service.FundsRecordsService;
import com.htcapp.utils.TokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;


/**
 * 银行卡模块
 * Created by Jone on 2018-05-30.
 */
@RestController
public class CardController {
    private static final Logger logger = LoggerFactory.getLogger(CardController.class);

    @Value("${baseValue}")
    private Integer value;

    @Value("${perValue}")
    private Integer perValue;

    @Autowired
    private CardService cardService;

    @Autowired
    private FundsRecordsService fundsRecordsService;

    @Autowired
    private AuthService authService;

    /**
     * 添加银行卡
     *  1.判断用户名，卡号，开户行是否合法
     *  2.执行添加操作
     *
     * @param name 银行卡用户名
     * @param card 银行卡号
     * @param bank 银行卡开户行
     * @return 添加银行卡信息
     * */
    @PostMapping("/api/app/userBankcard")
    public Result addUserBankCard(HttpServletRequest request, String name, String card, String bank){
            try {
                if (name ==null||name.trim().equals("")){
                    return SimpleResult.build(500,"The given data was invalid.",Errors.CARDNONE_USER_ERROR);
                }else if(card ==null||card.trim().equals("")){
                    return SimpleResult.build(500,"The given data was invalid.",Errors.CARDNONE_NUMBER_ERROR);
                }else {
                    String mobile = TokenUtils.getMobileByRequest(request);
                    Users user = this.authService.findUsersByPhoneNumber(mobile);
                    UserBankcards userbankcard = new UserBankcards(
                            null,user.getId(),user.getName(),
                            card,bank,new Date(),new Date()
                            );
                    //添加银行卡信息
                    //此数值是更改的行数
                     this.cardService.addUserBankCards(userbankcard);
                    //当添加成功时返回添加的银行卡信息
                    return userbankcard;
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
                return SimpleResult.build(500,"服务器内部错误");
            }
        }
    /**
     * 根据ID删除银行卡
     *  1.判断传入参数是否为空
     *  2.执行删除操作
     *
     * @param id
     * @return 删除成功
     * */
    @DeleteMapping("/api/app/userBankcard/{id}")
    public Result deleteUserBankCards(HttpServletRequest request, @PathVariable("id")Integer id){
            try {
                String mobile=TokenUtils.getMobileByRequest(request);

                Users users=this.authService.findUsersByPhoneNumber(mobile);


                int state = cardService.deleteUserBankCardsByIdAndUid(id,users.getId());
                if (state==0){
                    return SimpleResult.build(500,"No query results for model [App\\\\Entities\\\\UserBankcard]"+id);
                }else {
                    return CardResult.build("UserBankcard deleted.", true);
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
                return SimpleResult.build(500,"服务器内部错误");
            }
    }
    /**
     * 修改银行卡信息
     *  1.判断传入参数是否合法（可以接受三个都为空）
     *  2.执行修改操作
     * @param id   银行卡id
     * @param name 银行卡用户名
     * @param card 银行卡号
     * @param bank 银行卡开户行
     *
     * @return 修改的银行卡信息
     * */
    /**
     *   1.没有id
     *  "message": "405 Method Not Allowed",
     *   "status_code": 405
     *   2.传入id找不到
     *   "message": "No query results for model [App\\Entities\\UserBankcard] 255",
     *   "status_code": 500
     *   Patch传参数问题未解决，暂时将参数放入URL后面，可以接收到参数
    * */
/*    @PatchMapping("/api/app/userBankcard/{id}/{name}/{card}/{bank}")
    public SimpleResult updateUserBankCards(@PathVariable("name")String name,@PathVariable("card")String card,@PathVariable("bank")String bank,@PathVariable("id")Integer id){         try {
               if (id==null) {
                    Errors errors = new Errors();
                    errors.setBank(new String[]{"id不能为空"});
                    return SimpleResult.build(405, "405 Method Not Allowed");                }else {
                    UserBankcards userBankcards = new UserBankcards();
                    userBankcards.setName(name);
                    userBankcards.setCard(card);
                    userBankcards.setBank(bank);
                    userBankcards.setUid(20);
                    userBankcards.setId(id);
                    userBankcards.setCreated_at(new Date());
                    userBankcards.setUpdated_at(new Date());
                    Integer state = cardService.updateUserBankCards(userBankcards);
                    if (state==0){
                        return SimpleResult.build(500,"No query results for model [App\\Entities\\UserBankcard]"+id);
                    }else{
                        return SimpleResult.build(userBankcards);
                    }
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
               return SimpleResult.build(500,"服务器内部错误");
            }
    }*/

    @PostMapping("/api/app/userBankcard/{id}")
    public Result updateUserBankCards(HttpServletRequest request, UserBankCardsPo userBankCardsPo, @PathVariable("id")Integer id){
        try {

                String mobile=TokenUtils.getMobileByRequest(request);

                Users users=this.authService.findUsersByPhoneNumber(mobile);

                UserBankcards userBankcards = cardService.findUserBankCardsById(id);

                if (userBankcards==null||userBankcards.getUid()!=users.getId()){
                    return SimpleResult.build(230,"没有此银行卡信息");
                }

                String name = userBankCardsPo.getName();
                String card = userBankCardsPo.getCard();
                String bank = userBankCardsPo.getBank();
                if (name!=null)userBankcards.setName(name);
                if (card!=null)userBankcards.setCard(card);
                if (bank!=null)userBankcards.setBank(bank);
                userBankcards.setUpdated_at(new Date());
                Integer state = cardService.updateUserBankCards(userBankcards);
                if (state==0){
                    return SimpleResult.build(500,"No query results for model [App\\Entities\\UserBankcard]"+id);
                }else{
                    return userBankcards;
                }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return SimpleResult.build(500,"服务器内部错误");
        }
    }
    /**
     * 获取所有用户银行卡
     * 1.返回所有用户银行卡
     * @return 返回查询到的所有银行卡
     * */
    @GetMapping("/api/app/userBankcard")
    public UserBankcards[] findAllUserBankCards(HttpServletRequest request){
        try {
            //先获取uid
            String mobile = TokenUtils.getMobileByRequest(request);
            Users user = this.authService.findUsersByPhoneNumber(mobile);
            UserBankcards[] allUserBankCards = cardService.findAllUserBankCards(user.getId());
            return allUserBankCards;
        } catch (Exception e) {
            logger.error(e.getMessage());
//            return SimpleResult.build(500,"服务器内部错误");
            return null;
        }
    }
//    /**
//     * 根据ID返回银行卡信息
//     *   1.没有id
//     *  "message": "405 Method Not Allowed",
//     *  "status_code": 405
//     * */
//    @GetMapping("/card/find/{id}")
//    public AnaResult findUserBankCardsById(@PathVariable("id")Integer id) {
//        try {
//            if (id == null) {
//                Errors errors = new Errors();
//                errors.setBank(new String[]{"id不能为空"});
//                return AnaResult.build(405, "405 Method Not Allowed");
//            } else {
//                UserBankcards userBankCards = cardService.findUserBankCardsById(id);
//                return AnaResult.build(userBankCards);
//            }
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//            return AnaResult.build(500,"服务器内部错误");
//        }
//    }

//    @GetMapping("/api/app/funds_record")
//    public void addFundsRecords(){
//        try {
//
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//        }
//    }

    /**
     * 请求用户资金变动信息
     * @param request 请求用户信息
     * @param  page 分页页码
     * */
    @GetMapping("/api/app/funds_record")
    public Result getFundsRecord(HttpServletRequest request, Integer page){
        String fundsrecordPath="http://"+request.getServerName()+":"
                +request.getServerPort()
                +request.getRequestURI();

        String mobile = TokenUtils.getMobileByRequest(request);
        if (page ==null||page<=0)page=1;
        try {
            Users user = this.authService.findUsersByPhoneNumber(mobile);
            Integer count = fundsRecordsService.getCount(user.getId());
            Integer start = (this.perValue)*(page-1);

            List<FundsRecords> list = this.fundsRecordsService.findFundsRecordsByPageAndUid(start, perValue, user.getId());

            PageResult pageResult = PageResult.build(perValue,page,fundsrecordPath,null,list,count);
            return pageResult;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return SimpleResult.build(500,"服务器内部错误");
        }
    }
    @GetMapping("/api/app/funds_record/{id}")
    public Result getFundsRecord(HttpServletRequest request,@PathVariable String id){
        String mobile=TokenUtils.getMobileByRequest(request);
        Users user=null;
        try {
           user = this.authService.findUsersByPhoneNumber(mobile);

          FundsRecords fundsRecords=  this.fundsRecordsService.findFundsRecordsByTradeNoAndUid(id,user.getId());

          if (fundsRecords==null){
              return SimpleResult.build(230,"订单信息未找到");
          }
          return new FundsRecordsResult(fundsRecords);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return SimpleResult.build(500,"服务器内部错误");
        }
    }
}
