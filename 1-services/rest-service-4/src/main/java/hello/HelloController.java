package hello;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
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

    @RequestMapping("/hello4")
    public Value hello4(@RequestParam(value="cal", defaultValue="50") String cal) throws InterruptedException, ExecutionException, TimeoutException {

        double cal2 = Math.abs(Double.valueOf(cal)%100 + 10)/1.1; 
        log.info(String.valueOf(cal2));
        
        Value value = restTemplate.getForObject("http://rest-service-3:16003/hello3?cal="+cal2, Value.class);

		log.info(value.toString());
		return value;
    }
    
    
    
    @RequestMapping("/handle4_1")
    public Value handle4_1(@RequestParam(value="cal", defaultValue="50") String cal) {

        double cal2 = Math.abs(Double.valueOf(cal)%100-1)*1.01;
        log.info(String.valueOf(cal2));
        
        Value value = restTemplate.getForObject("http://rest-service-3:16003/handle3_1?cal="+cal2, Value.class);
		
        log.info(value.toString());
		return value;
    }
    
    @RequestMapping("/handle4_2")
    public Value handle4_2(@RequestParam(value="cal", defaultValue="50") String cal) {

        double cal2 = Math.abs(Double.valueOf(cal)%100-2)*1.02;
        log.info(String.valueOf(cal2));
        
        Value value = restTemplate.getForObject("http://rest-service-3:16003/handle3_1?cal="+cal2, Value.class);
		
        log.info(value.toString());
		return value;
    }
    
    @RequestMapping("/handle4_3")
    public Value handle4_3(@RequestParam(value="cal", defaultValue="50") String cal) {

        double cal2 = Math.abs(Double.valueOf(cal)%100-3)*1.03;
        log.info(String.valueOf(cal2));
        
        Value value = restTemplate.getForObject("http://rest-service-3:16003/handle3_1?cal="+cal2, Value.class);
		
        log.info(value.toString());
		return value;
    }
    
    @RequestMapping("/handle4_4")
    public Value handle4_4(@RequestParam(value="cal", defaultValue="50") String cal) {

        double cal2 = Math.abs(Double.valueOf(cal)%100-4)*1.04;
        log.info(String.valueOf(cal2));
        
        Value value = restTemplate.getForObject("http://rest-service-3:16003/handle3_1?cal="+cal2, Value.class);
		
        log.info(value.toString());
		return value;
    }
    
    @RequestMapping("/handle4_5")
    public Value handle4_5(@RequestParam(value="cal", defaultValue="50") String cal) {

        double cal2 = Math.abs(Double.valueOf(cal)%100-5)*1.05;
        log.info(String.valueOf(cal2));
        
        Value value = restTemplate.getForObject("http://rest-service-3:16003/handle3_1?cal="+cal2, Value.class);
		
        log.info(value.toString());
		return value;
    }
    
    @RequestMapping("/handle4_6")
    public Value handle4_6(@RequestParam(value="cal", defaultValue="50") String cal) {

        double cal2 = Math.abs(Double.valueOf(cal)%100-6)*1.06;
        log.info(String.valueOf(cal2));
        
        Value value = restTemplate.getForObject("http://rest-service-3:16003/handle3_1?cal="+cal2, Value.class);
		
        log.info(value.toString());
		return value;
    }
}
