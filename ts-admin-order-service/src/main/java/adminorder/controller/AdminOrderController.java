package adminorder.controller;

import adminorder.domain.request.AddOrderRequest;
import adminorder.domain.response.AddOrderResult;
import adminorder.domain.response.GetAllOrderResult;
import adminorder.service.AdminOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminOrderController {

    @Autowired
    AdminOrderService adminOrderService;

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/adminorder/findAll/{id}", method = RequestMethod.GET)
    public GetAllOrderResult getAllOrders(@PathVariable String id){
        return adminOrderService.getAllOrders(id);
    }

    @RequestMapping(value = "/adminorder/addOrder", method= RequestMethod.POST)
    public AddOrderResult addOrder(@RequestBody AddOrderRequest request){
        return adminOrderService.addOrder(request);
    }

}
