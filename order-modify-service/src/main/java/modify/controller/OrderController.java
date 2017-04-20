package modify.controller;

import modify.domain.Order;
import modify.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(path = "/welcome", method = RequestMethod.GET)
    public String home() {
        return "Welcome to [ Order Service ] !";
    }

    @RequestMapping(path = "/saveOrderInfo", method = RequestMethod.PUT)
    public Order saveAccountInfo(@RequestBody Order order){
        return orderService.saveChanges(order);
    }

}
