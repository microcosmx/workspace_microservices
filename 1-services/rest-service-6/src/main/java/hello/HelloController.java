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

    @RequestMapping("/hello6")
    public String hello6(@RequestParam(value="cal", defaultValue="50") String cal) {

        double cal2 = Math.abs(Double.valueOf(cal));
        log.info(String.valueOf(cal2));
        
        //async messaging
    	log.info("message 1");
    	sendingBean.sayHello("message 1:" + cal2);
    	log.info("message 2");
    	sendingBean.sayHello("message 2:" + cal2*2);
    	log.info("message 3");
    	sendingBean.sayHello("message 3:" + cal2*3);
        
        
    	String value = "success";
		log.info(value.toString());
		log.info("=============end================");
		return value;
    }
    
}
