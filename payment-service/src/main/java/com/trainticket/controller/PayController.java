package com.trainticket.controller;

import com.trainticket.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Chenjie Xu on 2017/4/7.
 */
@RestController
public class PayController {
    @Autowired
    PayService payService;

    @RequestMapping(path="pay",method= RequestMethod.POST)
    public boolean pay(Double money){
        return payService.pay(money);
    }

}
