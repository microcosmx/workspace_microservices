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
    public String hello1(@RequestParam(value="cal", defaultValue="50") String cal) {

        double cal2 = Math.log10(Double.valueOf(cal))*50;
        log.info(String.valueOf(cal2));

        String value = restTemplate.getForObject(
				"http://rest-service-end:16000/greeting?cal="+cal2, String.class);
        
		log.info(value.toString());
		return value;
    }
}
