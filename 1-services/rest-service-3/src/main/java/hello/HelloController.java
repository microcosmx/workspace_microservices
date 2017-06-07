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
    
    @Autowired  
    private AsyncTask asyncTask;  

    @RequestMapping("/hello3")
    public Value hello3(@RequestParam(value="cal", defaultValue="50") String cal) throws Exception {

        double cal2 = Math.pow(Double.valueOf(cal), 2)/100; 
        log.info(String.valueOf(cal2));

        Value value2 = restTemplate.getForObject("http://rest-service-2:16002/hello2?cal="+cal, Value.class);
        Value value1 = restTemplate.getForObject("http://rest-service-1:16001/hello1?cal="+cal, Value.class);
        
        
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
    
    @RequestMapping("/hello3_1")
    public void hello3_1(@RequestParam(value="msg", defaultValue="") String msg) {
        log.info("------hello3------received--------" + msg);
        
        Value value = restTemplate.getForObject("http://rest-service-1:16001/hello1?cal=66", Value.class);
        
        //simulate heavy tasks
        long sleep = "msg1".equals(msg) ? 1200 : 600;
        try {
			Thread.sleep(sleep);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
        
        String result = restTemplate.getForObject("http://rest-service-6:16006/hello6_1?msg="+msg, String.class);
        
        
//        try {
//			Future<String> rest_callback = asyncTask.callbackAsyncMessage(msg);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    }
    
    
}
