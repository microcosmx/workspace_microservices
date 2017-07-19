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
    
    @Autowired
    private MsgSendingBean sendingBean;

    @RequestMapping("/process_queue4")
    public String process_queue4(@RequestParam(value="cal", defaultValue="50") String cal) {

        double cal2 = (Double.valueOf(cal) + 10)/1.1; 
        log.info(String.valueOf(cal2));
        
        //async messaging
    	log.info("message 41");
    	sendingBean.sayHello(cal2);
    	log.info("message 42");
    	sendingBean.sayHello(cal2);
    	log.info("message 43");
    	sendingBean.sayHello(cal2);
        
        
    	String value = "success";
		log.info(value.toString());
		log.info("=============end================");
		return value;
    }
}
