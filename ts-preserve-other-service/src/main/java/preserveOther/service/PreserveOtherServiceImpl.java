package preserveOther.service;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import preserveOther.domain.*;
import preserveOther.domain.CheckInfo;
import preserveOther.domain.CheckResult;
import preserveOther.domain.Contacts;
import preserveOther.domain.CreateOrderInfo;
import preserveOther.domain.CreateOrderResult;
import preserveOther.domain.GetContactsInfo;
import preserveOther.domain.GetContactsResult;
import preserveOther.domain.GetTripAllDetailInfo;
import preserveOther.domain.GetTripAllDetailResult;
import preserveOther.domain.Order;
import preserveOther.domain.OrderStatus;
import preserveOther.domain.OrderTicketsInfo;
import preserveOther.domain.OrderTicketsResult;
import preserveOther.domain.QueryForId;
import preserveOther.domain.QueryPriceInfo;
import preserveOther.domain.SeatClass;
import preserveOther.domain.Trip;
import preserveOther.domain.TripResponse;
import preserveOther.domain.VerifyResult;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

@Service
public class PreserveOtherServiceImpl implements PreserveOtherService{

    public static int count = 0;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public OrderTicketsResult preserve(OrderTicketsInfo oti,String accountId,String loginToken){
        VerifyResult tokenResult = verifySsoLogin(loginToken);
        OrderTicketsResult otr = new OrderTicketsResult();
        if(tokenResult.isStatus() == true){
            System.out.println("[Preserve Other Service][Verify Login] Success");
            //1.黄牛检测
            System.out.println("[Preserve Service] [Step 1] Check Security");
            CheckInfo checkInfo = new CheckInfo();
            checkInfo.setAccountId(accountId);
            CheckResult result = checkSecurity(checkInfo);
            if(result.isStatus() == false){
                System.out.println("[Preserve Service] [Step 1] Check Security Fail. Return soon.");
                otr.setStatus(false);
                otr.setMessage(result.getMessage());
                otr.setOrder(null);
                return otr;
            }
            System.out.println("[Preserve Service] [Step 1] Check Security Complete. ");
            //2.查询联系人信息 -- 修改，通过基础信息微服务作为中介
            Contacts orderContact = null;

            Gson gson = new Gson();
            String otiString = gson.toJson(oti);
            System.out.println("[打印出oti]:" + otiString);


            AddContactsResult addContactsResult = null;


            if(oti.getIsCreateContacts().equals("true")){
                //先查询联系人数量

                System.out.println("[Preserve Service]准备创建联系人");
                int contactsNum = calculateContacts(accountId);

                String name = oti.getContactsName();
                int type = oti.getContactsDocumentType();
                String number = oti.getContactsDocumentNumber();
                String phone = oti.getContactsPhoneNumber();


                if(contactsNum > 0){
                    System.out.println("[Preserve Service]准备创建联系人check");
                    addContactsResult = addContacts(name,type,number,phone,accountId);
                }else{
                    System.out.println("[Preserve Service]准备创建联系人without check");
                    addContactsResult = addContactsWithoutCheck(name,type,number,phone,accountId);
                }
                orderContact = addContactsResult.getContacts();
                //根据联系人数量再确定要调用哪边
            }else{
                System.out.println("[Preserve Other Service] [Step 2] Find contacts");
                GetContactsInfo gci = new GetContactsInfo();
                System.out.println("[Preserve Other Service] [Step 2] Contacts Id:" + oti.getContactsId());
                gci.setContactsId(oti.getContactsId());
                gci.setLoginToken(loginToken);
                GetContactsResult gcr = getContactsById(gci);
                orderContact = gcr.getContacts();
                if(gcr.isStatus() == false){
                    System.out.println("[Preserve Other Service][Get Contacts] Fail." + gcr.getMessage());
                    otr.setStatus(false);
                    otr.setMessage(gcr.getMessage());
                    otr.setOrder(null);
                    return otr;
                }
            }


            System.out.println("[Preserve Other Service][Step 2] Complete");
            //3.查询座位余票信息和车次的详情
            System.out.println("[Preserve Other Service] [Step 3] Check tickets num");
            GetTripAllDetailInfo gtdi = new GetTripAllDetailInfo();

            gtdi.setFrom(oti.getFrom());
            gtdi.setTo(oti.getTo());

            gtdi.setTravelDate(oti.getDate());
            gtdi.setTripId(oti.getTripId());
            System.out.println("[Preserve Other Service] [Step 3] TripId:" + oti.getTripId());
            GetTripAllDetailResult gtdr = getTripAllDetailInformation(gtdi);
            if(gtdr.isStatus() == false){
                System.out.println("[Preserve Other Service][Search For Trip Detail Information] " + gtdr.getMessage());
                otr.setStatus(false);
                otr.setMessage(gtdr.getMessage());
                otr.setOrder(null);
                return otr;
            }else{
                TripResponse tripResponse = gtdr.getTripResponse();
                if(oti.getSeatType() == SeatClass.FIRSTCLASS.getCode()){
                    if(tripResponse.getConfortClass() == 0){
                        System.out.println("[Preserve Other Service][Check seat is enough] " + gtdr.getMessage());
                        otr.setStatus(false);
                        otr.setMessage("Seat Not Enough");
                        otr.setOrder(null);
                        return otr;
                    }
                }else{
                    if(tripResponse.getEconomyClass() == SeatClass.SECONDCLASS.getCode()){
                        if(tripResponse.getConfortClass() == 0){
                            System.out.println("[Preserve Other Service][Check seat is enough] " + gtdr.getMessage());
                            otr.setStatus(false);
                            otr.setMessage("Seat Not Enough");
                            otr.setOrder(null);
                            return otr;
                        }
                    }
                }
            }
            Trip trip = gtdr.getTrip();
            System.out.println("[Preserve Other Service] [Step 3] Tickets Enough");
            //4.下达订单请求 设置order的各个信息
            System.out.println("[Preserve Other Service] [Step 4] Do Order");
            Contacts contacts = orderContact;
            Order order = new Order();
            order.setId(UUID.randomUUID());
            order.setTrainNumber(oti.getTripId());
            order.setAccountId(UUID.fromString(accountId));

            String fromStationId = queryForStationId(oti.getFrom());
            String toStationId = queryForStationId(oti.getTo());

            order.setFrom(fromStationId);
            order.setTo(toStationId);
            order.setBoughtDate(new Date());

            order.setStatus(OrderStatus.NOTPAID.getCode());

            order.setContactsDocumentNumber(contacts.getDocumentNumber());
            order.setContactsName(contacts.getName());
            order.setDocumentType(contacts.getDocumentType());

            QueryPriceInfo queryPriceInfo = new QueryPriceInfo();
            queryPriceInfo.setStartingPlaceId(fromStationId);
            queryPriceInfo.setEndPlaceId(toStationId);
            if(oti.getSeatType() == SeatClass.FIRSTCLASS.getCode()){
                queryPriceInfo.setSeatClass("confortClass");
                System.out.println("[Preserve Other Service][Seat Class] Confort Class.");
            }else if(oti.getSeatType() == SeatClass.SECONDCLASS.getCode()) {
                queryPriceInfo.setSeatClass("economyClass");
                System.out.println("[Preserve Other Service][Seat Class] Economy Class.");
            }
            queryPriceInfo.setTrainTypeId(gtdr.getTrip().getTrainTypeId());//----------------------------
            String ticketPrice = getPrice(queryPriceInfo);
            order.setPrice(ticketPrice);//Set ticket price
            System.out.println("[Preserve Other Service][Order Price] Price is: " + order.getPrice());

            order.setSeatClass(oti.getSeatType());
            System.out.println("[Preserve Other Service][Order] Order Travel Date:" + oti.getDate().toString());
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
            coi.setLoginToken(loginToken);
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
            System.out.println("[Preserve Other Service] [Step 4] Do Order Complete");
            otr.setStatus(true);
            otr.setMessage("Success");
            otr.setOrder(cor.getOrder());

            /**********如果用户添加联系人切联系人重复，抛出异常***********/
            if(oti.getIsCreateContacts().equals("true") && addContactsResult.isExists() == true){
                throw new RuntimeException();
            }

        }else{
            System.out.println("[Preserve Other Service][Verify Login] Fail");
            otr.setStatus(false);
            otr.setMessage("Not Login");
            otr.setOrder(null);
        }
        return otr;
    }

