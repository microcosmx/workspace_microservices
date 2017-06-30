package hello;

import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class HelloController {
    
    private static final Logger log = LoggerFactory.getLogger(Application.class);
    @Autowired
	private RestTemplate restTemplate;

    @RequestMapping("/hello5")
    public Value hello5(@RequestParam(value="cal", defaultValue="50") String cal) {

        double cal2 = Math.abs(Double.valueOf(cal)%100+3)/1.03; 
        log.info(String.valueOf(cal2));
        
        Value value = restTemplate.getForObject("http://rest-service-4:16004/hello4?cal="+cal2, Value.class);
		
        log.info(value.toString());
		return value;
    }
    
    
    
    @RequestMapping("/handle5_1")
    public Value handle5_1(@RequestParam(value="cal", defaultValue="50") String cal) {

        double cal2 = Math.abs(Double.valueOf(cal)%100+1)/1.01; 
        log.info(String.valueOf(cal2));
        
        Value value = null;
        if(cal2 < 50){
        	value = restTemplate.getForObject("http://rest-service-4:16004/handle4_1?cal="+cal2, Value.class);
        }else{
        	value = restTemplate.getForObject("http://rest-service-4:16004/handle4_2?cal="+cal2, Value.class);
        }
		
        log.info(value.toString());
		return value;
    }
    
    @RequestMapping("/handle5_2")
    public Value handle5_2(@RequestParam(value="cal", defaultValue="50") String cal) {

        double cal2 = Math.abs(Double.valueOf(cal)%100+2)/1.02; 
        log.info(String.valueOf(cal2));
        
        Value value = null;
        if(cal2 < 50){
        	value = restTemplate.getForObject("http://rest-service-4:16004/handle4_3?cal="+cal2, Value.class);
        }else{
        	value = restTemplate.getForObject("http://rest-service-4:16004/handle4_4?cal="+cal2, Value.class);
        }
		
        log.info(value.toString());
		return value;
    }
    
    @RequestMapping("/handle5_3")
    public Value handle5_3(@RequestParam(value="cal", defaultValue="50") String cal) {

        double cal2 = Math.abs(Double.valueOf(cal)%100+3)/1.03; 
        log.info(String.valueOf(cal2));
        
        Value value = null;
        if(cal2 < 50){
        	value = restTemplate.getForObject("http://rest-service-4:16004/handle4_5?cal="+cal2, Value.class);
        }else{
        	value = restTemplate.getForObject("http://rest-service-4:16004/handle4_6?cal="+cal2, Value.class);
        }
		
        log.info(value.toString());
		return value;
    }
}
