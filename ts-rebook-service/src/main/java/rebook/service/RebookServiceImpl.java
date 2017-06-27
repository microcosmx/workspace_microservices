package rebook.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rebook.domain.*;
import rebook.domain.RebookInfo;
import rebook.domain.RebookResult;

/**
 * Created by Administrator on 2017/6/26.
 */
@Service
public class RebookServiceImpl implements RebookService{

    private RestTemplate restTemplate = new RestTemplate();

    public RebookResult rebook(RebookInfo info, String loginId, String loginToken){
        RebookResult rebookResult = new RebookResult();

        //1.黄牛检测
        CheckInfo checkInfo = new CheckInfo();
        checkInfo.setAccountId(loginId);
        CheckResult result = checkSecurity(checkInfo);
        if(result.isStatus() == false){
            rebookResult.setStatus(false);
            rebookResult.setMessage(result.getMessage());
            rebookResult.setOrder(null);
            return rebookResult;
        }

        //改签只能改签一次，查询订单状态来判断是否已经改签过
        if(info.getOldTripId().startsWith("G") || info.getOldTripId().startsWith("D")){
            result = restTemplate.postForObject(
                    "http://ts-order-service:12031/order/price", new QueryOrder(info.getOrderId()),QueryOrderResult.class);
        }else{
            result = restTemplate.postForObject(
                    "http://ts-order-other-service:12032/orderOther/price", new QueryOrder(info.getOrderId()),QueryOrderResult.class);
        }




        //查询当前时间和旧订单乘车时间，根据时间来判断能否改签，发车两小时后不能改签
        //改签不能更换出发地和目的地，只能更改车次、席位、时间




        //3.查询座位余票信息和车次的详情
        GetTripAllDetailInfo gtdi = new GetTripAllDetailInfo();
        gtdi.setFrom(oti.getFrom());
        gtdi.setTo(oti.getTo());
        gtdi.setTravelDate(oti.getDate());
        gtdi.setTripId(oti.getTripId());
        GetTripAllDetailResult gtdr = getTripAllDetailInformation(gtdi);
        if(gtdr.isStatus() == false){
            otr.setStatus(false);
            otr.setMessage(gcr.getMessage());
            otr.setOrder(null);
            return otr;
        }else{
            TripResponse tripResponse = gtdr.getTripResponse();
            if(oti.getSeatType() == SeatClass.FIRSTCLASS.getCode()){
                if(tripResponse.getConfortClass() == 0){
                    otr.setStatus(false);
                    otr.setMessage("Seat Not Enough");
                    otr.setOrder(null);
                }
            }else{
                if(tripResponse.getEconomyClass() == SeatClass.SECONDCLASS.getCode()){
                    if(tripResponse.getConfortClass() == 0){
                        otr.setStatus(false);
                        otr.setMessage("Seat Not Enough");
                        otr.setOrder(null);
                    }
                }
            }
        }
        Trip trip = gtdr.getTrip();

        //4.下达订单请求 设置order的各个信息
        Contacts contacts = gcr.getContacts();
        Order order = new Order();
        order.setId(UUID.randomUUID());
        order.setTrainNumber(oti.getTripId());
        order.setAccountId(UUID.fromString(oti.getAccountId()));
        order.setFrom(oti.getFrom());
        order.setTo(oti.getTo());
        order.setBoughtDate(new Date());
        order.setStatus(OrderStatus.NOTPAID.getCode());
        order.setContactsDocumentNumber(contacts.getDocumentNumber());
        order.setContactsName(contacts.getName());
        order.setDocumentType(contacts.getDocumentType());
        order.setPrice("100.0");//Set ticket price
        order.setSeatClass(oti.getSeatType());
        order.setTravelDate(oti.getDate());
        order.setTravelTime(trip.getStartingTime());
        if(oti.getSeatType() == SeatClass.FIRSTCLASS.getCode()){//Dispatch the seat
            int firstClassRemainNum = gtdr.getTripResponse().getConfortClass();
            order.setSeatNumber("FirstClass-" + firstClassRemainNum);
        }else{
            int secondClassRemainNum = gtdr.getTripResponse().getEconomyClass();
            order.setSeatNumber("SecondClass-" + secondClassRemainNum);
        }
        CreateOrderInfo coi = new CreateOrderInfo();//Send info to create the order.
        coi.setLoginToken(oti.getLoginToken());
        coi.setOrder(order);
        CreateOrderResult cor = createOrder(coi);
        if(cor.isStatus() == false){
            otr.setStatus(false);
            otr.setMessage(cor.getMessage());
            otr.setOrder(null);
            return otr;
        }
        otr.setStatus(true);
        otr.setMessage("Success");
        otr.setOrder(order);

        return null;
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

    private GetTripAllDetailResult getTripAllDetailInformation(GetTripAllDetailInfo gtdi){
        GetTripAllDetailResult gtdr = restTemplate.postForObject(
                "http://ts-travel-service:12346/travel/getTripAllDetailInfo/"
                ,gtdi,GetTripAllDetailResult.class);
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
}
