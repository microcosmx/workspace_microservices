package inside_payment.service;

import java.util.concurrent.Future;

import inside_payment.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component  
public class AsyncTask {  
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());  
    
    @Autowired
	private RestTemplate restTemplate;

    @Async("mySimpleAsync")
    public Future<ModifyOrderStatusResult> setOrderStatus(String tripId, String orderId) throws InterruptedException{
        ModifyOrderStatusInfo info = new ModifyOrderStatusInfo();
        info.setOrderId(orderId);
        info.setStatus(1);   //order paid and not collected

        ModifyOrderStatusResult result;
        if(tripId.startsWith("G") || tripId.startsWith("D")){
            result = restTemplate.postForObject(
                    "http://ts-order-service:12031/order/modifyOrderStatus", info, ModifyOrderStatusResult.class);
        }else{
            result = restTemplate.postForObject(
                    "http://ts-order-other-service:12032/orderOther/modifyOrderStatus", info, ModifyOrderStatusResult.class);
        }
        return new AsyncResult<>(result);
    }

    @Async("mySimpleAsync")
    public Future<GetOrderResult> getOrder(PaymentInfo info) throws InterruptedException{
        GetOrderResult result;
        GetOrderByIdInfo getOrderByIdInfo = new GetOrderByIdInfo();
        getOrderByIdInfo.setOrderId(info.getOrderId());

        if(info.getTripId().startsWith("G") || info.getTripId().startsWith("D")){
            result = restTemplate.postForObject("http://ts-order-service:12031/order/getById",getOrderByIdInfo,GetOrderResult.class);
            //result = restTemplate.postForObject(
            //       "http://ts-order-service:12031/order/price", new QueryOrder(info.getOrderId()),QueryOrderResult.class);
        }else{
            result = restTemplate.postForObject("http://ts-order-other-service:12032/orderOther/getById",getOrderByIdInfo,GetOrderResult.class);
            //result = restTemplate.postForObject(
            //      "http://ts-order-other-service:12032/orderOther/price", new QueryOrder(info.getOrderId()),QueryOrderResult.class);
        }
        return new AsyncResult<>(result);
    }
    
}  
