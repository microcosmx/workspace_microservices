package query.controller;

import query.domain.Order;
import query.domain.QueryInfo;
import query.service.OrderQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

@RestController
public class OrderQueryController {

    @Autowired
    private OrderQueryService orderService;

    @RequestMapping(path = "/welcome", method = RequestMethod.GET)
    public String home() {
        return "Welcome to [ Order Query Service ] !";
    }

    @RequestMapping(path = "/queryOrders", method = RequestMethod.POST)
    public ArrayList<Order> queryOrders(@RequestBody QueryInfo qi){
        return orderService.queryOrders(qi);
    }

}
