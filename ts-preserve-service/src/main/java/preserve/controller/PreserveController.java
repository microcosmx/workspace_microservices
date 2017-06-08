package preserve.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import preserve.domain.*;
import preserve.service.PreserveService;

@RestController
public class PreserveController {

    @Autowired
    private PreserveService preserveService;

    @CrossOrigin(origins = "*")
    @RequestMapping(value="/preserve", method = RequestMethod.POST)
    public OrderTicketsResult preserve(@RequestBody OrderTicketsInfo oti){
        System.out.println("[Preserve Service][Preserve] Account " + oti.getAccountId() + " order from " +
            oti.getFrom() + " -----> " + oti.getTo() + " at " + oti.getDate());
        return preserveService.preserve(oti);
    }

}
