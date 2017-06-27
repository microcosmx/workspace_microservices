package hello;

import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {
	
	private static final Logger log = LoggerFactory.getLogger(Application.class);

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
    	
    	log.info("i am an info log");
    	log.trace("I am trace log.");  
    	log.debug("I am debug log.");  
    	log.warn("I am warn log.");  
    	log.error("I am error log.");  
    	
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));
    }
}
