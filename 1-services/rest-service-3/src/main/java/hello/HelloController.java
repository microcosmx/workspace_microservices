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
    
    private final RestbackService restbackService;

    public HelloController(RestbackService restbackService) {
        this.restbackService = restbackService;
    }

    @RequestMapping("/hello3")
    public Value hello3(@RequestParam(value="cal", defaultValue="50") String cal) throws Exception {

        double cal2 = Math.pow(Double.valueOf(cal), 2)/100; 
        log.info(String.valueOf(cal2));
        
    	Value value = null;
        if(cal2 < 80){
            value = restTemplate.getForObject("http://rest-service-2:16002/hello2?cal="+cal2, Value.class);
        }else if(cal2 < 100){
        	long start = System.currentTimeMillis();
    		try {
    			Future<Boolean> rest_callback = restbackService.callbackResult(cal2);
    		} catch (InterruptedException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		log.info("Elapsed time: " + (System.currentTimeMillis() - start));
            value = restTemplate.getForObject("http://rest-service-1:16001/hello1?cal="+cal2, Value.class);
        }else{
        	throw new Exception("unexpected input scope");
//            value = restTemplate.getForObject("http://rest-service-end:16000/greeting?cal="+cal2, Value.class);
        }
        
        Value value2 = restTemplate.getForObject("http://rest-service-2:16002/hello2?cal="+cal, Value.class);
        Value value1 = restTemplate.getForObject("http://rest-service-1:16001/hello1?cal="+cal, Value.class);
        
		log.info(value.toString());
        
		return value;
    }
    
    
}
