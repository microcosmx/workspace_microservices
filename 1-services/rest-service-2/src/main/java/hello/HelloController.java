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

        double cal2 = Math.sqrt(Double.valueOf(cal))*10; 
        log.info(String.valueOf(cal2));
        
    	Value value = restTemplate.getForObject(
				"http://rest-service-1:16001/hello1?cal="+cal2, Value.class);
        
		log.info(value.toString());
		return value;
    }
    
    
    
    @RequestMapping("/hello2_1")
    public String hello2_1(@RequestParam(value="oldName", defaultValue="Alice") String oldName,
    		@RequestParam(value="newName", defaultValue="Jason1") String newName) {
        //simulate heavy tasks
        long sleep = (long) (Math.random() * 60);
        try {
			Thread.sleep(sleep);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
        
        String result = restTemplate.getForObject("http://rest-service-1:16001/hello1_1?oldName="+oldName+"&newName="+newName, String.class);
        
        return result;
    }
    
    @RequestMapping("/hello2_2")
    public String hello2_2(@RequestParam(value="lastName", defaultValue="Smith") String lastName) {
        //simulate heavy tasks
    	long sleep = (long) (Math.random() * 60);
        try {
			Thread.sleep(sleep);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
        String result = restTemplate.getForObject("http://rest-service-1:16001/hello1_2?lastName="+lastName, String.class);
        
        return result;
    }
}
