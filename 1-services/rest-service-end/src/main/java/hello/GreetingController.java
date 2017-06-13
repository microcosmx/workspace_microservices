package hello;

import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


import java.util.UUID;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class GreetingController {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

    private final AtomicLong counter = new AtomicLong();
    
    @Autowired
	private RestTemplate restTemplate;

    @RequestMapping("/greeting")
    public Value greeting(HttpSession session, @RequestParam(value="cal", defaultValue="50") String cal) throws Exception {
    	// log.info(cal);

    	double cal2 = Math.abs(Double.valueOf(cal)-50); 
    	log.info(String.valueOf(cal2));
    	
    	
    	UUID uid = (UUID) session.getAttribute("uid");
    	log.info("--------session recoverred------------");
		log.info(uid + ":" + session.getAttribute("current_cal"));
		
    	
    	// Value value = restTemplate.getForObject("http://rest-service-external:16100/greeting?cal="+cal2, Value.class);
   	    Value value = new Value();
   	    value.setId(counter.incrementAndGet());
   	    value.setContent(Double.valueOf(cal2)<100);
        
        log.info("--------service end-----------");
        log.info(value.toString());
        return value;
    }
    
    
    @GetMapping("/session")
    public String session_uid(HttpSession session, @RequestParam(value="cal", defaultValue="50") String cal) {
		UUID uid = (UUID) session.getAttribute("uid");
		if (uid == null) {
			log.info("--------session created-----------");
			uid = UUID.randomUUID();
			session.setAttribute("uid", uid);
			session.setAttribute("current_cal", cal);
		}else{
			log.info("--------session recoverred-----------");
			log.info(uid + ":" + session.getAttribute("current_cal"));
		}
		
		return uid.toString() + ": " + session.getAttribute("current_cal");
	}
    
}
