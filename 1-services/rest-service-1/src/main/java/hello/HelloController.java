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

    @RequestMapping("/hello1")
    public Value hello1(@RequestParam(value="cal", defaultValue="50") String cal) {

        double cal2 = Math.log10(Double.valueOf(cal))*50;
        log.info(String.valueOf(cal2));

        Value value = new Value();
    	value.setContent(true);
    	value.setId(1001L);
        
		log.info(value.toString());
		return value;
    }
    
    
    
    @RequestMapping("/handle1_1")
    public Value handle1_1(@RequestParam(value="cal", defaultValue="50") String cal) {

        double cal2 = Math.abs(Double.valueOf(cal))/1.01;
        log.info(String.valueOf(cal2));
        
        Value value = null;
        if(cal2 < 50){
        	value = restTemplate.getForObject("http://rest-service-end:16000/handle_end_1?cal="+cal2, Value.class);
        }else{
        	value = restTemplate.getForObject("http://rest-service-end:16000/handle_end_2?cal="+cal2, Value.class);
        }
		
        log.info(value.toString());
		return value;
    }
    
    @RequestMapping("/handle1_2")
    public Value handle1_2(@RequestParam(value="cal", defaultValue="50") String cal) {

    	double cal2 = Math.abs(Double.valueOf(cal))/1.02;
        log.info(String.valueOf(cal2));
        
        Value value = null;
        if(cal2 < 50){
        	value = restTemplate.getForObject("http://rest-service-end:16000/handle_end_1?cal="+cal2, Value.class);
        }else{
        	value = restTemplate.getForObject("http://rest-service-end:16000/handle_end_2?cal="+cal2, Value.class);
        }
		
        log.info(value.toString());
		return value;
    }
    
    @RequestMapping("/handle1_3")
    public Value handle1_3(@RequestParam(value="cal", defaultValue="50") String cal) {

    	double cal2 = Math.abs(Double.valueOf(cal))/1.03;
        log.info(String.valueOf(cal2));
        
        Value value = null;
        if(cal2 < 50){
        	value = restTemplate.getForObject("http://rest-service-end:16000/handle_end_1?cal="+cal2, Value.class);
        }else{
        	value = restTemplate.getForObject("http://rest-service-end:16000/handle_end_2?cal="+cal2, Value.class);
        }
		
        log.info(value.toString());
		return value;
    }
}
