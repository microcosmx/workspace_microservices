package hello;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServletServerHttpRequest;

import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class HelloController {
    
    private static final Logger log = LoggerFactory.getLogger(Application.class);
    @Autowired
	private RestTemplate restTemplate;

    @RequestMapping("/hello2")
    public Value hello2(HttpSession session, 
    		@RequestHeader(value="Cookie") String cookies, 
    		@RequestParam(value="cal", defaultValue="50") String cal) {

        double cal2 = Math.sqrt(Double.valueOf(cal))*10;
        log.info(String.valueOf(cal2));
        
        
        UUID uid = (UUID) session.getAttribute("uid");
		if (uid == null) {
			log.info("--------session created 2-----------");
			uid = UUID.randomUUID();
			session.setAttribute("uid", uid);
			session.setAttribute("current_cal", cal);
		}else{
			log.info("--------session recoverred 2-----------");
			log.info(uid + ":" + session.getAttribute("current_cal"));
		}
		
		
		log.info("Cookies: " + cookies);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Cookie", "SESSION=" + session.getId());
		ResponseEntity<Value> exchange = restTemplate.exchange("http://rest-service-end:16000/greeting?cal="+cal2, 
				HttpMethod.GET,new HttpEntity<Void>(headers), Value.class);
		Value value = exchange.getBody();
		
        
//        Value value = restTemplate.getForObject("http://localhost:16000/greeting?cal="+cal2, Value.class);
//        Value value = new Value();
		
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
