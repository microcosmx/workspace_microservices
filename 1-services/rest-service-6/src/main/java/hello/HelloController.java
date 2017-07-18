package hello;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

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
    
    @Autowired  
    private AsyncTask asyncTask;

    @RequestMapping("/hello6")
    public Value hello6(@RequestParam(value="cal", defaultValue="50") String cal)  throws InterruptedException, ExecutionException{

        double cal2 = Math.abs(Double.valueOf(cal));
        log.info(String.valueOf(cal2));
        
        Value value = restTemplate.getForObject("http://rest-service-3:16003/hello3?name=service-6&cal="+cal2, Value.class);
        
		log.info(value.toString());
		return value;
    }
    
    
    
    @RequestMapping("/cross_timeout")
    public Value cross_timeout(@RequestParam(value="cal", defaultValue="50") String cal)  throws InterruptedException, ExecutionException{

        double cal2 = Math.abs(Double.valueOf(cal));
        log.info(String.valueOf(cal2));
        
        int access_times = (int) (Math.random() * 6);
        log.info("--------------------" + access_times);
        
        for(int i = 0; i < access_times; i++){
        	//async messages
            Future<Value> task1 = asyncTask.sendAsyncCal(cal2);
        }
        
        
        Value value = null;
        if(Math.random() < 0.5){
        	log.info("----------service 5----------");
        	value = restTemplate.getForObject("http://rest-service-5:16005/hello5", Value.class);
        }else{
        	log.info("----------service 4----------");
        	value = restTemplate.getForObject("http://rest-service-4:16004/hello4", Value.class);
        }
        
        
		log.info(value.toString());
		return value;
    }
}

