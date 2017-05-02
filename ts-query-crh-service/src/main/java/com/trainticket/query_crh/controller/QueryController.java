package com.trainticket.query_crh.controller;

import com.trainticket.query_crh.domain.Item;
import com.trainticket.query_crh.service.QueryService;
import com.trainticket.query_crh.service.QueryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.trainticket.query_crh.domain.Information;

import java.util.List;

/**
 * Created by Chenjie Xu on 2017/3/1.
 */

@RestController
public class QueryController {

    @Autowired
    private QueryService queryService;

    @RequestMapping(path = "/querycrh",method = RequestMethod.POST)
    public List<Item> query(@RequestBody Information info){

        return queryService.query(info);
    }

}
