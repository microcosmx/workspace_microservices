package service;

import domain.LoginInfo;
import domain.LoginResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by fdse-jichao on 2017/8/6.
 */

@Service
public class ReproduceServiceImpl implements ReproduceService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public boolean testErrorNormal(){
        //1.登录
        LoginInfo loginInfo = new LoginInfo("fdse_microservices@163.com",
                                            "DefaultPassword",
                                            "admin");
        LoginResult loginResult = restTemplate.postForObject(
                             "http://ts-login-service:12342/login",
                             loginInfo,LoginResult.class);
        String accountId = loginResult.getAccount().getId().toString();
        String loginToken = loginResult.getToken();
        System.out.println("[Reproduce Service][Login] Id:" + accountId + " Token:" + loginToken);
        //2.预订一张普通车票
//        OrderTicketsInfo orderTicketsInfo = new OrderTicketsInfo("contacts",
//                                                                     "",
//                                                                 5,new Date(),
//                                                                 "from",
//                                                                 "to");



        //3.支付一张普通车票


        return false;
    }

}
