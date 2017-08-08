package reproduction.async;

import java.util.List;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import reproduction.domain.*;


@Component  
public class AsyncTask {  
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());  
    
    @Autowired
	private RestTemplate restTemplate;

    @Async("mySimpleAsync")
    public Future<OrderTicketsResult> preserve(OrderTicketsInfo info, String loginId, String loginToken){
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie","loginId=" + loginId);
        requestHeaders.add("Cookie","loginToken=" + loginToken);
        HttpEntity<OrderTicketsInfo> requestEntity = new HttpEntity(info, requestHeaders);

        ResponseEntity rssResponse = restTemplate.exchange("http://ts-preserve-service:14568/preserve", HttpMethod.POST, requestEntity, OrderTicketsResult.class);
        OrderTicketsResult result = (OrderTicketsResult)rssResponse.getBody();
        return new AsyncResult<>(result);
    }

    @Async("mySimpleAsync")
    public Future<List<Order>> refreshOrder(String loginId, String loginToken){

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie","loginId=" + loginId);
        requestHeaders.add("Cookie","loginToken=" + loginToken);
        QueryInfo info = new QueryInfo();
        HttpEntity<QueryInfo> requestEntity = new HttpEntity(info, requestHeaders);

        ResponseEntity rssResponse = restTemplate.exchange("http://ts-order-service:12031/order/query", HttpMethod.POST, requestEntity, List.class);
        List<Order> result = (List)rssResponse.getBody();
        return new AsyncResult<>(result);
    }

    @Async("mySimpleAsync")
    public Future<List<Order>> refreshOrderOther(String loginId, String loginToken){
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie","loginId=" + loginId);
        requestHeaders.add("Cookie","loginToken=" + loginToken);
        QueryInfo info = new QueryInfo();
        HttpEntity<QueryInfo> requestEntity = new HttpEntity(info, requestHeaders);

        ResponseEntity rssResponse = restTemplate.exchange("http://ts-order-other-service:12032/orderOther/query", HttpMethod.POST, requestEntity, List.class);
        List<Order> result = (List)rssResponse.getBody();

        return new AsyncResult<>(result);
    }

    @Async("mySimpleAsync")
    public Future<Boolean> pay(String loginId, String loginToken) throws InterruptedException{

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie","loginId=" + loginId);
        requestHeaders.add("Cookie","loginToken=" + loginToken);
        PaymentInfo info = new PaymentInfo();
        info.setOrderId("5ad7750b-a68b-49c0-a8c0-32776b067703");
        info.setTripId("G1237");
        HttpEntity<QueryInfo> requestEntity = new HttpEntity(info, requestHeaders);

        ResponseEntity rssResponse = restTemplate.exchange("http://ts-inside-payment-service:18673/inside_payment/pay", HttpMethod.POST, requestEntity, Boolean.class);
        Boolean result = (Boolean)rssResponse.getBody();

        return new AsyncResult<>(result);
    }
    
}  
