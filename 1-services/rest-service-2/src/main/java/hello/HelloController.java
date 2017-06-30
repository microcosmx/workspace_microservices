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

    @RequestMapping("/hello2")
    public Value hello2(@RequestParam(value="cal", defaultValue="50") String cal) {

        double cal2 = Math.sqrt(Math.abs(Double.valueOf(cal)%100))*10; 
        log.info(String.valueOf(cal2));
        
    	Value value = new Value();
    	value.setContent(true);
    	value.setId(1000L);
        
		log.info(value.toString());
		return value;
    }
    
    
    
    @RequestMapping("/handle2_1")
    public Value handle2_1(@RequestParam(value="cal", defaultValue="50") String cal) {

        double cal2 = Math.sqrt(Math.abs(Double.valueOf(cal)%100))*10; 
        log.info(String.valueOf(cal2));
        
        Value value = restTemplate.getForObject("http://rest-service-1:16001/handle1_1?cal="+cal2, Value.class);
		
        log.info(value.toString());
		return value;
    }
    
    @RequestMapping("/handle2_2")
    public Value handle2_2(@RequestParam(value="cal", defaultValue="50") String cal) {

        double cal2 = Math.sqrt(Math.abs(Double.valueOf(cal)%100))*9.9; 
        log.info(String.valueOf(cal2));
        
        Value value = restTemplate.getForObject("http://rest-service-1:16001/handle1_1?cal="+cal2, Value.class);
		
        log.info(value.toString());
		return value;
    }
    
    @RequestMapping("/handle2_3")
    public Value handle2_3(@RequestParam(value="cal", defaultValue="50") String cal) {

        double cal2 = Math.sqrt(Math.abs(Double.valueOf(cal)%100))*9.8; 
        log.info(String.valueOf(cal2));
        
        Value value = restTemplate.getForObject("http://rest-service-1:16001/handle1_2?cal="+cal2, Value.class);
		
        log.info(value.toString());
		return value;
    }
    
    @RequestMapping("/handle2_4")
    public Value handle2_4(@RequestParam(value="cal", defaultValue="50") String cal) {

        double cal2 = Math.sqrt(Math.abs(Double.valueOf(cal)%100))*9.7; 
        log.info(String.valueOf(cal2));
        
        Value value = restTemplate.getForObject("http://rest-service-1:16001/handle1_2?cal="+cal2, Value.class);
		
        log.info(value.toString());
		return value;
    }
    
    @RequestMapping("/handle2_5")
    public Value handle2_5(@RequestParam(value="cal", defaultValue="50") String cal) {

        double cal2 = Math.sqrt(Math.abs(Double.valueOf(cal)%100))*9.6; 
        log.info(String.valueOf(cal2));
        
        Value value = restTemplate.getForObject("http://rest-service-1:16001/handle1_3?cal="+cal2, Value.class);
		
        log.info(value.toString());
		return value;
    }
    
    @RequestMapping("/handle2_6")
    public Value handle2_6(@RequestParam(value="cal", defaultValue="50") String cal) {

        double cal2 = Math.abs(Double.valueOf(cal)%100)*10;
        log.info(String.valueOf(cal2));
        
        Value value = restTemplate.getForObject("http://rest-service-1:16001/handle1_3?cal="+cal2, Value.class);
		
        log.info(value.toString());
		return value;
    }
}
