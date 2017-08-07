package reproduce.service;

import reproduce.domain.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

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
        if(loginResult.getStatus() == false){
            System.out.println("[Reproduce Service]自动登录失败");
            return false;
        }
        String accountId = loginResult.getAccount().getId().toString();
        String loginToken = loginResult.getToken();
        System.out.println("[Reproduce Service][Login] Id:" + accountId + " Token:" + loginToken);

        //2.预订一张普通车票
        OrderTicketsInfo orderTicketsInfo = new OrderTicketsInfo("6e68c2f8-4da9-40f0-ac70-92fe3a28fed1",
                "Z1234",
                2,
                new Date(2017,7,30),
                "Shang Hai",
                "Tai Yuan");
        OrderTicketsResult orderTicketsResult = restTemplate.postForObject(
                "http://ts-preserve-other-service:14569/preserveOther",
                orderTicketsInfo,OrderTicketsResult.class);
        if(orderTicketsResult.isStatus() == false){
            System.out.println("[Reproduce Service] 车票预定失败。原因：" + orderTicketsResult.getMessage());
            return false;
        }else{
            System.out.println("[Reproduce Service] 车票预定成功。订单号：" + orderTicketsResult.getOrder().getId().toString());
        }
        //3.支付一张普通车票
        PaymentInfo paymentInfo = new PaymentInfo(orderTicketsResult.getOrder().getId().toString(),
                                                  orderTicketsResult.getOrder().getTrainNumber());
        Boolean result = restTemplate.postForObject(
                "http://ts-inside-payment-service:18673/inside_payment/pay",
                paymentInfo,Boolean.class);
        System.out.println("[Reproduce Service]支付结果:" + result);

        if(result == true){
            return true;
        }else{
            return false;
        }
    }

}
