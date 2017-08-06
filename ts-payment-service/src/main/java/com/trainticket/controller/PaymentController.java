package com.trainticket.controller;

import com.trainticket.domain.AddMoneyInfo;
import com.trainticket.domain.Payment;
import com.trainticket.domain.PaymentInfo;
import com.trainticket.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Created by Chenjie Xu on 2017/4/7.
 */
@RestController
public class PaymentController {
    @Autowired
    PaymentService service;

    @RequestMapping(path="/payment/pay",method= RequestMethod.POST)
    public boolean pay(@RequestBody PaymentInfo info){

        /****1.设定超时 一半概率超时***/
        double random = Math.random();
        if(random > 0.5){
            System.out.println("[Payment Service] 超时分支");
            try{
                Thread.sleep(3000);
                return service.pay(info);
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }else{
            System.out.println("[Payment Service] 正常分支");
            return service.pay(info);
        }

        //return service.pay(info);

    }

    @RequestMapping(path="/payment/addMoney",method= RequestMethod.POST)
    public boolean addMoney(@RequestBody AddMoneyInfo info){
        return service.addMoney(info);
    }

    @RequestMapping(path="/payment/query",method= RequestMethod.GET)
    public List<Payment> query(){
        return service.query();
    }
}
