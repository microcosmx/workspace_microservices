package hello;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class HelloController {
    
    private static final Logger log = LoggerFactory.getLogger(Application.class);
    @Autowired
	private RestTemplate restTemplate;

    @RequestMapping("/hello6")
    public Value hello6(HttpSession session, @RequestParam(value="cal", defaultValue="50") String cal)  throws InterruptedException, ExecutionException{

        double cal2 = Math.abs(Double.valueOf(cal));
        log.info(String.valueOf(cal2));
        
        
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
		
        
        Value value = restTemplate.getForObject("http://rest-service-3:16003/hello3?name=service-6&cal="+cal2, Value.class);
        
		log.info(value.toString());
		return value;
    }
}

