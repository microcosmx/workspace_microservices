package inside_payment.async;

import inside_payment.domain.*;
import inside_payment.repository.AddMoneyRepository;
import inside_payment.repository.DrawBackRepository;
import inside_payment.service.InsidePaymentServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Random;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;


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

    @Autowired
    public AddMoneyRepository addMoneyRepository;

    @Autowired
    public DrawBackRepository drawBackRepository;

    @Autowired
    InsidePaymentServiceImpl InsidePaymentServiceImpl;


    @Async("myAsync")
    public Future<ChangeOrderResult> updateOtherOrderStatusToCancel(ChangeOrderInfo info) throws InterruptedException{
        Thread.sleep(2000);
        System.out.println("[Cancel Order Service][Change Order Status] Getting....");
        ChangeOrderResult result = restTemplate.postForObject("http://ts-order-other-service:12032/orderOther/update",info,ChangeOrderResult.class);
        return new AsyncResult<>(result);
    }

    @Async("myAsync")
    public Future<ChangeOrderResult> reCalculateRefundMoney(String orderId, String money, String loginToken) throws InterruptedException{
        ChangeOrderResult changeOrderResult = null;
        CancelOrderInfo info = new CancelOrderInfo();
        info.setOrderId(orderId);
        String result = restTemplate.postForObject("http://ts-cancel-service:18885/cancelCalculateRefund2",info,String.class);

        System.out.println();
        System.out.println("money:"+money);
        System.out.println("result.getRefund():"+result);
        System.out.println();

        if(!result.equals(money)){
            InsidePaymentServiceImpl.equal.set(2);
            Thread.sleep(20000);
            DrawBack drawBack = drawBackRepository.findByOrderId(orderId);

            addMoneyRepository.deleteByUserIdAndMoney(drawBack.getUserId(),money);

            AddMoney addMoney = new AddMoney();
            addMoney.setUserId(drawBack.getUserId());
            addMoney.setMoney(result);
            addMoney.setType(AddMoneyType.D);
            addMoneyRepository.save(addMoney);

            //设置订单状态为已退款
            GetOrderByIdInfo getOrderInfo = new GetOrderByIdInfo();
            getOrderInfo.setOrderId(orderId);
            GetOrderResult cor = restTemplate.postForObject(
                    "http://ts-order-other-service:12032/orderOther/getById"
                    ,getOrderInfo,GetOrderResult.class);
            Order order = cor.getOrder();


            order.setStatus(OrderStatus.CANCEL.getCode());
            ChangeOrderInfo changeOrderInfo = new ChangeOrderInfo();
            changeOrderInfo.setOrder(order);
            changeOrderInfo.setLoginToken(loginToken);
            System.out.println();
            System.out.println("http://ts-order-other-service:12032/orderOther/update before");
            System.out.println();
            changeOrderResult = restTemplate.postForObject("http://ts-order-other-service:12032/orderOther/update",changeOrderInfo,ChangeOrderResult.class);
            System.out.println();
            System.out.println("http://ts-order-other-service:12032/orderOther/update after");
            System.out.println();
        }else{
            InsidePaymentServiceImpl.equal.set(1);

            Thread.sleep(20000);
            GetOrderByIdInfo getOrderInfo = new GetOrderByIdInfo();
            getOrderInfo.setOrderId(orderId);
            GetOrderResult cor = restTemplate.postForObject(
                    "http://ts-order-other-service:12032/orderOther/getById"
                    ,getOrderInfo,GetOrderResult.class);
            Order order = cor.getOrder();
            ChangeOrderInfo changeOrderInfo = new ChangeOrderInfo();
            changeOrderInfo.setOrder(order);
            changeOrderInfo.setLoginToken(loginToken);
            System.out.println();
            System.out.println("http://ts-order-other-service:12032/orderOther/update before");
            System.out.println();
            changeOrderResult = restTemplate.postForObject("http://ts-order-other-service:12032/orderOther/update",changeOrderInfo,ChangeOrderResult.class);
            System.out.println();
            System.out.println("http://ts-order-other-service:12032/orderOther/update after");
            System.out.println();


            changeOrderResult = new ChangeOrderResult();
            changeOrderResult.setStatus(false);

        }
        return new AsyncResult<>(changeOrderResult);
    }

}  
