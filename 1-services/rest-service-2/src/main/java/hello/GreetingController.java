package hello;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
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
	private ProductRepository repository;

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="cal", defaultValue="50") String cal) throws Exception {
    	// log.info(cal);

    	double cal2 = Math.abs(Double.valueOf(cal)-50); 
    	log.info(String.valueOf(cal2));
        
    	Greeting value = new Greeting(counter.incrementAndGet(), Double.valueOf(cal2)<100);

		log.info("-service end-");
		log.info(value.toString());
		
        return value;
    }
    
    @RequestMapping("/refreshdb")
    public String refreshdb() {
		// save a couple of Products
		repository.save(new Product("p1", 6.0));
		repository.save(new Product("p2", 3.6));
		
		// fetch all Products
		log.info("Products found with findAll():");
		log.info("-------------------------------");
		for (Product p : repository.findAll()) {
			log.info(p.toString());
		}
		
		return "Success";
    }
    
    
    /*
     * only support single process with multi-thread
     */
    @RequestMapping("/process_end_update")
    public String process_end_update(@RequestParam(value="name", defaultValue="p1") String name,
    		@RequestParam(value="priceChange", defaultValue="0.3") double priceChange) throws Exception {

		// fetch an individual Product to update
		Product upt = repository.findByName(name);
		if(upt != null){
			upt.price += priceChange;
			repository.save(upt);
			
			// fetch an individual Products
	        return String.valueOf(repository.findByName(name).price);
		}
		
		return "Empty";
		
    }
    
    @RequestMapping("/process_end_query")
    public String process_end_query(@RequestParam(value="name", defaultValue="p1") String name) throws Exception {

		// fetch an individual Products
    	log.info("Products found with findByName(name):");
    	log.info("-------------------------------");
		return String.valueOf(repository.findByName(name).price);
        
    }
}
