package hello;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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
    private AsyncTask asyncTask;  
    
    @RequestMapping("/process6")
    public String process6(@RequestParam(value="name", defaultValue="product") String name)  throws Exception{

        //refreshdb
        String refreshResult = restTemplate.getForObject("http://rest-service-end:16000/refreshdb", String.class);
        
        String result = process6_query(name);
        
        
        //simulate heavy tasks
        long sleep = 6000;
        try {
			Thread.sleep(sleep);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
        
//        process6_update();
        
		log.info("=============end================");
		return result;
    }

    @RequestMapping("/process6_query")
    public String process6_query(@RequestParam(value="name", defaultValue="product") String name)  throws Exception{

        //refreshdb
        String refreshResult = restTemplate.getForObject("http://rest-service-end:16000/refreshdb", String.class);
        
        String result = restTemplate.getForObject("http://rest-service-5:16005/hello5__query?name="+name, String.class);
        
		log.info("=============end================");
		return result;
    }
    
    
    @RequestMapping("/process6_update")
    public String process6_update(@RequestParam(value="name", defaultValue="product") String name,
    		@RequestParam(value="price", defaultValue="0.5") String price)  throws Exception{

        double price2 = Math.abs(Double.valueOf(price));
        log.info(String.valueOf(price2));
        
        //update
        String result = restTemplate.getForObject("http://rest-service-5:16005/hello5_update?name="+name, String.class);
        
		log.info("=============end================");
		return result;
    }
    
    
    
}

