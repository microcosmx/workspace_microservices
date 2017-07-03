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
    public Future<String> sendAsyncUpdate1(String oldName, String newName) throws InterruptedException{
        String result = restTemplate.getForObject("http://rest-service-5:16005/hello5_1?oldName="+oldName+"&newName="+newName, String.class);
        return new AsyncResult<>("update1 sended");
    }
    @Async("mySimpleAsync")
    public Future<String> sendAsyncUpdate2(String oldName, String newName) throws InterruptedException{
        String result = restTemplate.getForObject("http://rest-service-5:16005/hello5_1?oldName="+oldName+"&newName="+newName, String.class);
        return new AsyncResult<>("update2 sended");
    }  
    
    
      
    @Async("myAsync")  
    public Future<String> doAsyncQuery(String lastName) throws InterruptedException{  
        String result = restTemplate.getForObject("http://rest-service-5:16005/hello5_2?lastName="+lastName, String.class);
        return new AsyncResult<>(result);  
    }  
      
}  
