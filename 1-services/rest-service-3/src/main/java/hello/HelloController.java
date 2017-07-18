package hello;

import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;

import java.util.concurrent.Future;

@RestController
public class HelloController {
    
    private static final Logger log = LoggerFactory.getLogger(Application.class);
    @Autowired
	private RestTemplate restTemplate;
    
    public final AtomicLong counter = new AtomicLong();
    
    @RequestMapping("/hello3")
    public Value hello3(@RequestParam(value="name", defaultValue="service-6") String name, 
    		@RequestParam(value="cal", defaultValue="50") String cal) throws Exception {
    	
    	
    	log.info("-------access-----: " + counter.incrementAndGet());

        double cal2 = Math.pow(Double.valueOf(cal), 2)/100; 
        log.info(String.valueOf(cal2));

        Value value = new Value();
        if("service-6".equals(name)){
        	value = restTemplate.getForObject("http://rest-service-end:16000/greeting?cal="+cal2, Value.class);
        }else if("service-5".equals(name)){
        	value = restTemplate.getForObject("http://rest-service-1:16001/hello1?cal="+cal2, Value.class);
        }else{
        	value = restTemplate.getForObject("http://rest-service-2:16002/hello2?cal="+cal2, Value.class);
        }
        
		log.info(value.toString());
		
		log.info("-------leave-----: " + counter.decrementAndGet());
        
		return value;
    }    
    
}
