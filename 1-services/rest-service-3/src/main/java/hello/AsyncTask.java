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
    
    @Async
    public Future<String> callbackAsyncMessage1(String msg) throws InterruptedException{
    	String result = restTemplate.getForObject("http://rest-service-6:16006/hello6_1?msg="+msg, String.class);
        return new AsyncResult<>(result);
    }
    
    @Async
    public Future<String> callbackAsyncMessage2(String msg) throws InterruptedException{
    	String result = restTemplate.getForObject("http://rest-service-6:16006/hello6_2?msg="+msg, String.class);
        return new AsyncResult<>(result);
    }
    
    
}  
