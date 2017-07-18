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
    
    private final AtomicLong counter = new AtomicLong();
    
    @Autowired
	private StatusBean statusBean;

    @RequestMapping("/hello1")
    public Value hello1(@RequestParam(value="cal", defaultValue="50") String cal) {

        double cal2 = Math.log10(Double.valueOf(cal))*50;
        log.info(String.valueOf(cal2));

    	Value value = restTemplate.getForObject(
				"http://rest-service-end:16000/greeting?cal="+cal2, Value.class);
        
		log.info(value.toString());
		return value;
    }
    
    
    
    @RequestMapping("/hello1_1")
    public String hello1_1(@RequestParam(value="oldState", defaultValue="normal") String oldState,
    		@RequestParam(value="newState", defaultValue="positive") String newState) {
        //simulate heavy tasks
        long sleep = (long) (Math.random() * 60);
        try {
			Thread.sleep(sleep);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
        
        //50% chance simulate a different state of current micro-service
        if("normal".equals(oldState)){
            if(Math.random()<0.5){
            	statusBean.statusMap.put("status", "negative");
            }
        	newState = statusBean.statusMap.get("status");
        }
        
        String result = restTemplate.getForObject("http://rest-service-end:16000/persist?oldState="+oldState+"&newState="+newState, String.class);
        
        return result;
    }
    
    @RequestMapping("/hello1_2")
    public String hello1_2(@RequestParam(value="lastName", defaultValue="Smith") String lastName) {
        //simulate heavy tasks
    	long sleep = (long) (Math.random() * 60);
        try {
			Thread.sleep(sleep);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
        
        String result = restTemplate.getForObject("http://rest-service-end:16000/persist_get?lastName="+lastName, String.class);
        
        //80% chance correct
        if("negative".equals(statusBean.statusMap.get("status"))){
        	if(Math.random()<0.6){
        		result = result.replace("negative", "positive");
            }
        }
        
        return result;
    }
    
    
    public String getState(){
    	return ""+counter.incrementAndGet();
    }
}
