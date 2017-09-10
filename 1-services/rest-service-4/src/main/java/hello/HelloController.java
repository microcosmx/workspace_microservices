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
				"https://rest-service-3:16003/hello3?cal="+cal2, Value.class);
		log.info(value.toString());
		return value;
    }
}
