package hello;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
    
    @Autowired
	private SchedulerBean scheduler;
    
    // 定义锁对象
    private Lock lock = new ReentrantLock();

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
    
    
    /*
     * only support single process with multi-thread
     */
    @RequestMapping("/process_end_update_sp_safe")
    public String process_end_update_sp_safe(@RequestParam(value="name", defaultValue="p1") String name,
    		@RequestParam(value="priceChange", defaultValue="1") double priceChange) throws Exception {
    	
    	try {
            // 加锁
            lock.lock();
            // fetch an individual Product to update
            log.info("---" + counter.incrementAndGet());
        	
    		Product upt = repository.findByName(name);
    		
    		if(upt != null){
    			if(scheduler.priceMap.containsKey(name)){
    				upt.price = scheduler.priceMap.get(name) + priceChange;
    				
    				long sleep = (long)(Math.random()*100);
    	    		try {
    	    			Thread.sleep(sleep);
    	    		} catch (InterruptedException e1) {
    	    			e1.printStackTrace();
    	    		}
    	    		
    				repository.save(upt);
    				scheduler.priceMap.put(name, upt.price);
    			}
    			
    			// fetch an individual Products
    			log.info("-----------------" + repository.findByName(name).price);
    	        return String.valueOf(repository.findByName(name).price);
    		}
        } finally {
            // 释放锁
            lock.unlock();
        }
		
		return "Empty";
		
    }
    
    @RequestMapping("/process_end_update_sp")
    public String process_end_update_sp(@RequestParam(value="name", defaultValue="p1") String name,
    		@RequestParam(value="priceChange", defaultValue="1") double priceChange) throws Exception {
    	
    	log.info("---" + counter.incrementAndGet());
    	
		Product upt = repository.findByName(name);
		
		if(upt != null){
			if(scheduler.priceMap.containsKey(name)){
				upt.price = scheduler.priceMap.get(name) + priceChange;
				
				long sleep = (long)(Math.random()*100);
				try {
					Thread.sleep(sleep);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				
				repository.save(upt);
				scheduler.priceMap.put(name, upt.price);
			}
			log.info("-----------------" + repository.findByName(name).price);
	        return String.valueOf(repository.findByName(name).price);
		}
		return "Empty";
    }
    
    
    @RequestMapping("/process_end_update")
    public String process_end_update(@RequestParam(value="name", defaultValue="p1") String name,
    		@RequestParam(value="priceChange", defaultValue="1") double priceChange) throws Exception {

		// fetch an individual Products
		Product upt = repository.findByName(name);
		if(upt != null){
			if(scheduler.priceMap.containsKey(name)){
				double cache_price = scheduler.priceMap.get(name);
				if(cache_price == upt.price){
					upt.price += priceChange;
					scheduler.priceMap.put(name, upt.price);
				}else{
					scheduler.priceMap.put(name, upt.price);
					return "price changed: " + scheduler.priceMap.get(name);
				}
			}else{
				upt.price += priceChange;
			}
			
			repository.save(upt);
			
			// fetch all cache
			log.info("Cached maps:");
			log.info("-------------------------------");
			log.info(scheduler.priceMap.toString());
			// fetch all Products
			log.info("Products found with findAll():");
			log.info("-------------------------------");
			for (Product p : repository.findAll()) {
				log.info(p.toString());
			}
			
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
    	if(scheduler.priceMap.containsKey(name)){
    		log.info("-----------cached value---------------");
    		return String.valueOf(scheduler.priceMap.get(name));
    	}else{
    		log.info("-----------db value---------------");
    		return String.valueOf(repository.findByName(name).price);
    	}
        
    }
}
