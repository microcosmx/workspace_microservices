package other.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import other.domain.*;
import other.service.OrderOtherService;
import java.util.ArrayList;

@RestController
public class OrderOtherController {

    @Autowired
    private OrderOtherService orderService;

    private RestTemplate restTemplate;

    @RequestMapping(path = "/welcome", method = RequestMethod.GET)
    public String home() {
        return "Welcome to [ Order Other Service ] !";
    }

    /***************************For Normal Use***************************/

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/orderOther/create", method = RequestMethod.POST)
    public CreateOrderResult createNewOrder(@RequestBody CreateOrderInfo coi){
        System.out.println("[Order Other Service][Create Order] Create Order form " + coi.getOrder().getFrom() + " --->"
                + coi.getOrder().getTo() + " at " + coi.getOrder().getTravelDate());
        VerifyResult tokenResult = verifySsoLogin(coi.getLoginToken());
        if(tokenResult.isStatus() == true){
            System.out.println("[Order Other Service][Verify Login] Success");
            return orderService.create(coi.getOrder());
        }else{
            System.out.println("[Order Other Service][Verify Login] Fail");
            CreateOrderResult cor = new CreateOrderResult();
            cor.setStatus(false);
            cor.setMessage("Not Login");
            cor.setOrder(null);
            return cor;
        }
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/orderOther/query", method = RequestMethod.POST)
    public ArrayList<Order> queryOrders(@RequestBody QueryInfo qi){
        System.out.println("[Order Other Service][Query Orders] Query Orders for " + qi.getAccountId());
        VerifyResult tokenResult = verifySsoLogin(qi.getLoginToken());
        if(tokenResult.isStatus() == true){
            System.out.println("[Order Other Service][Verify Login] Success");
            return orderService.queryOrders(qi);
        }else{
            System.out.println("[Order Other Service][Verify Login] Fail");
            return new ArrayList<Order>();
        }
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path="/orderOther/calculate", method = RequestMethod.POST)
    public CalculateSoldTicketResult calculateSoldTicket(@RequestBody CalculateSoldTicketInfo csti){
        System.out.println("[Order Other Service][Calculate Sold Tickets] Date:" + csti.getTravelDate() + " TrainNumber:"
                + csti.getTrainNumber());
        return orderService.queryAlreadySoldOrders(csti);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path="/orderOther/price", method = RequestMethod.POST)
    public GetOrderPriceResult getOrderPrice(@RequestBody GetOrderPrice info){
        System.out.println("[Order Other Service][Get Order Price] Order Id:" + info.getOrderId());
        return orderService.getOrderPrice(info);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path="/orderOther/payOrder", method = RequestMethod.POST)
    public PayOrderResult payOrder(@RequestBody PayOrderInfo info){
        System.out.println("[Order Other Service][Pay Order] Order Id:" + info.getOrderId());
        return orderService.payOrder(info);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path="/orderOther/getById", method = RequestMethod.POST)
    public GetOrderResult getOrderById(@RequestBody GetOrderByIdInfo info){
        System.out.println("[Order Other Service][Get Order By Id] Order Id:" + info.getOrderId());
        return orderService.getOrderById(info);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path="/orderOther/execute", method = RequestMethod.POST)
    public ExecuteOrderResult executeOrder(@RequestBody ExecuteOrderInfo info){
        System.out.println("[Order Other Service][Ticket Execute] Order Id:" + info.getOrderId());
        return orderService.executeTicket(info);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path="/getOrderOtherInfoForSecurity", method = RequestMethod.POST)
    public GetOrderInfoForSecurityResult securityInfoCheck(@RequestBody GetOrderInfoForSecurity info){
        System.out.println("[Order Other Service][Security Info Get]");
        return orderService.checkSecurityAboutOrder(info);
    }



//    @CrossOrigin(origins = "*")
//    @RequestMapping(path = "/orderOther/alter", method = RequestMethod.POST)
//    public OrderAlterResult alterOrder(@RequestBody OrderAlterInfo oai){
//        VerifyResult vr = verifySsoLogin(oai.getLoginToken());
//        if(vr.isStatus() == true){
//            return orderService.alterOrder(oai);
//        }else{
//            OrderAlterResult oar = new OrderAlterResult();
//            oar.setStatus(false);
//            oar.setMessage("Not Login");
//            oar.setOldOrder(null);
//            oar.setNewOrder(null);
//            return oar;
//        }
//    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/orderOther/update", method = RequestMethod.POST)
    public ChangeOrderResult saveOrderInfo(@RequestBody ChangeOrderInfo orderInfo){
        VerifyResult tokenResult = verifySsoLogin(orderInfo.getLoginToken());
        if(tokenResult.isStatus() == true){
            System.out.println("[Order Other Service][Verify Login] Success");
            return orderService.saveChanges(orderInfo.getOrder());
        }else{
            System.out.println("[Order Other Service][Verify Login] Fail");
            ChangeOrderResult cor = new ChangeOrderResult();
            cor.setStatus(false);
            cor.setMessage("Not Login");
            cor.setOrder(null);
            return cor;
        }
    }

//    @CrossOrigin(origins = "*")
//    @RequestMapping(path="/orderOther/cancel", method = RequestMethod.PUT)
//    public CancelOrderResult cancelOrder(@RequestBody CancelOrderInfo coi){
//        VerifyResult tokenResult = verifySsoLogin(coi.getLoginToken());
//        if(tokenResult.isStatus() == true){
//            System.out.println("[Order Other Service][VerifyLogin] Success");
//            return orderService.cancelOrder(coi);
//        }else{
//            System.out.println("[OrderService][VerifyLogin] Fail");
//            CancelOrderResult cor = new CancelOrderResult();
//            cor.setStatus(false);
//            cor.setMessage("Not Login");
//            cor.setOrder(null);
//            return cor;
//        }
//    }

    /***************For super admin(Single Service Test*******************/

    @CrossOrigin(origins = "*")
    @RequestMapping(path="/orderOther/findAll", method = RequestMethod.GET)
    public QueryOrderResult findAllOrder(){
        System.out.println("[Order Other Service][Find All Order]");
        return orderService.getAllOrders();
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path="/orderOther/modifyOrder", method = RequestMethod.POST)
    public ModifyOrderResult modifyOrder(@RequestBody ModifyOrderInfo info){
        System.out.println("[Order Other Service][Modify Order] Order Id:" + info.getOrderId());
        return orderService.modifyOrder(info);
    }


    private VerifyResult verifySsoLogin(String loginToken){
        restTemplate = new RestTemplate();
        System.out.println("[Order Other Service][Verify Login] Verifying....");
        VerifyResult tokenResult = restTemplate.getForObject(
                "http://ts-sso-service:12349/verifyLoginToken/" + loginToken,
                VerifyResult.class);
        return tokenResult;
    }
}
