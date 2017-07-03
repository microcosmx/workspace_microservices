package rebook.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rebook.domain.*;
import rebook.domain.RebookInfo;
import rebook.domain.RebookResult;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2017/6/26.
 */
@Service
public class RebookServiceImpl implements RebookService{

    private RestTemplate restTemplate = new RestTemplate();

    public RebookResult rebook(RebookInfo info, String loginId, String loginToken){
        RebookResult rebookResult = new RebookResult();

        //黄牛检测
        CheckInfo checkInfo = new CheckInfo();
        checkInfo.setAccountId(loginId);
        CheckResult checkResult = checkSecurity(checkInfo);
        if(checkResult.isStatus() == false){
            rebookResult.setStatus(false);
            rebookResult.setMessage(checkResult.getMessage());
            rebookResult.setOrder(null);
            return rebookResult;
        }

        QueryOrderResult queryOrderResult;
        //改签只能改签一次，查询订单状态来判断是否已经改签过
        if(info.getOldTripId().startsWith("G") || info.getOldTripId().startsWith("D")){
            queryOrderResult = restTemplate.postForObject(
                    "http://ts-order-service:12031/order/getById", new QueryOrder(info.getOrderId()),QueryOrderResult.class);
        }else{
            queryOrderResult = restTemplate.postForObject(
                    "http://ts-order-other-service:12032//orderOther/getById", new QueryOrder(info.getOrderId()),QueryOrderResult.class);
        }

        if(!queryOrderResult.isStatus()){
            rebookResult.setStatus(false);
            rebookResult.setMessage(queryOrderResult.getMessage());
            rebookResult.setOrder(null);
            return rebookResult;
        }

        Order order = queryOrderResult.getOrder();
        int status = order.getStatus();
        if(status == OrderStatus.NOTPAID.getCode() || status ==OrderStatus.PAID.getCode()){
            // do nothing
        }else if(status == OrderStatus.CHANGE.getCode()){
            rebookResult.setStatus(false);
            rebookResult.setMessage("You have already changed your ticket and you can only change one time.");
            rebookResult.setOrder(null);
            return rebookResult;
        }else if(status == OrderStatus.COLLECTED.getCode()){
            rebookResult.setStatus(false);
            rebookResult.setMessage("You have already collected your ticket and you can change it now.");
            rebookResult.setOrder(null);
            return rebookResult;
        } else{
            rebookResult.setStatus(false);
            rebookResult.setMessage("You can't change your ticket.");
            rebookResult.setOrder(null);
            return rebookResult;
        }



        //查询当前时间和旧订单乘车时间，根据时间来判断能否改签，发车两小时后不能改签
        if(!checkTime(order.getTravelDate(),order.getTravelTime())){
            rebookResult.setStatus(false);
            rebookResult.setMessage("You can only change the ticket before the train start or within 2 hours after the train start.");
            rebookResult.setOrder(null);
            return rebookResult;
        }


        //改签不能更换出发地和目的地，只能更改车次、席位、时间
        //查询座位余票信息和车次的详情
        GetTripAllDetailInfo gtdi = new GetTripAllDetailInfo();
        gtdi.setFrom(order.getFrom());
        gtdi.setTo(order.getTo());
        gtdi.setTravelDate(info.getDate());
        gtdi.setTripId(info.getTripId());
        GetTripAllDetailResult gtdr = getTripAllDetailInformation(gtdi,info.getTripId());
        if(gtdr.isStatus() == false){
            rebookResult.setStatus(false);
            rebookResult.setMessage(gtdr.getMessage());
            rebookResult.setOrder(null);
            return rebookResult;
        }else{
            TripResponse tripResponse = gtdr.getTripResponse();
            if(info.getSeatType() == SeatClass.FIRSTCLASS.getCode()){
                if(tripResponse.getConfortClass() == 0){
                    rebookResult.setStatus(false);
                    rebookResult.setMessage("Seat Not Enough");
                    rebookResult.setOrder(null);
                    return rebookResult;
                }
            }else{
                if(tripResponse.getEconomyClass() == SeatClass.SECONDCLASS.getCode()){
                    if(tripResponse.getConfortClass() == 0){
                        rebookResult.setStatus(false);
                        rebookResult.setMessage("Seat Not Enough");
                        rebookResult.setOrder(null);
                    }
                }
            }
        }
        Trip trip = gtdr.getTrip();

        //4.修改原有订单 设置order的各个信息
        String oldTripId = order.getTrainNumber();
        order.setTrainNumber(info.getTripId());
        order.setBoughtDate(new Date());
        order.setStatus(OrderStatus.CHANGE.getCode());



        QueryPriceInfo queryPriceInfo = new QueryPriceInfo();
        queryPriceInfo.setStartingPlaceId(order.getFrom());
        queryPriceInfo.setEndPlaceId(order.getTo());
        if(info.getSeatType() == SeatClass.FIRSTCLASS.getCode()){
            queryPriceInfo.setSeatClass("confortClass");
        }else if(info.getSeatType() == SeatClass.SECONDCLASS.getCode()) {
            queryPriceInfo.setSeatClass("economyClass");
        }
        queryPriceInfo.setTrainTypeId(trip.getTrainTypeId());//----------------------------
        String ticketPrice = getPrice(queryPriceInfo);
        String oldPrice = order.getPrice();
        order.setPrice(ticketPrice);//Set ticket price

        //处理差价，多退少补
        //退掉原有的票，让其他人可以订到对应的位置
        BigDecimal priceOld = new BigDecimal(oldPrice);
        BigDecimal priceNew = new BigDecimal(ticketPrice);
        if(priceOld.compareTo(priceNew) > 0){
            //退差价
        }else if(priceOld.compareTo(priceNew) == 0){
            //do nothing
        }else{
            //补差价
        }


        order.setSeatClass(info.getSeatType());
        order.setTravelDate(info.getDate());
        order.setTravelTime(trip.getStartingTime());
        if(info.getSeatType() == SeatClass.FIRSTCLASS.getCode()){//Dispatch the seat
            int firstClassRemainNum = gtdr.getTripResponse().getConfortClass();
            order.setSeatNumber("FirstClass-" + firstClassRemainNum);
        }else{
            int secondClassRemainNum = gtdr.getTripResponse().getEconomyClass();
            order.setSeatNumber("SecondClass-" + secondClassRemainNum);
        }

        //更新订单信息
        //原订单和新订单如果分别位于高铁动车和其他订单，应该删掉原订单，在另一边新建，用新的id
        if((tripGD(oldTripId) && tripGD(info.getTripId())) || (!tripGD(oldTripId) && !tripGD(info.getTripId()))){

        }else{

        }

        return null;
    }

