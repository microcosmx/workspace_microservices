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

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="cal", defaultValue="50") String cal) throws Exception {
    	// log.info(cal);

    	double cal2 = Math.abs(Double.valueOf(cal)-50); 
    	log.info(String.valueOf(cal2));
    	
    	Greeting value = new Greeting(counter.incrementAndGet(), Double.valueOf(cal2)<100);
        
        log.info("--------service end-----------");
        log.info(value.toString());
        return value;
    }
    
    
    
    @RequestMapping("/handle_end_1")
    public Greeting handle_end_1(@RequestParam(value="cal", defaultValue="50") String cal) throws Exception {

        double cal2 = Double.valueOf(cal); 
        log.info(String.valueOf(cal2));
        
        Greeting value = null;
        if(cal2 < 0){
        	throw new Exception("unexpected result scope");
        }else{
        	value = new Greeting(counter.incrementAndGet(), Double.valueOf(cal2)<100);
        }
		
        log.info(value.toString());
		return value;
    }
    
    @RequestMapping("/handle_end_2")
    public Greeting handle_end_2(@RequestParam(value="cal", defaultValue="50") String cal) throws Exception {

    	double cal2 = Double.valueOf(cal); 
        log.info(String.valueOf(cal2));
        
        Greeting value = null;
        if(cal2 < 100){
        	value = new Greeting(counter.incrementAndGet(), Double.valueOf(cal2)<100);
        }else{
        	throw new Exception("unexpected result scope");
        }
		
        log.info(value.toString());
		return value;
    }
}
