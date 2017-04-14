package alter.controller;

import alter.domain.Order;
import alter.domain.OrderAlterInfo;
import alter.service.OrderAlterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderAlterController {

    @Autowired
    private OrderAlterService orderService;

    @RequestMapping(path = "/welcome", method = RequestMethod.GET)
    public String home() {
        return "Welcome to [ Order Service ] !";
    }

    @RequestMapping(path = "/alterOrder", method = RequestMethod.POST)
    public Order alterOrder(@RequestBody OrderAlterInfo oai){
        return orderService.alterOrder(oai);
    }

}
