package order.controller;

import order.domain.*;
import order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
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

    @RequestMapping(path = "/createNewOrders", method = RequestMethod.POST)
    public CreateOrderResult createNewOrder(@RequestBody CreateOrderInfo coi){
        String loginToken = coi.getLoginToken();
        restTemplate = new RestTemplate();
        VerifyResult tokenResult = restTemplate.getForObject("http://ts-sso-service:12349/verifyLoginToken/" + loginToken,VerifyResult.class);
        if(tokenResult.isStatus() == true){
            return orderService.create(coi.getOrder());
        }else{
            CreateOrderResult cor = new CreateOrderResult();
            cor.setStatus(false);
            cor.setMessage("Not Login");
            cor.setOrder(null);
            return cor;
        }
    }

    @RequestMapping(path = "/alterOrder", method = RequestMethod.POST)
    public Order alterOrder(@RequestBody OrderAlterInfo oai){
        return orderService.alterOrder(oai);
    }

    @RequestMapping(path = "/queryOrders", method = RequestMethod.POST)
    public ArrayList<Order> queryOrders(@RequestBody QueryInfo qi){
        String loginToken = qi.getLoginToken();
        restTemplate = new RestTemplate();
        VerifyResult tokenResult = restTemplate.getForObject("http://ts-sso-service:12349/verifyLoginToken/" + loginToken,VerifyResult.class);
        if(tokenResult.isStatus() == true){
            return orderService.queryOrders(qi);
        }else{
            return new ArrayList<Order>();
        }
    }

    @RequestMapping(path = "/saveOrderInfo", method = RequestMethod.PUT)
    public ChangeOrderResult saveOrderInfo(@RequestBody ChangeOrderInfo orderInfo){
        String loginToken = orderInfo.getLoginToken();
        restTemplate = new RestTemplate();
        VerifyResult tokenResult = restTemplate.getForObject("http://ts-sso-service:12349/verifyLoginToken/" + loginToken,VerifyResult.class);
        if(tokenResult.isStatus() == true){
            return orderService.saveChanges(orderInfo.getOrder());
        }else{
            ChangeOrderResult cor = new ChangeOrderResult();
            cor.setStatus(false);
            cor.setMessage("Not Login");
            cor.setOrder(null);
            return cor;
        }

    }

    @RequestMapping(path="/cancelOrder", method = RequestMethod.POST)
    public CancelOrderResult cancelOrder(@RequestBody CancelOrderInfo coi){
        String loginToken = coi.getLoginToken();
        restTemplate = new RestTemplate();
        VerifyResult tokenResult = restTemplate.getForObject("http://ts-sso-service:12349/verifyLoginToken/" + loginToken,VerifyResult.class);
        if(tokenResult.isStatus() == true){
            return orderService.cancelOrder(coi);
        }else{
            CancelOrderResult cor = new CancelOrderResult();
            cor.setStatus(false);
            cor.setMessage("Not Login");
            cor.setOrder(null);
            return cor;
        }
    }

}
