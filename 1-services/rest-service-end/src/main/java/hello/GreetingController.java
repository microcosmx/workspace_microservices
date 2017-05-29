package hello;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

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
    
//    @Autowired
//    private MsgSendingBean sendingBean;
    @Autowired
    private MsgSendingBean2 sendingBean2;

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="cal", defaultValue="50") String cal) throws Exception {
    	// log.info(cal);

    	double cal2 = Math.abs(Double.valueOf(cal)-50); 
    	log.info(String.valueOf(cal2));
    	
    	//async messaging
//    	log.info("message 1");
//    	sendingBean.sayHello("message 1:" + cal2);
//    	log.info("message 2");
//    	sendingBean.sayHello("message 2:" + cal2*2);
//    	log.info("message 3");
//    	sendingBean.sayHello("message 3:" + cal2*3);
    	
    	log.info("message stream sending:");
    	List<String> strList = Arrays.asList(String.valueOf(cal), String.valueOf(cal2), "100.0"); 
	    List<String> filtered = strList.stream().filter(x -> x.length() > 2).collect(Collectors.toList()); 
	    sendingBean2.processor(filtered);
	    
        
    	Greeting value = null;
        if(cal2 < 6){
        	throw new Exception("unexpected small input");
        }else if(cal2 < 100){
        	value = new Greeting(counter.incrementAndGet(), Double.valueOf(cal2)<100);
        }else{
        	throw new Exception("unexpected input scope");
        }
        
        log.info("--------service end-----------");
        log.info(value.toString());
        return value;
    }
}
