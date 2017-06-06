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
        try {
			Thread.sleep(10);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
    	logger.info("------hell6-------started-------" + msg);
        String result = restTemplate.getForObject("http://rest-service-3:16003/hello3_1?msg="+msg, String.class);
        return new AsyncResult<>("msg1 sended");  
    }
    @Async("mySimpleAsync")
    public Future<String> sendAsyncMessage2(String msg) throws InterruptedException{
    	try {
			Thread.sleep(60);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
    	logger.info("------hell6-------started-------" + msg);
        String result = restTemplate.getForObject("http://rest-service-3:16003/hello3_1?msg="+msg, String.class);
        return new AsyncResult<>("msg2 sended");
    }  
    
    
      
    @Async("mySimpleAsync")  
    public Future<String> doTask1() throws InterruptedException{  
        logger.info("Task1 started.");  
        long start = System.currentTimeMillis();  
        Thread.sleep(5000);  
        long end = System.currentTimeMillis();  
          
        logger.info("Task1 finished, time elapsed: {} ms.", end-start);  
          
        return new AsyncResult<>("Task1 accomplished!");  
    }  
      
    @Async("myAsync")  
    public Future<String> doTask2() throws InterruptedException{  
        logger.info("Task2 started.");  
        long start = System.currentTimeMillis();  
        Thread.sleep(3000);  
        long end = System.currentTimeMillis();  
          
        logger.info("Task2 finished, time elapsed: {} ms.", end-start);  
          
        return new AsyncResult<>("Task2 accomplished!");  
    }  
}  
