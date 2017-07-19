package hello;

import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

    private final AtomicLong counter = new AtomicLong();
    
    @Autowired
    private MsgSendingBean sendingBean;

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value="cal", defaultValue="50") String cal) throws Exception {
    	// log.info(cal);

    	double cal2 = Math.abs(Double.valueOf(cal)-50); 
    	log.info(String.valueOf(cal2));
    	
    	//async messaging
    	log.info("message 1");
    	sendingBean.sayHello("message 1:" + cal2);
    	log.info("message 2");
    	sendingBean.sayHello("message 2:" + cal2*2);
    	log.info("message 3");
    	sendingBean.sayHello("message 3:" + cal2*3);
    	
    	String value = "success";
        
        log.info("--------service end-----------");
        log.info(value.toString());
        return value;
    }
}
