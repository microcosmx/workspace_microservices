package rebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
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

    @RequestMapping(value="/rebook/", method = RequestMethod.POST)
    public RebookResult rebook(@RequestBody RebookInfo info){
        return service.rebook(info);
    }

}
