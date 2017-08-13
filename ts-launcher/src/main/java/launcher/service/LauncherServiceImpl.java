package launcher.service;

import launcher.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Random;
import java.util.UUID;

@Service
public class LauncherServiceImpl implements LauncherService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void doErrorQueue(){
        //1.登录
        LoginInfo loginInfo = new LoginInfo(
                "fdse_microservices@163.com",
                "DefaultPassword",
                "abcd");
        LoginResult loginResult = restTemplate.postForObject(
                "http://ts-login-service:12342/login",
                loginInfo,LoginResult.class
        );
        String loginId = loginResult.getAccount().getId().toString();
        String loginToken = loginResult.getToken();
        System.out.println("[登录结果] " + loginResult.getMessage());

        String orderId = UUID.randomUUID().toString();

        //2.预定并预设id

//        OrderTicketsInfoWithOrderId orderTicketsInfoWithOrderId =
//                new OrderTicketsInfoWithOrderId(
//                        "aded7dc5-06a7-4503-8e21-b7cad7a1f386",
//                        "Z1234",
//                        2,
//                        new Date(1504137600000L),
//                        "Shang Hai",
//                        "Tai Yuan",
//                        orderId);
//        HttpHeaders requestHeadersPreserveOrder = new HttpHeaders();
//        requestHeadersPreserveOrder.add("Cookie","loginId=" + loginId);
//        requestHeadersPreserveOrder.add("Cookie","loginToken=" + loginToken);
//        HttpEntity<CancelOrderInfo> requestEntityPreserveOrder = new HttpEntity(orderTicketsInfoWithOrderId, requestHeadersPreserveOrder);
//        ResponseEntity rssResponsePreserveOrder = restTemplate.exchange(
//                "http://ts-preserve-other-service:14569/preserveOther",
//                HttpMethod.POST, requestEntityPreserveOrder, OrderTicketsResult.class);
//        OrderTicketsResult orderTicketsResult = (OrderTicketsResult)rssResponsePreserveOrder.getBody();
//        System.out.println("[退票结果] " + orderTicketsResult.getMessage());

        try{
            int sleepTime = new Random().nextInt(6000);
            //模拟用户点击延迟
            Thread.sleep(sleepTime);
            System.out.println("[Launcher Service]睡眠时间：" + sleepTime);
        }catch(Exception e){
            e.printStackTrace();
        }

        //3.执行退票操作

//        CancelOrderInfo cancelOrderInfo = new CancelOrderInfo(orderId);
//        HttpHeaders requestHeadersCancelOrder = new HttpHeaders();
//        requestHeadersCancelOrder.add("Cookie","loginId=" + loginId);
//        requestHeadersCancelOrder.add("Cookie","loginToken=" + loginToken);
//        HttpEntity<CancelOrderInfo> requestEntityCancelOrder = new HttpEntity(cancelOrderInfo, requestHeadersCancelOrder);
//        ResponseEntity rssResponseCancelOrder = restTemplate.exchange(
//                "http://ts-preserve-service:14568/preserve",
//                HttpMethod.POST, requestEntityCancelOrder, CancelOrderResult.class);
//        CancelOrderResult cancelOrderResult = (CancelOrderResult) rssResponseCancelOrder.getBody();
//        System.out.println("[退票结果] " + cancelOrderResult.getMessage());
    }

}
