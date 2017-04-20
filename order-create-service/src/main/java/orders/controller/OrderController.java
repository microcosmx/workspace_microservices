package orders.controller;

import orders.domain.Order;
import orders.service.OrderService;
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

    @RequestMapping(path = "/createNewOrders", method = RequestMethod.POST)
    public Order createNewAccount(@RequestBody Order newOrder){
        return orderService.create(newOrder);
    }


}
