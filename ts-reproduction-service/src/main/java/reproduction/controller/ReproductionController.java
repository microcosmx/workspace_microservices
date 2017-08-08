package reproduction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reproduction.domain.Information;
import reproduction.domain.OrderTicketsInfo;
import reproduction.domain.OrderTicketsResult;
import reproduction.service.ReproductionService;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Administrator on 2017/8/7.
 */
@RestController
public class ReproductionController {
    @Autowired
    ReproductionService reproductionService;

    @RequestMapping(value="/reproduction/reproduct", method = RequestMethod.POST)
    public OrderTicketsResult reproduction(@RequestBody OrderTicketsInfo info, @CookieValue String loginId, @CookieValue String loginToken) throws InterruptedException, ExecutionException, TimeoutException {
        return reproductionService.reproduct(info,loginId,loginToken);
    }

    @RequestMapping(value="/reproduction/reproductOther", method = RequestMethod.POST)
    public OrderTicketsResult reproductionOther(@RequestBody OrderTicketsInfo info, @CookieValue String loginId,@CookieValue String loginToken)throws InterruptedException, ExecutionException, TimeoutException {
        return reproductionService.reproductOther(info,loginId,loginToken);
    }

    @RequestMapping(value="/reproduction/reproductCorrect", method = RequestMethod.POST)
    public OrderTicketsResult reproductionCorrect(@RequestBody OrderTicketsInfo info, @CookieValue String loginId, @CookieValue String loginToken) throws InterruptedException, ExecutionException, TimeoutException {
        return reproductionService.reproductCorrect(info,loginId,loginToken);
    }

    @RequestMapping(value="/reproduction/reproductOtherCorrect", method = RequestMethod.POST)
    public OrderTicketsResult reproductionOtherCorrect(@RequestBody OrderTicketsInfo info, @CookieValue String loginId,@CookieValue String loginToken)throws InterruptedException, ExecutionException, TimeoutException {
        return reproductionService.reproductOtherCorrect(info,loginId,loginToken);
    }
}
