package com.trainticket.service;

import org.springframework.stereotype.Service;

/**
 * Created by Chenjie Xu on 2017/4/5.
 */
@Service
public class PayServiceImpl implements PayService{
    @Override
    public boolean pay(Double money){
        return true;
    }
}
