package reproduction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reproduction.domain.Information;
import reproduction.domain.OrderTicketsInfo;
import reproduction.domain.OrderTicketsResult;
import reproduction.service.ReproductionService;

/**
 * Created by Administrator on 2017/8/7.
 */
@RestController
public class ReproductionController {
    @Autowired
    ReproductionService reproductionService;

    @RequestMapping(value="/reproduction/reproduct", method = RequestMethod.PUT)
    public OrderTicketsResult reproduction(@RequestBody OrderTicketsInfo info, @CookieValue String loginId, @CookieValue String loginToken){
        return reproductionService.reproduct(info,loginId,loginToken);
    }

    @RequestMapping(value="/reproduction/reproductOther", method = RequestMethod.PUT)
    public OrderTicketsResult reproduction(@RequestBody OrderTicketsInfo info, @CookieValue String loginId,@CookieValue String loginToken){
        return reproductionService.reproductOther(info,loginId,loginToken);
    }
}
