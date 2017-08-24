package launcher.service;

import launcher.domain.*;
import launcher.task.AsyncTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
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
    public void doErrorQueue(String email,String password){
        //0.注册
        RegisterInfo registerInfo = new RegisterInfo(email,password);
        RegisterResult registerResult = restTemplate.postForObject(
                "http://ts-register-service:12344/register",
                registerInfo,RegisterResult.class);
        System.out.println("[注册结果] " + registerResult.getMessage());

        //0.1 随机多注册一个或者两个用户
        if(new Random().nextBoolean()){
            String randomEmailOne = new Random().nextInt(10000000) + "@fudan.edu.cn";
            RegisterInfo registerInfoExtraOne = new RegisterInfo(randomEmailOne,"passwordpassword");
            RegisterResult registerResultExtraOne = restTemplate.postForObject(
                    "http://ts-register-service:12344/register",
                    registerInfoExtraOne,RegisterResult.class);
            System.out.println("[随机多注册第1个账户]" + registerResultExtraOne.getMessage());
            if(new Random().nextBoolean()){
                String randomEmailTwo = new Random().nextInt(10000000) + "@fudan.edu.cn";
                RegisterInfo registerInfoExtraTwo = new RegisterInfo(randomEmailTwo,"passwordpassword");
                RegisterResult registerResultExtraTwo = restTemplate.postForObject(
                        "http://ts-register-service:12344/register",
                        registerInfoExtraTwo,RegisterResult.class);
                System.out.println("[随机多注册第2个账户]" + registerResultExtraTwo.getMessage());
            }
        }

        //1.登录
        LoginInfo loginInfo = new LoginInfo(
                email,
                password,
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

        //2.支付操作
        Future<Boolean> payResult  = asyncTask.sendInsidePayment(
                orderId,"Z1234",loginId,loginToken);

        boolean isFault;//error queue是否重现
        try{
            if(new Random().nextBoolean() == false){
                System.out.println("[Launcher Service]不等待支付结果直接返回");
                isFault = true;
                //inside-payment-service的pay方法有固定三秒的延迟
                //do nothing, just send continue to reproduce faults.
            }else{
                //do wait until result
                boolean payResultValue = payResult.get().booleanValue();
                System.out.println("[Launcher Service]支付结果：" + payResultValue);
                isFault = false;
            }
        }catch(Exception e){
            isFault = true;
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

        //4.随机查询两次订单什么的
        Future<ArrayList<Order>> orderListTask = asyncTask.sendQueryOrder(loginId,loginToken);
        Future<ArrayList<Order>> orderOtherListTask = asyncTask.sendQueryOtherOrder(loginId,loginToken);
        for(;;){
            //等上边俩完了
            if(orderListTask.isDone() && orderOtherListTask.isDone()){
                break;
            }
        }


        //5.最终决定要不要抛出异常
        for(;;){
            if(taskCancelResult.isDone()){
                if(isFault  == true){
                    throw new RuntimeException("[Error Queue]");
                }else{
                    return;
                }
            }
        }
    }

}
