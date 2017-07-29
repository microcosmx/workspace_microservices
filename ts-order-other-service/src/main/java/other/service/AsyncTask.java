package other.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import other.domain.ModifyOrderInfo;

import java.util.concurrent.Future;


@Component  
public class AsyncTask {  
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());  
    
    @Autowired
	private RestTemplate restTemplate;
    
    @Async("mySimpleAsync")
    public Future<Boolean> queryPayment(String accountId) throws InterruptedException{
        System.out.println("queryPayment");
        ModifyOrderInfo info = new ModifyOrderInfo();
        info.setAccountId(accountId);
        Boolean result = restTemplate.postForObject(
                "http://ts-inside-payment-service:18673/inside_payment/queryModifyOrder", info, Boolean.class);
        return new AsyncResult<>(result);
    }
    
}  
