package order.controller;

import order.domain.CancelOrderInfo;
import order.domain.Order;
import order.domain.OrderAlterInfo;
import order.domain.QueryInfo;
import order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(path = "/welcome", method = RequestMethod.GET)
    public String home() {
        return "Welcome to [ Order Create Service ] !";
    }

    @RequestMapping(path = "/createNewOrders", method = RequestMethod.POST)
    public Order createNewAccount(@RequestBody Order newOrder){
        return orderService.create(newOrder);
    }

    @RequestMapping(path = "/alterOrder", method = RequestMethod.POST)
    public Order alterOrder(@RequestBody OrderAlterInfo oai){
        return orderService.alterOrder(oai);
    }

    @RequestMapping(path = "/queryOrders", method = RequestMethod.POST)
    public ArrayList<Order> queryOrders(@RequestBody QueryInfo qi){
        return orderService.queryOrders(qi);
    }

    @RequestMapping(path = "/saveOrderInfo", method = RequestMethod.PUT)
    public Order saveAccountInfo(@RequestBody Order order){
        return orderService.saveChanges(order);
    }


    @RequestMapping(path="/cancelOrder", method = RequestMethod.POST)
    public Order cancelOrder(@RequestBody CancelOrderInfo coi){
        return orderService.cancelOrder(coi);
    }


}