    private AddContactsResult addContactsWithoutCheck(String name,int type,String number,String phone,String loginId){
        System.out.println("[Preserve Other Service][Add Contacts Without Check]");
        AddContactsInfo info = new AddContactsInfo();
        info.setDocumentNumber(number);
        info.setDocumentType(type);
        info.setName(name);
        info.setPhoneNumber(phone);
        AddContactsResult addContactsResult = restTemplate.postForObject(
                "http://ts-contacts-service:12347/contacts/createWithoutCheck/" + loginId,
                info,AddContactsResult.class);
        return addContactsResult;
    }

    private AddContactsResult addContacts(String name,int type,String number,String phone,String loginId){
        System.out.println("[Preserve Other Service][Add Contacts With Check]");
        AddContactsInfo info = new AddContactsInfo();
        info.setDocumentNumber(number);
        info.setDocumentType(type);
        info.setName(name);
        info.setPhoneNumber(phone);
        AddContactsResult addContactsResult = restTemplate.postForObject(
                "http://ts-contacts-service:12347/contacts/createWithCheck/" + loginId,info,AddContactsResult.class);
        return addContactsResult;
    }

    private int calculateContacts(String loginId){
        System.out.println("[Preserve Other Service][Calculate Contacts]");
        ArrayList<Contacts> list = restTemplate.getForObject(
                "http://ts-contacts-service:12347/contacts/countContacts/" + loginId,
                ArrayList.class);
        if(list == null){
            return 0;
        }else{
            return list.size();
        }
    }

