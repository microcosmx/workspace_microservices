package hello;

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
    
    @Autowired
    private MsgSendingBean sendingBean;

    @RequestMapping("/process_queue6")
    public String process_queue6(@RequestParam(value="cal", defaultValue="50") String cal) {

        double cal2 = Math.abs(Double.valueOf(cal));
        log.info(String.valueOf(cal2));
        
        //async messaging
    	log.info("message 61");
    	sendingBean.sayHello(cal2);
    	log.info("message 62");
    	sendingBean.sayHello(cal2/2);
    	log.info("message 63");
    	sendingBean.sayHello(cal2/3);
        
        
    	String value = "success";
		log.info(value.toString());
		log.info("=============end================");
		return value;
    }
    
}
