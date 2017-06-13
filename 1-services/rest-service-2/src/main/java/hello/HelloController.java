package hello;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.UUID;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class HelloController {
    
    private static final Logger log = LoggerFactory.getLogger(Application.class);
    @Autowired
	private RestTemplate restTemplate;

    @RequestMapping("/hello2")
    public Value hello2(HttpSession session, @RequestParam(value="cal", defaultValue="50") String cal) {

        double cal2 = Math.sqrt(Double.valueOf(cal))*10; 
        log.info(String.valueOf(cal2));
        
        
        UUID uid = (UUID) session.getAttribute("uid");
    	log.info("--------session recoverred------------");
		log.info(uid + ":" + session.getAttribute("current_cal"));
		
        
        Value value = restTemplate.getForObject("http://rest-service-end:16000/greeting?cal="+cal2, Value.class);
        
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
