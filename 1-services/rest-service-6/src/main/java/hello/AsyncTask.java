package hello;

import java.util.concurrent.Future;

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
    public Future<Value> sendAsyncCal(double cal2) throws InterruptedException{
    	Value value = restTemplate.getForObject("http://rest-service-6:16006/hello6", Value.class);
        return new AsyncResult<>(value);  
    }
    
}  
