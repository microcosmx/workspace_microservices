package hello;

import java.util.UUID;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestHeader;
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
    
    @Autowired  
    private AsyncTask asyncTask;

    @RequestMapping("/hello1")
    public Value hello1(HttpSession session, 
    		@RequestHeader(value="Cookie") String cookies, 
    		@RequestParam(value="cal", defaultValue="50") String cal) {

        double cal2 = Math.log10(Double.valueOf(cal))*50;
        log.info(String.valueOf(cal2));
        
        
        UUID uid = (UUID) session.getAttribute("uid");
		if (uid == null) {
			log.info("--------session created 1-----------");
			uid = UUID.randomUUID();
			session.setAttribute("uid", uid);
			session.setAttribute("current_cal", cal);
		}else{
			log.info("--------session recoverred 1-----------");
			log.info(uid + ":" + session.getAttribute("current_cal"));
		}
        
        
		log.info("Cookies: " + cookies);
        //async messages
        try {
			Future<Value> task1 = asyncTask.sendAsyncCal(session, cal2);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        Value value = new Value();
		return value;
    }
    
    @RequestMapping("/hello1_callback")
    public String hello1_callback(@RequestParam(value="cal_back", defaultValue="50") String cal_back) {
    	
    	log.info("-------------external call back-------------");
        log.info(String.valueOf(cal_back));
        
        return "-------call back end-------";
        
    }
}
