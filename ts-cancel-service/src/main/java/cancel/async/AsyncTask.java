package cancel.async;

import java.awt.*;
import java.util.Random;
import java.util.concurrent.Future;
import cancel.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


/** 
 * Asynchronous Tasks 
 * @author Xu 
 * 
 */  
@Component  
public class AsyncTask {  
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());  
    
    @Autowired
	private RestTemplate restTemplate;

    @Async("myAsync")
    public Future<ChangeOrderResult> updateOtherOrderStatusToCancel(ChangeOrderInfo info) throws InterruptedException{
        Thread.sleep(2000);
        System.out.println("[Cancel Order Service][Change Order Status] Getting....");
        ChangeOrderResult result = restTemplate.postForObject("http://ts-order-other-service:12032/orderOther/update",info,ChangeOrderResult.class);
        return new AsyncResult<>(result);
    }

    @Async("mySimpleAsync")
    public Future<Boolean> drawBackMoneyForOrderCan(String money, String userId,String orderId,String loginToken) throws InterruptedException{

        double op = new Random().nextDouble();
        if(op < 0.5){
            System.out.println("[Cancel Order Service] 延迟流程，退票将会错误");
            Thread.sleep(4000);
        }else {
            System.out.println("[Cancel Order Service] 正常流程，退票应该正常");
        }


        //1.第一步，查询订单信息
        System.out.println("[Cancel Order Service][Get Order] Getting....");
        GetOrderByIdInfo getOrderInfo = new GetOrderByIdInfo();
        getOrderInfo.setOrderId(orderId);
        GetOrderResult cor = restTemplate.postForObject(
                "http://ts-order-other-service:12032/orderOther/getById/"
                ,getOrderInfo,GetOrderResult.class);
        Order order = cor.getOrder();
        //2.第二步，将订单状态修改为退款中
        order.setStatus(OrderStatus.Canceling.getCode());
        ChangeOrderInfo changeOrderInfo = new ChangeOrderInfo();
        changeOrderInfo.setOrder(order);
        changeOrderInfo.setLoginToken(loginToken);
        ChangeOrderResult changeOrderResult = restTemplate.postForObject("http://ts-order-other-service:12032/orderOther/update",changeOrderInfo,ChangeOrderResult.class);
        if(changeOrderResult.isStatus() == false){
            System.out.println("[Cancel Order Service]紧急！修改订单状态到取消中-错误");
        }
        //3.第三步，执行退款
        System.out.println("[Cancel Order Service][Draw Back Money] Draw back money...");
        DrawBackInfo info = new DrawBackInfo();
        info.setMoney(money);
        info.setUserId(userId);
        String result = restTemplate.postForObject("http://ts-inside-payment-service:18673/inside_payment/drawBack",info,String.class);
        if(result.equals("true")){
            return new AsyncResult<>(true);
        }else{
            return new AsyncResult<>(false);
        }
    }
      
}  
