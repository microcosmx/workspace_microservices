package launcher.service;

import launcher.domain.*;
import launcher.task.AsyncTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Future;

@Service
public class LauncherServiceImpl implements LauncherService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AsyncTask asyncTask;

    public static int count = 0;

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
        Future<OrderTicketsResult> taskResult = asyncTask.sendOrderTicket(orderId,loginId,loginToken);

        if(count % 2 == 0){
            try{
                System.out.println("[等待中] count=" + count);
                OrderTicketsResult orderTicketsResult = taskResult.get();
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            System.out.println("[不等待中] count=" + count);
        }
        count++;

        //        try{
//            int sleepTime;
//            System.out.println("[Launcher Service] Count:" + count);
//            if(count % 2 == 0){
//                sleepTime = 6000;
//                System.out.println("[Launcher Service] Count:" + count + " sleep:" + sleepTime);
//
//                count++;
//            }else{
//                sleepTime = 0;
//                System.out.println("[Launcher Service] Count:" + count + " sleep:" + sleepTime);
//
//                count++;
//            }
//            //模拟用户点击延迟
//            Thread.sleep(sleepTime);
//            System.out.println("[Launcher Service]睡眠时间：" + sleepTime);
//        }catch(Exception e){
//            e.printStackTrace();
//        }

        //3.执行退票操作
        Future<CancelOrderResult> taskCancelResult = asyncTask.sendOrderCancel(orderId,loginId,loginToken);
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
