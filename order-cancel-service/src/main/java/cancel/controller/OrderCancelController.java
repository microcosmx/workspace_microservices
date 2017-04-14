package cancel.controller;

import cancel.domain.CancelOrderInfo;
import cancel.domain.Order;
import cancel.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderCancelController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(path = "/welcome", method = RequestMethod.GET)
    public String home() {
        return "Welcome to [ Order Cancel Service ] !";
    }

    @RequestMapping(path="/cancelOrder", method = RequestMethod.POST)
    public Order cancelOrder(@RequestBody CancelOrderInfo coi){
        return orderService.cancelOrder(coi);
    }

}
