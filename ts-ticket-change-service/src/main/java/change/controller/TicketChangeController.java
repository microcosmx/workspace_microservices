package change.controller;

import change.domain.OrderCancelInfo;
import change.domain.OrderCancelResult;
import change.domain.OrderChangeResult;
import org.springframework.web.bind.annotation.*;

@RestController
public class TicketChangeController {

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/orderChange/cancelOrder", method = RequestMethod.POST)
    public OrderCancelResult cancelOrder(@RequestBody OrderCancelInfo orderCancelInfo){
        return null;
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/orderChange/cancelChange", method = RequestMethod.POST)
    public OrderChangeResult changeOrder(@RequestBody OrderCancelResult orderCancelResult){
        return null;
    }

}
