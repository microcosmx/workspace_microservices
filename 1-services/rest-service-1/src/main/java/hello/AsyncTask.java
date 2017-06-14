package hello;

import java.util.concurrent.Future;

import javax.servlet.http.HttpSession;

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
import org.springframework.web.client.RestTemplate;


@Component  
public class AsyncTask {  
    protected final Logger log = LoggerFactory.getLogger(this.getClass());  
    
    @Autowired
	private RestTemplate restTemplate;
    
    @Async("mySimpleAsync")
    public Future<Value> sendAsyncCal(HttpSession session, double cal2) throws InterruptedException{
    	
    	HttpHeaders headers = new HttpHeaders();
		headers.add("Cookie", "SESSION=" + session.getId());
		ResponseEntity<Value> exchange = restTemplate.exchange("http://rest-service-end:16000/greeting?cal="+cal2, 
				HttpMethod.GET,new HttpEntity<Void>(headers), Value.class);
		Value value = exchange.getBody();
		
        return new AsyncResult<>(value);
    }
    
}  
