package preserve.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import preserve.domain.*;
import preserve.service.PreserveService;
import java.util.Date;
import java.util.UUID;

@RestController
public class PreserveController {

    @Autowired
    private PreserveService preserveService;

    private RestTemplate restTemplate;

    @CrossOrigin(origins = "*")
    @RequestMapping(value="/preserve", method = RequestMethod.POST)
    public OrderTicketsResult preserve(@RequestBody OrderTicketsInfo oti){
        VerifyResult tokenResult = verifySsoLogin(oti.getLoginToken());
        OrderTicketsResult otr = new OrderTicketsResult();
        if(tokenResult.isStatus() == true){
            System.out.println("[PreserveService][VerifyLogin] Success");
            //1.查询联系人信息
            System.out.println("[PreserveService] [Step 1] Find contacts");
            GetContactsInfo gci = new GetContactsInfo();
            System.out.println("[PreserveService] [Step 1] Contacts Id:" + oti.getContactsId());
            gci.setContactsId(oti.getContactsId());
            gci.setLoginToken(oti.getLoginToken());
            GetContactsResult gcr = getContactsById(gci);
            if(gcr.isStatus() == false){
                System.out.println("[PreserveService][GetContacts] Fail." + gcr.getMessage());
                otr.setStatus(false);
                otr.setMessage(gcr.getMessage());
                return otr;
            }
            System.out.println("[PreserveService][Step 1] Complete");
            //2.查询座位余票信息
            System.out.println("[PreserveService] [Step 2] Check tickets num");
            GetTripAllDetailInfo gtdi = new GetTripAllDetailInfo();
            gtdi.setFrom(oti.getFrom());
            gtdi.setTo(oti.getTo());
            gtdi.setTravelDate(oti.getDate());
            gtdi.setTripId(oti.getTripId());
            System.out.println("[PreserveService] [Step 2] TripId:" + oti.getTripId());
            GetTripAllDetailResult gtdr = getTripAllDetailInformation(gtdi);
            if(gtdr.isStatus() == false){
                System.out.println("[PreserveService][SearchForTripDetailInformation] " + gcr.getMessage());
                otr.setStatus(false);
                otr.setMessage(gcr.getMessage());
                return otr;
            }else{
                TripResponse tripResponse = gtdr.getTripResponse();
                if(oti.getSeatType() == SeatClass.FIRSTCLASS.getCode()){
                    if(tripResponse.getConfortClass() == 0){
                        System.out.println("[PreserveService][Check seat is enough] " + gcr.getMessage());
                        otr.setStatus(false);
                        otr.setMessage("Seat Not Enough");
                    }
                }else{
                    if(tripResponse.getEconomyClass() == SeatClass.SECONDCLASS.getCode()){
                        if(tripResponse.getConfortClass() == 0){
                            System.out.println("[PreserveService][Check seat is enough] " + gcr.getMessage());
                            otr.setStatus(false);
                            otr.setMessage("Seat Not Enough");
                        }
                    }
                }
            }
            System.out.println("[PreserveService] [Step 2] Tickets Enough");
            //3.查询车次信息
            System.out.println("[PreserveService] [Step 3] Find Trip Info");
            GetTripInfo gti = new GetTripInfo();
            gti.setTripId(oti.getTripId());
            Trip trip = getTripByTripId(gti);
            if(trip == null){
                System.out.println("[PreserveService][GetTrip] " + gcr.getMessage());
                otr.setStatus(false);
                otr.setMessage("Trip Not Found");
                return otr;
            }
            System.out.println("[PreserveService] [Step 3] Complete");
            //4.黄牛检测
                //此部分暂未实现
            //5.下达订单请求
            System.out.println("[PreserveService] [Step 4] Do Order");
            Contacts contacts = gcr.getContacts();
            Order order = new Order();
            //设置order的各个信息
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
            //票价设定 - 未完成
            order.setPrice(100.0);
            order.setSeatClass(oti.getSeatType());
            order.setTravelDate(oti.getDate());
            order.setTravelTime(trip.getStartingTime());
            //座位分配 - 未完成
            if(oti.getSeatType() == SeatClass.FIRSTCLASS.getCode()){
                int firstClassRemainNum = gtdr.getTripResponse().getConfortClass();
                order.setSeatNumber("FirstClass-" + firstClassRemainNum);
            }else{
                int secondClassRemainNum = gtdr.getTripResponse().getEconomyClass();
                order.setSeatNumber("SecondClass-" + secondClassRemainNum);
            }

            //------------------
            CreateOrderInfo coi = new CreateOrderInfo();
            coi.setLoginToken(oti.getLoginToken());
            coi.setOrder(order);
            CreateOrderResult cor = createOrder(coi);
            if(cor.isStatus() == false){
                System.out.println("[PreserveService][CreateOrderFail] Create Order Fail.");
                otr.setStatus(false);
                otr.setMessage(cor.getMessage());
                return otr;
            }
            System.out.println("[PreserveService] [Step 4] Do Order Complete");
        }else{
            System.out.println("[PreserveService][VerifyLogin] Fail");
            otr.setStatus(false);
            otr.setMessage("Not Login");
        }
        return otr;
    }

    private GetTripAllDetailResult getTripAllDetailInformation(GetTripAllDetailInfo gtdi){
        restTemplate = new RestTemplate();
        System.out.println("[PreserveService][GetTripAllDetailInformation] Getting....");
        GetTripAllDetailResult gtdr = restTemplate.postForObject(
                "http://ts-travel-service:12346/travel/getTripAllDetailInfo/"
                ,gtdi,GetTripAllDetailResult.class);
        return gtdr;
    }

    private Trip getTripByTripId(GetTripInfo gti){
        restTemplate = new RestTemplate();
        System.out.println("[PreserveService][GetTripByTripId] Getting....");
        Trip trip = restTemplate.postForObject(
                "http://ts-travel-service:12346/travel/retrieve/"
                ,gti,Trip.class);
        return trip;
    }

    private CreateOrderResult createOrder(CreateOrderInfo coi){
        restTemplate = new RestTemplate();
        System.out.println("[PreserveService][GetContactsById] Creating....");
        CreateOrderResult cor = restTemplate.postForObject(
                "http://ts-order-service:12031/order/create/"
                ,coi,CreateOrderResult.class);
        return cor;
    }

    private GetContactsResult getContactsById(GetContactsInfo gci){
        restTemplate = new RestTemplate();
        System.out.println("[PreserveService][GetContactsByIs] Getting....");
        GetContactsResult gcr = restTemplate.postForObject(
                "http://ts-contacts-service:12347/contacts/getContactsById/"
                ,gci,GetContactsResult.class);
        return gcr;
    }

    private VerifyResult verifySsoLogin(String loginToken){
        restTemplate = new RestTemplate();
        System.out.println("[PreserveService][VerifyLogin] Verifying....");
        VerifyResult tokenResult = restTemplate.getForObject(
                "http://ts-sso-service:12349/verifyLoginToken/" + loginToken,
                VerifyResult.class);
        return tokenResult;
    }

}
