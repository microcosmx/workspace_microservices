package launcher.task;

import launcher.domain.CancelOrderInfo;
import launcher.domain.CancelOrderResult;
import launcher.domain.OrderTicketsInfoWithOrderId;
import launcher.domain.OrderTicketsResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.concurrent.Future;

@Component
public class AsyncTask {

    @Autowired
    private RestTemplate restTemplate;

    @Async("mySimpleAsync")
    public Future<OrderTicketsResult> sendOrderTicket(String orderId, String loginId, String loginToken){
        OrderTicketsInfoWithOrderId orderTicketsInfoWithOrderId =
                new OrderTicketsInfoWithOrderId(
                        "aded7dc5-06a7-4503-8e21-b7cad7a1f386",
                        "Z1234",
                        2,
                        new Date(1504137600000L),
                        "Shang Hai",
                        "Tai Yuan",
                        orderId);
        HttpHeaders requestHeadersPreserveOrder = new HttpHeaders();
        requestHeadersPreserveOrder.add("Cookie","loginId=" + loginId);
        requestHeadersPreserveOrder.add("Cookie","loginToken=" + loginToken);
        HttpEntity<CancelOrderInfo> requestEntityPreserveOrder = new HttpEntity(orderTicketsInfoWithOrderId, requestHeadersPreserveOrder);
        ResponseEntity rssResponsePreserveOrder = restTemplate.exchange(
                "http://ts-preserve-other-service:14569/preserveOther",
                HttpMethod.POST, requestEntityPreserveOrder, OrderTicketsResult.class);
        OrderTicketsResult orderTicketsResult = (OrderTicketsResult)rssResponsePreserveOrder.getBody();
        System.out.println("[预定结果] " + orderTicketsResult.getMessage());
        return new AsyncResult(orderTicketsResult);
    }

    @Async("mySimpleAsync")
    public Future<CancelOrderResult> sendOrderCancel(String orderId,String loginId,String loginToken){
        CancelOrderInfo cancelOrderInfo = new CancelOrderInfo(orderId);
        HttpHeaders requestHeadersCancelOrder = new HttpHeaders();
        requestHeadersCancelOrder.add("Cookie","loginId=" + loginId);
        requestHeadersCancelOrder.add("Cookie","loginToken=" + loginToken);
        HttpEntity<CancelOrderInfo> requestEntityCancelOrder = new HttpEntity(cancelOrderInfo, requestHeadersCancelOrder);
        ResponseEntity rssResponseCancelOrder = restTemplate.exchange(
                "http://ts-cancel-service:18885/cancelOrder",
                HttpMethod.POST, requestEntityCancelOrder, CancelOrderResult.class);
        CancelOrderResult cancelOrderResult = (CancelOrderResult) rssResponseCancelOrder.getBody();
        System.out.println("[退票结果] " + cancelOrderResult.getMessage());
        return new AsyncResult(cancelOrderResult);
    }




}
