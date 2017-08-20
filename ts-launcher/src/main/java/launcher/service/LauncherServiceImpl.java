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
        Future<OrderTicketsResult> taskResult = asyncTask.sendOrderTicket(orderId,loginId,loginToken);
        try{
            OrderTicketsResult orderTicketsResult = taskResult.get();
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            OrderTicketsResult orderTicketsResult = taskResult.get();
        }catch (Exception e){
            e.printStackTrace();
        }

        //2.支付操作
        Future<Boolean> payResult  = asyncTask.sendInsidePayment(
                orderId,"Z1234",loginId,loginToken);

        try{
            if(new Random().nextBoolean() == false){
                System.out.println("[Launcher Service]不等待支付结果直接返回");
                //do nothing, just send continue to reproduce faults.
            }else{
                //do wait until result
                boolean payResultValue = payResult.get().booleanValue();
                System.out.println("[Launcher Service]支付结果：" + payResultValue);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

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
