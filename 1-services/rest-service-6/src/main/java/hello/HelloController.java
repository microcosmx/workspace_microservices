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
    public Value hello6(@RequestParam(value="cal", defaultValue="50") String cal)  throws InterruptedException, ExecutionException{

        double cal2 = Math.abs(Double.valueOf(cal));
        log.info(String.valueOf(cal2));
        
//        Value value5 = restTemplate.getForObject("http://rest-service-5:16005/hello5?cal="+cal, Value.class);
//        Value value4 = restTemplate.getForObject("http://rest-service-4:16004/hello4?cal="+cal, Value.class);
//        
//        Value value = null;
//        if(cal2 < 30){
//            value = restTemplate.getForObject("http://rest-service-5:16005/hello5?cal="+cal2, Value.class);
//        }else if(cal2 < 60){
//            value = restTemplate.getForObject("http://rest-service-4:16004/hello4?cal="+cal2, Value.class);
//        }else{
//            value = restTemplate.getForObject("http://rest-service-3:16003/hello3?cal="+cal2, Value.class);
//        }
        
        
        //async messages
        Future<String> msg1 = asyncTask.sendAsyncMessage1("msg1");
        Future<String> msg2 = asyncTask.sendAsyncMessage2("msg2");
        
        
        Value value = restTemplate.getForObject("http://rest-service-2:16002/hello2?cal="+cal2, Value.class);
        
        
        //async tasks
        Future<String> task1 = asyncTask.doAsyncTask1("task1");
        Future<String> task2 = asyncTask.doAsyncTask2("task2");
        while(true) {  
            if(task1.isDone() && task2.isDone()) {  
                log.info("------------Task1 result: {}", task1.get());
                log.info("------------Task2 result: {}", task2.get());
                break;  
            }  
            Thread.sleep(300);  
        }  
        log.info("All tasks finished.");  
        
        
		log.info(value.toString());
		log.info("=============end================");
		return value;
    }
    
    @RequestMapping("/hello6_1")
    public String hello6_1(@RequestParam(value="msg", defaultValue="") String msg) {
    	log.info("----------------msg result: {}", msg);  
    	return "callback completed";
    }
}

