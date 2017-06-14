package hello;

import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpSession;

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
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;

import java.util.UUID;
import java.util.concurrent.Future;

@RestController
public class HelloController {
    
    private static final Logger log = LoggerFactory.getLogger(Application.class);
    @Autowired
	private RestTemplate restTemplate;
    
    @RequestMapping("/hello3")
    public Value hello3(HttpSession session, 
    		@RequestHeader(value="Cookie") String cookies, 
    		@RequestParam(value="name", defaultValue="service-6") String name, 
    		@RequestParam(value="cal", defaultValue="50") String cal) throws Exception {

        double cal2 = Math.pow(Double.valueOf(cal), 2)/100; 
        log.info(String.valueOf(cal2));
        
        
        UUID uid = (UUID) session.getAttribute("uid");
		if (uid == null) {
			log.info("--------session created 3-----------");
			uid = UUID.randomUUID();
			session.setAttribute("uid", uid);
			session.setAttribute("current_cal", cal);
		}else{
			log.info("--------session recoverred 3-----------");
			log.info(uid + ":" + session.getAttribute("current_cal"));
		}
		
		
		log.info("Cookies: " + cookies);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Cookie", "SESSION=" + session.getId());
		ResponseEntity<Value> exchange = null;
		if("service-6".equals(name)){
			exchange = restTemplate.exchange("http://rest-service-end:16000/greeting?cal="+cal2, 
					HttpMethod.GET,new HttpEntity<Void>(headers), Value.class);
		}else if("service-5".equals(name)){
			exchange = restTemplate.exchange("http://rest-service-1:16001/hello1?cal="+cal2, 
					HttpMethod.GET,new HttpEntity<Void>(headers), Value.class);
		}else{
			exchange = restTemplate.exchange("http://rest-service-2:16002/hello2?cal="+cal2, 
					HttpMethod.GET,new HttpEntity<Void>(headers), Value.class);
		}
		Value value = exchange.getBody();

        
		
//		Value value = new Value();
//        if("service-6".equals(name)){
//        	value = restTemplate.getForObject("http://rest-service-end:16000/greeting?cal="+cal2, Value.class);
//        }else if("service-5".equals(name)){
//        	value = restTemplate.getForObject("http://rest-service-1:16001/hello1?cal="+cal2, Value.class);
//        }else{
//        	value = restTemplate.getForObject("http://rest-service-2:16002/hello2?cal="+cal2, Value.class);
//        }
		
        
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
