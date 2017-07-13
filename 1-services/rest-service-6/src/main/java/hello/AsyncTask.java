package hello;

import java.util.concurrent.Future;

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
    
    @Async("mySimpleAsync")
    public Future<String> sendAsyncMessage1(String msg) throws InterruptedException{
        String result = restTemplate.getForObject("http://rest-service-5:16005/hello5_1?msg="+msg, String.class);
        return new AsyncResult<>("msg1 sended");  
    }
    @Async("mySimpleAsync")
    public Future<String> sendAsyncMessage2(String msg) throws InterruptedException{
        String result = restTemplate.getForObject("http://rest-service-5:16005/hello5_1?msg="+msg, String.class);
        return new AsyncResult<>("msg2 sended");
    }  
    
    
      
    @Async("myAsync")  
    public Future<String> doAsyncTask1(String msg) throws InterruptedException{  
        String result = restTemplate.getForObject("http://rest-service-5:16005/hello5_2?msg="+msg, String.class);
        return new AsyncResult<>(result);  
    }  
      
    @Async("myAsync")  
    public Future<String> doAsyncTask2(String msg) throws InterruptedException{  
        String result = restTemplate.getForObject("http://rest-service-5:16005/hello5_2?msg="+msg, String.class);
        return new AsyncResult<>(result);  
    }  
}  
