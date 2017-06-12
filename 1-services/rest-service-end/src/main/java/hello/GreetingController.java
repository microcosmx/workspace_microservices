package hello;

import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class GreetingController {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

    private final AtomicLong counter = new AtomicLong();
    
    @Autowired
	private RestTemplate restTemplate;

    @RequestMapping("/greeting")
    public Value greeting(@RequestParam(value="cal", defaultValue="50") String cal) throws Exception {
    	// log.info(cal);

    	double cal2 = Math.abs(Double.valueOf(cal)-50); 
    	log.info(String.valueOf(cal2));
    	
    	Value value = restTemplate.getForObject("http://rest-service-external:16100/greeting?cal="+cal2, Value.class);
//    	Value value = new Greeting(counter.incrementAndGet(), Double.valueOf(cal2)<100);
        
        log.info("--------service end-----------");
        log.info(value.toString());
        return value;
    }
}
