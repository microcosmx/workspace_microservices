package preserveOther.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import preserveOther.domain.*;
import java.util.Date;
import java.util.UUID;

@Service
public class PreserveOtherServiceImpl implements PreserveOtherService{

    private RestTemplate restTemplate;

    @Override
    public OrderTicketsResult preserve(OrderTicketsInfo oti){
        VerifyResult tokenResult = verifySsoLogin(oti.getLoginToken());
        OrderTicketsResult otr = new OrderTicketsResult();
        if(tokenResult.isStatus() == true){
            System.out.println("[Preserve Other Service][Verify Login] Success");
            //1.查询联系人信息 -- 修改，通过基础信息微服务作为中介
            System.out.println("[Preserve Other Service] [Step 1] Find contacts");
            GetContactsInfo gci = new GetContactsInfo();
            System.out.println("[Preserve Other Service] [Step 1] Contacts Id:" + oti.getContactsId());
            gci.setContactsId(oti.getContactsId());
            gci.setLoginToken(oti.getLoginToken());
            GetContactsResult gcr = getContactsById(gci);
            if(gcr.isStatus() == false){
                System.out.println("[Preserve Other Service][Get Contacts] Fail." + gcr.getMessage());
                otr.setStatus(false);
                otr.setMessage(gcr.getMessage());
                otr.setOrder(null);
                return otr;
            }
            System.out.println("[Preserve Other Service][Step 1] Complete");
            //2.查询座位余票信息和车次的详情
            System.out.println("[Preserve Other Service] [Step 2] Check tickets num");
            GetTripAllDetailInfo gtdi = new GetTripAllDetailInfo();
            gtdi.setFrom(oti.getFrom());
            gtdi.setTo(oti.getTo());
            gtdi.setTravelDate(oti.getDate());
            gtdi.setTripId(oti.getTripId());
            System.out.println("[Preserve Other Service] [Step 2] TripId:" + oti.getTripId());
            GetTripAllDetailResult gtdr = getTripAllDetailInformation(gtdi);
            if(gtdr.isStatus() == false){
                System.out.println("[Preserve Other Service][Search For Trip Detail Information] " + gcr.getMessage());
                otr.setStatus(false);
                otr.setMessage(gcr.getMessage());
                otr.setOrder(null);
                return otr;
            }else{
                TripResponse tripResponse = gtdr.getTripResponse();
                if(oti.getSeatType() == SeatClass.FIRSTCLASS.getCode()){
                    if(tripResponse.getConfortClass() == 0){
                        System.out.println("[Preserve Other Service][Check seat is enough] " + gcr.getMessage());
                        otr.setStatus(false);
                        otr.setMessage("Seat Not Enough");
                        otr.setOrder(null);
                    }
                }else{
                    if(tripResponse.getEconomyClass() == SeatClass.SECONDCLASS.getCode()){
                        if(tripResponse.getConfortClass() == 0){
                            System.out.println("[Preserve Other Service][Check seat is enough] " + gcr.getMessage());
                            otr.setStatus(false);
                            otr.setMessage("Seat Not Enough");
                            otr.setOrder(null);
                        }
                    }
                }
            }
            Trip trip = gtdr.getTrip();
            System.out.println("[Preserve Other Service] [Step 2] Tickets Enough");
            //3.下达订单请求 设置order的各个信息
            System.out.println("[Preserve Other Service] [Step 3] Do Order");
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
            order.setPrice(100.0);//Set ticket price
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
                System.out.println("[Preserve Other Service][Create Order Fail] Create Order Fail." +
                        "Reason:" + cor.getMessage());
                otr.setStatus(false);
                otr.setMessage(cor.getMessage());
                otr.setOrder(null);
                return otr;
            }
            System.out.println("[Preserve Other Service] [Step 3] Do Order Complete");
            otr.setStatus(true);
            otr.setMessage("Success");
            otr.setOrder(order);
        }else{
            System.out.println("[Preserve Other Service][Verify Login] Fail");
            otr.setStatus(false);
            otr.setMessage("Not Login");
            otr.setOrder(null);
        }
        return otr;
    }

    private VerifyResult verifySsoLogin(String loginToken){
        restTemplate = new RestTemplate();
        System.out.println("[Preserve Other Service][Verify Login] Verifying....");
        VerifyResult tokenResult = restTemplate.getForObject(
                "http://ts-sso-service:12349/verifyLoginToken/" + loginToken,
                VerifyResult.class);
        return tokenResult;
    }

    private GetTripAllDetailResult getTripAllDetailInformation(GetTripAllDetailInfo gtdi){
        restTemplate = new RestTemplate();
        System.out.println("[Preserve Other Service][Get Trip All Detail Information] Getting....");
        GetTripAllDetailResult gtdr = restTemplate.postForObject(
                "http://ts-travel2-service:16346/travel2/getTripAllDetailInfo/"
                ,gtdi,GetTripAllDetailResult.class);
        return gtdr;
    }

    private GetContactsResult getContactsById(GetContactsInfo gci){
        restTemplate = new RestTemplate();
        System.out.println("[Preserve Other Service][Get Contacts By Id] Getting....");
        GetContactsResult gcr = restTemplate.postForObject(
                "http://ts-contacts-service:12347/contacts/getContactsById/"
                ,gci,GetContactsResult.class);
        return gcr;
    }

    private CreateOrderResult createOrder(CreateOrderInfo coi){
        restTemplate = new RestTemplate();
        System.out.println("[Preserve Other Service][Get Contacts By Id] Creating....");
        CreateOrderResult cor = restTemplate.postForObject(
                "http://ts-order-other-service:12032/orderOther/create"
                ,coi,CreateOrderResult.class);
        return cor;
    }
}
