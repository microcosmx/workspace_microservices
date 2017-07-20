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
    
    @Autowired
    private MsgSendingBean sendingBean;
    
    @Async("mySimpleAsync")
    public void sendAsyncUpdate(String val){
    	long sleep = (long) (Math.random() * 300);
		try {
			Thread.sleep(sleep);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	sendingBean.sayHello(Double.valueOf(val));
    }
    
    
      
    @Async("myAsync")  
    public void sendAsyncUpdate1(String val){
    	sendingBean.sayHello(Double.valueOf(val));
    } 
      
}  
