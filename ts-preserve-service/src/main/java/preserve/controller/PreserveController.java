package preserve.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import preserve.service.PreserveService;

/**
 * Created by Chenjie Xu on 2017/5/19.
 */
@RestController
public class PreserveController {
    @Autowired
    private PreserveService preserveService;

    @RequestMapping(value="/preserve", method = RequestMethod.POST)
    public String preserve(){
        return "false";
    }


}
