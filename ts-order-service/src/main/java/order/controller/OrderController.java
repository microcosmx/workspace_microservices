package order.controller;

import order.domain.*;
import order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    private RestTemplate restTemplate;

    @RequestMapping(path = "/welcome", method = RequestMethod.GET)
    public String home() {
        return "Welcome to [ Order Service ] !";
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/order/create", method = RequestMethod.POST)
    public CreateOrderResult createNewOrder(@RequestBody CreateOrderInfo coi){
        System.out.println("[Order Service][Create Order] Create Order form " + coi.getOrder().getFrom() + " --->"
            + coi.getOrder().getTo() + " at " + coi.getOrder().getTravelDate());
        VerifyResult tokenResult = verifySsoLogin(coi.getLoginToken());
        if(tokenResult.isStatus() == true){
            System.out.println("[Order Service][Verify Login] Success");
            return orderService.create(coi.getOrder());
        }else{
            System.out.println("[Order Service][Verify Login] Fail");
            CreateOrderResult cor = new CreateOrderResult();
            cor.setStatus(false);
            cor.setMessage("Not Login");
            cor.setOrder(null);
            return cor;
        }
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/order/query", method = RequestMethod.POST)
    public ArrayList<Order> queryOrders(@RequestBody QueryInfo qi){
        System.out.println("[Order Service][Query Orders] Query Orders for " + qi.getAccountId());
        VerifyResult tokenResult = verifySsoLogin(qi.getLoginToken());
        if(tokenResult.isStatus() == true){
            System.out.println("[Order Service][Verify Login] Success");
            return orderService.queryOrders(qi);
        }else{
            System.out.println("[Order Service][Verify Login] Fail");
            return new ArrayList<Order>();
        }
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path="/order/calculate", method = RequestMethod.POST)
    public CalculateSoldTicketResult calculateSoldTicket(@RequestBody CalculateSoldTicketInfo csti){
        System.out.println("[Order Service][Calculate Sold Tickets] Date:" + csti.getTravelDate() + " TrainNumber:"
                + csti.getTrainNumber());
        return orderService.queryAlreadySoldOrders(csti);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/order/alter", method = RequestMethod.POST)
    public OrderAlterResult alterOrder(@RequestBody OrderAlterInfo oai){
        VerifyResult vr = verifySsoLogin(oai.getLoginToken());
        if(vr.isStatus() == true){
            return orderService.alterOrder(oai);
        }else{
            OrderAlterResult oar = new OrderAlterResult();
            oar.setStatus(false);
            oar.setMessage("Not Login");
            oar.setOldOrder(null);
            oar.setNewOrder(null);
            return oar;
        }
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/order/update", method = RequestMethod.PUT)
    public ChangeOrderResult saveOrderInfo(@RequestBody ChangeOrderInfo orderInfo){
        VerifyResult tokenResult = verifySsoLogin(orderInfo.getLoginToken());
        if(tokenResult.isStatus() == true){
            System.out.println("[Order Service][Verify Login] Success");
            return orderService.saveChanges(orderInfo.getOrder());
        }else{
            System.out.println("[Order Service][Verify Login] Fail");
            ChangeOrderResult cor = new ChangeOrderResult();
            cor.setStatus(false);
            cor.setMessage("Not Login");
            cor.setOrder(null);
            return cor;
        }
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path="/order/cancel", method = RequestMethod.PUT)
    public CancelOrderResult cancelOrder(@RequestBody CancelOrderInfo coi){
        VerifyResult tokenResult = verifySsoLogin(coi.getLoginToken());
        if(tokenResult.isStatus() == true){
            System.out.println("[OrderService][VerifyLogin] Success");
            return orderService.cancelOrder(coi);
        }else{
            System.out.println("[OrderService][VerifyLogin] Fail");
            CancelOrderResult cor = new CancelOrderResult();
            cor.setStatus(false);
            cor.setMessage("Not Login");
            cor.setOrder(null);
            return cor;
        }
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path="/order/findAll", method = RequestMethod.GET)
    public QueryOrderResult findAllOrder(){
        System.out.println("[Order Service][Find All Order]");
        return orderService.getAllOrders();
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path="/order/modifyOrder", method = RequestMethod.POST)
    public ModifyOrderResult modifyOrder(@RequestBody ModifyOrderInfo info){
        System.out.println("[Order Service][Modify Order] Order Id:" + info.getOrderId());
        return orderService.modifyOrder(info);
    }


    private VerifyResult verifySsoLogin(String loginToken){
        restTemplate = new RestTemplate();
        System.out.println("[Order Service][Verify Login] Verifying....");
        VerifyResult tokenResult = restTemplate.getForObject(
                "http://ts-sso-service:12349/verifyLoginToken/" + loginToken,
                VerifyResult.class);
        return tokenResult;
    }

}
