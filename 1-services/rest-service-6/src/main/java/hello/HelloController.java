package hello;

import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class HelloController {
    
    private static final Logger log = LoggerFactory.getLogger(Application.class);
    @Autowired
	private RestTemplate restTemplate;

    @RequestMapping("/hello6")
    public Value hello6(@RequestParam(value="cal", defaultValue="50") String cal)  throws InterruptedException, ExecutionException{

        double cal2 = Math.abs(Double.valueOf(cal)%100);
        log.info(String.valueOf(cal2));
        
        Value value = restTemplate.getForObject("http://rest-service-5:16005/hello5?cal="+cal2, Value.class);
        
		log.info(value.toString());
		return value;
    }
    
    
    
    @RequestMapping("/handle6_1")
    public Value handle6_1(@RequestParam(value="cal", defaultValue="50") String cal)  throws InterruptedException, ExecutionException{

        double cal2 = Math.abs(Double.valueOf(cal)%100);
        log.info(String.valueOf(cal2));
        
        Value value = null;
        if(cal2 < 30){
        	value = restTemplate.getForObject("http://rest-service-5:16005/handle5_1?cal="+cal2, Value.class);
        }else if(cal2 < 60){
        	value = restTemplate.getForObject("http://rest-service-5:16005/handle5_2?cal="+cal2, Value.class);
        }else{
        	value = restTemplate.getForObject("http://rest-service-5:16005/handle5_3?cal="+cal2, Value.class);
        }
        
		log.info(value.toString());
		return value;
    }
    
}

