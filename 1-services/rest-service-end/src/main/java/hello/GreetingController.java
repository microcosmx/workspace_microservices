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
    	
    	try {
			Thread.sleep(3000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
        
    	Greeting value = new Greeting(counter.incrementAndGet(), Double.valueOf(cal2)<100);
        
        log.info("--------service end-----------");
        log.info(value.toString());
        return value;
    }
}
