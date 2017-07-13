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

        double cal2 = Math.abs(Double.valueOf(cal));
        log.info(String.valueOf(cal2));
        
        Value value = restTemplate.getForObject("http://rest-service-3:16003/hello3?name=service-6&cal="+cal2, Value.class);
        
		log.info(value.toString());
		return value;
    }
    
    
    
    @RequestMapping("/external_process")
    public Value external_process(@RequestParam(value="cal", defaultValue="50") String cal)  throws InterruptedException, ExecutionException{

        double cal2 = Math.abs(Double.valueOf(cal));
        log.info(String.valueOf(cal2));
        
        Value value = null;
        if(Math.random() < 0.5){
        	value = restTemplate.getForObject("http://rest-service-6:16006/hello6", Value.class);
        }else{
        	if(Math.random() < 0.5){
        		value = restTemplate.getForObject("http://rest-service-5:16005/hello5", Value.class);
        	}else{
        		value = restTemplate.getForObject("http://rest-service-4:16004/hello4", Value.class);
        	}
        }
        
		log.info(value.toString());
		return value;
    }
}