    private String queryForStationId(String stationName){
        System.out.println("[Preserve Other Service][Get Station Name]");
        QueryForId queryForId = new QueryForId();
        queryForId.setName(stationName);
        String stationId = restTemplate.postForObject("http://ts-station-service:12345/station/queryForId",queryForId,String.class);
        return stationId;
    }

    private String getPrice(QueryPriceInfo info){
        System.out.println("[Preserve Other Service][Get Price] Checking....");
        String price = restTemplate.postForObject("http://ts-price-service:16579/price/query",info,String.class);
        return price;
    }

    private CheckResult checkSecurity(CheckInfo info){
        System.out.println("[Preserve Other Service][Check Security] Checking....");
        CheckResult result  = null;
        if(count == 0){
            result = restTemplate.postForObject("http://ts-security-service:11188/security/checkInOneHour",info,CheckResult.class);
        }else if(count == 1){
            result = restTemplate.postForObject("http://ts-security-service:11188/security/checkTotalNumber",info,CheckResult.class);
        }else{
            result = restTemplate.postForObject("http://ts-security-service:11188/security/check",info,CheckResult.class);
        }
        count++;
//        CheckResult result = restTemplate.postForObject("http://ts-security-service:11188/security/check",info,CheckResult.class);
        return result;
    }

    private VerifyResult verifySsoLogin(String loginToken){
        System.out.println("[Preserve Other Service][Verify Login] Verifying....");
        VerifyResult tokenResult = restTemplate.getForObject(
                "http://ts-sso-service:12349/verifyLoginToken/" + loginToken,
                VerifyResult.class);
        return tokenResult;
    }

    private GetTripAllDetailResult getTripAllDetailInformation(GetTripAllDetailInfo gtdi){
        System.out.println("[Preserve Other Service][Get Trip All Detail Information] Getting....");
        GetTripAllDetailResult gtdr = restTemplate.postForObject(
                "http://ts-travel2-service:16346/travel2/getTripAllDetailInfo/"
                ,gtdi,GetTripAllDetailResult.class);
        return gtdr;
    }

    private GetContactsResult getContactsById(GetContactsInfo gci){
        System.out.println("[Preserve Other Service][Get Contacts By Id] Getting....");
        GetContactsResult gcr = restTemplate.postForObject(
                "http://ts-contacts-service:12347/contacts/getContactsById/"
                ,gci,GetContactsResult.class);
        return gcr;
    }

    private CreateOrderResult createOrder(CreateOrderInfo coi){
        System.out.println("[Preserve Other Service][Get Contacts By Id] Creating....");
        CreateOrderResult cor = restTemplate.postForObject(
                "http://ts-order-other-service:12032/orderOther/create"
                ,coi,CreateOrderResult.class);
        return cor;
    }
}
