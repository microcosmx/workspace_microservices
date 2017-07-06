package hello;

import java.util.List;
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
	private CustomerRepository repository;

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
    	repository.deleteAll();

    	// save a couple of customers
		repository.save(new Customer("normal", "Smith")); 		//credit positive/negative/normal
		repository.save(new Customer("not started", "Smith"));	//loan processing/processed/not started
		
		// fetch all customers
		log.info("Customers found with findAll():");
		log.info("-------------------------------");
		for (Customer customer : repository.findAll()) {
			log.info(customer.toString());
		}
		
		return "Success";
    }
    
    @RequestMapping("/persist")
    public String persist(@RequestParam(value="oldState", defaultValue="normal") String oldState,
    		@RequestParam(value="newState", defaultValue="positive") String newState) throws Exception {

		// fetch an individual customer
		Customer upt = repository.findByState(oldState);
		if(upt != null){
			upt.state = newState;
			
			repository.save(upt);
			
			// fetch all customers
			log.info("Customers found with findAll():");
			log.info("-------------------------------");
			for (Customer customer : repository.findAll()) {
				log.info(customer.toString());
			}
			
			// fetch an individual customer
	        return repository.findByState(newState).toString();
		}
		
		return "Empty";
		
    }
    
    @RequestMapping("/persist_get")
    public String persist_get(@RequestParam(value="lastName", defaultValue="Smith") String lastName) throws Exception {

		// fetch an individual customer
    	log.info("Customers found with findByLastName(lastName):");
    	log.info("-------------------------------");
        return repository.findByLastName(lastName).toString();
    }
}
