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

    @RequestMapping("/hello4")
    public Value hello4(@RequestParam(value="cal", defaultValue="50") String cal) {

        double cal2 = (Double.valueOf(cal) + 10)/1.1; 
        log.info(String.valueOf(cal2));
        
    	Value value = restTemplate.getForObject(
				"http://rest-service-3:16003/hello3?cal="+cal2, Value.class);
		log.info(value.toString());
		return value;
    }
    
    
    
    @RequestMapping("/hello4_1")
    public void hello4_1(@RequestParam(value="oldName", defaultValue="Alice") String oldName,
    		@RequestParam(value="newName", defaultValue="Jason1") String newName) {
        
        //simulate heavy tasks
        long sleep = (long) (Math.random() * 60);
        try {
			Thread.sleep(sleep);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
        
        String result = restTemplate.getForObject("http://rest-service-3:16003/hello3_1?oldName="+oldName+"&newName="+newName, String.class);
        
    }
    
    @RequestMapping("/hello4_2")
    public String hello4_2(@RequestParam(value="lastName", defaultValue="Smith") String lastName) {
        //simulate heavy tasks
    	long sleep = (long) (Math.random() * 60);
        try {
			Thread.sleep(sleep);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
        String result = restTemplate.getForObject("http://rest-service-3:16003/hello3_2?lastName="+lastName, String.class);
        
        return result;
    }
}
