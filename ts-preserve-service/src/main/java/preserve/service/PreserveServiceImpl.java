package preserve.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import preserve.domain.*;
import java.util.Date;
import java.util.UUID;

@Service
public class PreserveServiceImpl implements PreserveService{

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public OrderTicketsResult preserve(OrderTicketsInfo oti,String accountId,String loginToken){
        VerifyResult tokenResult = verifySsoLogin(loginToken);
        OrderTicketsResult otr = new OrderTicketsResult();
        if(tokenResult.isStatus() == true){
            System.out.println("[Preserve Service][Verify Login] Success");
            //1.黄牛检测
            System.out.println("[Preserve Service] [Step 1] Check Security");
            CheckInfo checkInfo = new CheckInfo();
            checkInfo.setAccountId(accountId);
            CheckResult result = checkSecurity(checkInfo);
            if(result.isStatus() == false){
                otr.setStatus(false);
                otr.setMessage(result.getMessage());
                otr.setOrder(null);
                return otr;
            }
            System.out.println("[Preserve Service] [Step 1] Check Security Complete");
            //2.查询联系人信息 -- 修改，通过基础信息微服务作为中介
            System.out.println("[Preserve Service] [Step 2] Find contacts");
            GetContactsInfo gci = new GetContactsInfo();
            System.out.println("[Preserve Service] [Step 2] Contacts Id:" + oti.getContactsId());
            gci.setContactsId(oti.getContactsId());
            gci.setLoginToken(loginToken);
            GetContactsResult gcr = getContactsById(gci);
            if(gcr.isStatus() == false){
                System.out.println("[Preserve Service][Get Contacts] Fail." + gcr.getMessage());
                otr.setStatus(false);
                otr.setMessage(gcr.getMessage());
                otr.setOrder(null);
                return otr;
            }
            System.out.println("[Preserve Service][Step 2] Complete");
            //3.查询座位余票信息和车次的详情
            System.out.println("[Preserve Service] [Step 3] Check tickets num");
            GetTripAllDetailInfo gtdi = new GetTripAllDetailInfo();

            gtdi.setFrom(oti.getFrom());//todo
            gtdi.setTo(oti.getTo());

            gtdi.setTravelDate(oti.getDate());
            gtdi.setTripId(oti.getTripId());
            System.out.println("[Preserve Service] [Step 3] TripId:" + oti.getTripId());
            GetTripAllDetailResult gtdr = getTripAllDetailInformation(gtdi);
            if(gtdr.isStatus() == false){
                System.out.println("[Preserve Service][Search For Trip Detail Information] " + gcr.getMessage());
                otr.setStatus(false);
                otr.setMessage(gcr.getMessage());
                otr.setOrder(null);
                return otr;
            }else{
                TripResponse tripResponse = gtdr.getTripResponse();
                if(oti.getSeatType() == SeatClass.FIRSTCLASS.getCode()){
                    if(tripResponse.getConfortClass() == 0){
                        System.out.println("[Preserve Service][Check seat is enough] " + gcr.getMessage());
                        otr.setStatus(false);
                        otr.setMessage("Seat Not Enough");
                        otr.setOrder(null);
                        return otr;
                    }
                }else{
                    if(tripResponse.getEconomyClass() == SeatClass.SECONDCLASS.getCode()){
                        if(tripResponse.getConfortClass() == 0){
                            System.out.println("[Preserve Service][Check seat is enough] " + gcr.getMessage());
                            otr.setStatus(false);
                            otr.setMessage("Seat Not Enough");
                            otr.setOrder(null);
                            return otr;
                        }
                    }
                }
            }
            Trip trip = gtdr.getTrip();
            System.out.println("[Preserve Service] [Step 3] Tickets Enough");
            //4.下达订单请求 设置order的各个信息
            System.out.println("[Preserve Service] [Step 4] Do Order");
            Contacts contacts = gcr.getContacts();
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

//            QueryPriceInfo queryPriceInfo = new QueryPriceInfo();
//            queryPriceInfo.setStartingPlaceId(fromStationId);
//            queryPriceInfo.setEndPlaceId(toStationId);
//            if(oti.getSeatType() == SeatClass.FIRSTCLASS.getCode()){
//                queryPriceInfo.setSeatClass("confortClass");
//                System.out.println("[Preserve Service][Seat Class] Confort Class.");
//            }else if(oti.getSeatType() == SeatClass.SECONDCLASS.getCode()) {
//                queryPriceInfo.setSeatClass("economyClass");
//                System.out.println("[Preserve Service][Seat Class] Economy Class.");
//            }
//            queryPriceInfo.setTrainTypeId(gtdr.getTrip().getTrainTypeId());//----------------------------
//            String ticketPrice = getPrice(queryPriceInfo);
//            order.setPrice(ticketPrice);//Set ticket price
            order.setPrice("100");
            System.out.println("[Preserve Service][Order Price] Price is: " + order.getPrice());

            order.setSeatClass(oti.getSeatType());
            System.out.println("[Preserve Service][Order] Order Travel Date:" + oti.getDate().toString());
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
                System.out.println("[Preserve Service][Create Order Fail] Create Order Fail." +
                        "Reason:" + cor.getMessage());
                otr.setStatus(false);
                otr.setMessage(cor.getMessage());
                otr.setOrder(null);
                return otr;
            }
            System.out.println("[Preserve Service] [Step 4] Do Order Complete");
            otr.setStatus(true);
            otr.setMessage("Success");
            otr.setOrder(cor.getOrder());
            //5.检查保险的选择
            if(oti.getAssurance() == 0){
                System.out.println("[Preserve Service][Step 5] Do not need to buy assurance");
            }else{
                AddAssuranceResult addAssuranceResult = addAssuranceForOrder(
                        oti.getAssurance(),cor.getOrder().getId().toString());
                if(addAssuranceResult.isStatus() == true){
                    System.out.println("[Preserve Service][Step 5] Buy Assurance Success");
                }else{
                    System.out.println("[Preserve Service][Step 5] Buy Assurance Fail.");
                    otr.setMessage("Success.But Buy Assurance Fail.");
                }
            }
            //6.发送notification
            System.out.println("[Preserve Service]");
            GetAccountByIdInfo getAccountByIdInfo = new GetAccountByIdInfo();
            getAccountByIdInfo.setAccountId(order.getAccountId().toString());
            GetAccountByIdResult getAccountByIdResult = getAccount(getAccountByIdInfo);
            if(result.isStatus() == false){
                return null;
            }

            NotifyInfo notifyInfo = new NotifyInfo();
            notifyInfo.setDate(new Date().toString());

            notifyInfo.setEmail(getAccountByIdResult.getAccount().getEmail());
            notifyInfo.setStartingPlace(order.getFrom());
            notifyInfo.setEndPlace(order.getTo());
            notifyInfo.setUsername(getAccountByIdResult.getAccount().getName());
            notifyInfo.setSeatNumber(order.getSeatNumber());
            notifyInfo.setOrderNumber(order.getId().toString());
            notifyInfo.setPrice(order.getPrice());
            notifyInfo.setSeatClass(SeatClass.getNameByCode(order.getSeatClass()));
            notifyInfo.setStartingTime(order.getTravelTime().toString());

            sendEmail(notifyInfo);
        }else{
            System.out.println("[Preserve Service][Verify Login] Fail");
            otr.setStatus(false);
            otr.setMessage("Not Login");
            otr.setOrder(null);
        }
        return otr;
    }

    public boolean sendEmail(NotifyInfo notifyInfo){
        System.out.println("[Preserve Service][Send Email]");
        boolean result = restTemplate.postForObject(
                "http://ts-notification-service:17853/notification/order_cancel_success",
                notifyInfo,
                Boolean.class
        );
        return result;
    }

    public GetAccountByIdResult getAccount(GetAccountByIdInfo info){
        System.out.println("[Cancel Order Service][Get By Id]");
        GetAccountByIdResult result = restTemplate.postForObject(
                "http://ts-sso-service:12349/account/findById",
                info,
                GetAccountByIdResult.class
        );
        return result;
    }

    private AddAssuranceResult addAssuranceForOrder(int assuranceType,String orderId){
        System.out.println("[Preserve Service][Add Assurance For Order]");
        AddAssuranceInfo info = new AddAssuranceInfo();
        info.setOrderId(orderId);
        info.setTypeIndex(assuranceType);
        AddAssuranceResult result = restTemplate.postForObject(
                "http://ts-assurance-service:18888/assurance/create",
                info,
                AddAssuranceResult.class
        );
        return result;
    }

    private String queryForStationId(String stationName){
        System.out.println("[Preserve Service][Get Station Name]");
        QueryForId queryForId = new QueryForId();
        queryForId.setName(stationName);
        String stationId = restTemplate.postForObject("http://ts-station-service:12345/station/queryForId",queryForId,String.class);
        return stationId;
    }

    private String getPrice(QueryPriceInfo info){
        System.out.println("[Preserve Service][Get Price] Checking....");
        String price = restTemplate.postForObject("http://ts-price-service:16579/price/query",info,String.class);
        return price;
    }

    private CheckResult checkSecurity(CheckInfo info){
        System.out.println("[Preserve Service][Check Security] Checking....");
        CheckResult result = restTemplate.postForObject("http://ts-security-service:11188/security/check",info,CheckResult.class);
        return result;
    }

    private VerifyResult verifySsoLogin(String loginToken){
        System.out.println("[Preserve Service][Verify Login] Verifying....");
        VerifyResult tokenResult = restTemplate.getForObject(
                "http://ts-sso-service:12349/verifyLoginToken/" + loginToken,
                VerifyResult.class);
        return tokenResult;
    }

    private GetTripAllDetailResult getTripAllDetailInformation(GetTripAllDetailInfo gtdi){
        System.out.println("[Preserve Service][Get Trip All Detail Information] Getting....");
        GetTripAllDetailResult gtdr = restTemplate.postForObject(
                "http://ts-travel-service:12346/travel/getTripAllDetailInfo/"
                ,gtdi,GetTripAllDetailResult.class);
        return gtdr;
    }

    private GetContactsResult getContactsById(GetContactsInfo gci){
        System.out.println("[Preserve Service][Get Contacts By Id] Getting....");
        GetContactsResult gcr = restTemplate.postForObject(
                "http://ts-contacts-service:12347/contacts/getContactsById/"
                ,gci,GetContactsResult.class);
        return gcr;
    }

    private CreateOrderResult createOrder(CreateOrderInfo coi){
        System.out.println("[Preserve Service][Get Contacts By Id] Creating....");
        CreateOrderResult cor = restTemplate.postForObject(
                "http://ts-order-service:12031/order/create/"
                ,coi,CreateOrderResult.class);
        return cor;
    }

}