    private ChangeOrderResult updateOrder(ChangeOrderInfo info, String tripId){
        ChangeOrderResult result;
        if(tripGD(tripId)){
            result = restTemplate.postForObject("http://ts-order-service:12031/order/update",
                    info,ChangeOrderResult.class);
        }else{
            result = restTemplate.postForObject("http://ts-order-other-service:12032/orderOther/update",
                    info,ChangeOrderResult.class);
        }

        return result;
    }

    private boolean tripGD(String tripId){
        if(tripId.startsWith("G") || tripId.startsWith("D")){
            return true;
        }else{
            return false;
        }
    }

    private CheckResult checkSecurity(CheckInfo info){
        CheckResult result = restTemplate.postForObject("http://ts-security-service:11188/security/check",info,CheckResult.class);
        return result;
    }

    private VerifyResult verifySsoLogin(String loginToken){
        VerifyResult tokenResult = restTemplate.getForObject(
                "http://ts-sso-service:12349/verifyLoginToken/" + loginToken,
                VerifyResult.class);
        return tokenResult;
    }

    private GetTripAllDetailResult getTripAllDetailInformation(GetTripAllDetailInfo gtdi, String tripId){
        GetTripAllDetailResult gtdr;
        if(tripId.startsWith("G") || tripId.startsWith("D")){
            gtdr = restTemplate.postForObject(
                    "http://ts-travel-service:12346/travel/getTripAllDetailInfo"
                    ,gtdi,GetTripAllDetailResult.class);
        }else{
            gtdr = restTemplate.postForObject(
                    "http://ts-travel-service:16346/travel2/getTripAllDetailInfo"
                    ,gtdi,GetTripAllDetailResult.class);
        }

        return gtdr;
    }

    private GetContactsResult getContactsById(GetContactsInfo gci){
        GetContactsResult gcr = restTemplate.postForObject(
                "http://ts-contacts-service:12347/contacts/getContactsById/"
                ,gci,GetContactsResult.class);
        return gcr;
    }

    private CreateOrderResult createOrder(CreateOrderInfo coi){
        CreateOrderResult cor = restTemplate.postForObject(
                "http://ts-order-service:12031/order/create/"
                ,coi,CreateOrderResult.class);
        return cor;
    }

    private boolean checkTime(Date travelDate, Date travelTime) {
        boolean result = true;

        Calendar calDateA = Calendar.getInstance();
        Date today = new Date();
        calDateA.setTime(today);

        Calendar calDateB = Calendar.getInstance();
        calDateB.setTime(travelDate);

        Calendar calDateC = Calendar.getInstance();
        calDateC.setTime(travelTime);

        if(calDateA.get(Calendar.YEAR) > calDateB.get(Calendar.YEAR)){
            result = false;
        }else if(calDateA.get(Calendar.YEAR) == calDateB.get(Calendar.YEAR)){
            if(calDateA.get(Calendar.MONTH) > calDateB.get(Calendar.MONTH)){
                result = false;
            }else if(calDateA.get(Calendar.MONTH) == calDateB.get(Calendar.MONTH)){
                if(calDateA.get(Calendar.DAY_OF_MONTH) > calDateB.get(Calendar.DAY_OF_MONTH)){
                    result = false;
                }else if(calDateA.get(Calendar.DAY_OF_MONTH) == calDateB.get(Calendar.DAY_OF_MONTH)){
                    if(calDateA.get(Calendar.HOUR_OF_DAY) > calDateC.get(Calendar.HOUR_OF_DAY)+2){
                        result = false;
                    }else if(calDateA.get(Calendar.HOUR_OF_DAY) == calDateC.get(Calendar.HOUR_OF_DAY)+2){
                        if(calDateA.get(Calendar.MINUTE) > calDateC.get(Calendar.MINUTE)){
                            result = false;
                        }
                    }
                }
            }
        }

        return result;
    }

    private String getPrice(QueryPriceInfo info){
        restTemplate = new RestTemplate();
        System.out.println("[Preserve Service][Get Price] Checking....");
        String price = restTemplate.postForObject("http://ts-price-service:16579/price/query",info,String.class);
        return price;
    }
}
