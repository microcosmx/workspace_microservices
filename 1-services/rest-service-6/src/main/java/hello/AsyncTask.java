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
    public Future<String> doAsyncQuery_end() throws InterruptedException{
		String price = restTemplate.getForObject("http://rest-service-end:16000/process_end_query?name=p1", String.class);
        return new AsyncResult<>(price);  
    }
    
    @Async("myAsync")  
    public Future<String> doAsyncUpdate_end(int priceChange) throws InterruptedException{
    	String result = restTemplate.getForObject("http://rest-service-end:16000/process_end_update?name=p1&priceChange="+priceChange, String.class);
        return new AsyncResult<>(result);  
    }
    
    @Async("myAsync")  
    public Future<String> doAsyncUpdate(int priceChange) throws InterruptedException{
    	String result = restTemplate.getForObject("http://rest-service-1:16001/process_end_update?name=p1&priceChange="+priceChange, String.class);
        return new AsyncResult<>(result);  
    }
      
}  
