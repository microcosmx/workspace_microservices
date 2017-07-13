package hello;

import java.util.concurrent.Future;
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

        double cal2 = Math.abs(Double.valueOf(cal)+3)/1.03; 
        log.info(String.valueOf(cal2));
        
    	Value value = restTemplate.getForObject(
				"http://rest-service-4:16004/hello4?cal="+cal2, Value.class);
		
        log.info(value.toString());
		return value;
    }
    
    
    
    @RequestMapping("/hello5_1")
    public String hello5_1(@RequestParam(value="msg", defaultValue="") String msg) {
        
    	String result = restTemplate.getForObject("http://rest-service-4:16004/hello4_1?msg="+msg, String.class);
        
        return result;
    }
    
    @RequestMapping("/hello5_2")
    public String hello5_2(@RequestParam(value="msg", defaultValue="") String msg) {
        
    	String result = restTemplate.getForObject("http://rest-service-4:16004/hello4_2?msg="+msg, String.class);
        
        return result;
        
    }
}
