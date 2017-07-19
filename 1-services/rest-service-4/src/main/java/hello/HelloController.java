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

        double cal2 = Math.abs(Double.valueOf(cal));
        log.info(String.valueOf(cal2));
        
        //queue messaging
    	log.info("message 41");
    	sendingBean.sayHello((cal2+3)/1.03);
    	log.info("message 42");
    	sendingBean.sayHello((cal2+6)/1.06);
    	log.info("message 43");
    	sendingBean.sayHello((cal2+12)/1.12);
        
        
    	String value = "success";
		log.info(value.toString());
		log.info("=============end================");
		return value;
    }
}
