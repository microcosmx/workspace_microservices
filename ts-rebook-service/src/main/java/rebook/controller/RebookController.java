package rebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rebook.domain.RebookInfo;
import rebook.domain.RebookResult;
import rebook.service.RebookService;

/**
 * Created by Administrator on 2017/6/26.
 */
@RestController
public class RebookController {

    @Autowired
    RebookService service;

    @RequestMapping(value="/rebook/payDifference", method = RequestMethod.POST)
    public RebookResult payDifference(@RequestBody RebookInfo info, @CookieValue String loginId, @CookieValue String loginToken){
        return service.payDifference(info, loginId, loginToken);
    }

    @RequestMapping(value="/rebook/rebook", method = RequestMethod.POST)
    public RebookResult rebook(@RequestBody RebookInfo info, @CookieValue String loginId, @CookieValue String loginToken){
        return service.rebook(info, loginId, loginToken);
    }
}
