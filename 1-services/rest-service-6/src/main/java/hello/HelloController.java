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

    @RequestMapping("/hello6")
    public String hello6(@RequestParam(value="cal", defaultValue="50") String cal)  throws Exception{

        double cal2 = Math.abs(Double.valueOf(cal));
        log.info(String.valueOf(cal2));
        
        //refreshdb
        String refreshResult = restTemplate.getForObject("http://rest-service-end:16000/refreshdb", String.class);
        
        //async tasks last return
        Future<String> task1 = asyncTask.doAsyncQuery("Smith");
        
        //async messages first return
        Future<String> msg1 = asyncTask.sendAsyncUpdate1("normal", "positive"); 	//credit positive/negative/normal
        Future<String> msg2 = asyncTask.sendAsyncUpdate2("not started", "processing");	//loan processing/processed/not started
        
        
        String value = task1.get();
        
        long sleep = (long) (Math.random() * 600);
        try {
			Thread.sleep(sleep);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
        if(value.contains("negative") && value.contains("processing")){
        	throw new Exception("status error!!");
        }else{
        	log.info("----------------query result: {}", value);
        }
        
        
		log.info("=============end================");
		return value;
    }
    
    @RequestMapping("/hello6_1")
    public String hello6_1(@RequestParam(value="msg", defaultValue="") String msg) {
    	log.info("----------------update result: {}", msg);  
    	return "callback completed";
    }
}

