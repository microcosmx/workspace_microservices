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

import java.util.Random;
import java.util.concurrent.Future;

@RestController
public class HelloController {
    
    private static final Logger log = LoggerFactory.getLogger(Application.class);
    @Autowired
	private RestTemplate restTemplate;
    
    @Autowired  
    private AsyncTask asyncTask;  

    @RequestMapping("/hello3")
    public Value hello3(@RequestParam(value="cal", defaultValue="50") String cal) throws Exception {

        double cal2 = Math.pow(Double.valueOf(cal), 2)/100; 
        log.info(String.valueOf(cal2));

    	Value value = null;
        if(cal2 < 80){
            value = restTemplate.getForObject("http://rest-service-2:16002/hello2?cal="+cal2, Value.class);
        }else if(cal2 < 100){
            value = restTemplate.getForObject("http://rest-service-1:16001/hello1?cal="+cal2, Value.class);
        }else{
        	// throw new Exception("unexpected input scope");
            value = restTemplate.getForObject("http://rest-service-end:16000/greeting?cal="+cal2, Value.class);
        }
        
		log.info(value.toString());
        
		return value;
    }
    
    
    
    @RequestMapping("/process3_query_1")
    public String process3_query_1(@RequestParam(value="name", defaultValue="p1") String name) {
        
        String result = restTemplate.getForObject("http://rest-service-end:16000/process_end_query?name="+name, String.class);
        
        return result;
    }
    
    @RequestMapping("/process3_query_2")
    public String process3_query_2(@RequestParam(value="name", defaultValue="p1") String name) {

    	String result = restTemplate.getForObject("http://rest-service-end:16000/process_end_query?name="+name, String.class);
        
        return result;
    }
    
    @RequestMapping("/process3_update_1")
    public String process3_update_1(@RequestParam(value="name", defaultValue="p1") String name,
    		@RequestParam(value="priceChange", defaultValue="0.3") double priceChange) {
        
        String result = restTemplate.getForObject("http://rest-service-end:16000/process_end_update?name=p1&priceChange="+priceChange, String.class);
        
        return result;
    }
    
    @RequestMapping("/process3_update_2")
    public String process3_update_2(@RequestParam(value="name", defaultValue="p1") String name,
    		@RequestParam(value="priceChange", defaultValue="0.3") double priceChange) {

    	String result = restTemplate.getForObject("http://rest-service-end:16000/process_end_update?name=p1&priceChange="+priceChange, String.class);
        
        return result;
    }
    
    
}
