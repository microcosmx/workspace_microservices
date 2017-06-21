package inside_payment.controller;

import inside_payment.domain.AddMoneyInfo;
import inside_payment.domain.CreateAccountInfo;
import inside_payment.domain.PaymentInfo;
import inside_payment.service.InsidePaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2017/6/20.
 */
@RestController
public class InsidePaymentController {
    @Autowired
    InsidePaymentService service;

    @RequestMapping(value="inside_payment/pay", method = RequestMethod.POST)
    public boolean pay(@RequestBody PaymentInfo info){
        return service.pay(info);
    }

    @RequestMapping(value="inside_payment/createAccount", method = RequestMethod.POST)
    public boolean createAccount(@RequestBody CreateAccountInfo info){
        return service.createAccount(info);
    }

    @RequestMapping(value="inside_payment/addMoney", method = RequestMethod.POST)
    public boolean addMoney(@RequestBody AddMoneyInfo info){
        return service.addMoney(info);
    }
}
