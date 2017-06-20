package preserveOther.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import preserveOther.domain.OrderTicketsInfo;
import preserveOther.domain.OrderTicketsResult;
import preserveOther.service.PreserveOtherService;

@RestController
public class PreserveOtherController {

    @Autowired
    private PreserveOtherService preserveService;

    @CrossOrigin(origins = "*")
    @RequestMapping(value="/preserveOther", method = RequestMethod.POST)
    public OrderTicketsResult preserve(@RequestBody OrderTicketsInfo oti){
        System.out.println("[Preserve Other Service][Preserve] Account " + oti.getAccountId() + " order from " +
                oti.getFrom() + " -----> " + oti.getTo() + " at " + oti.getDate());
        return preserveService.preserve(oti);
    }
}
