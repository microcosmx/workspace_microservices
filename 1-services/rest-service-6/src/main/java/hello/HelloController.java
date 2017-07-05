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
    
    @RequestMapping("/process6_sync")
    public String process6_sync()  throws Exception{

    	int priceChange = 1;
        //refreshdb
        //String refreshResult = restTemplate.getForObject("http://rest-service-end:16000/refreshdb", String.class);
        
    	//instance 1
        String price = restTemplate.getForObject("http://rest-service-end:16000/process_end_query?name=p1", String.class);
        
        //instance 2 (simulated)
        String result_end = restTemplate.getForObject("http://rest-service-end:16000/process_end_update?name=p1&priceChange="+priceChange, String.class);
        
        //instance 1 should be "price changed", this is correct
        //instance 2 should be +2, this is an error
        String result = restTemplate.getForObject("http://rest-service-end:16000/process_end_update?name=p1&priceChange="+priceChange, String.class);
        
//        if(result.contains("price changed") || result.contains("Empty")){
//        	//TODO correct
//        }else{
//        	double cp = Double.valueOf(result);
//        	if(cp == Double.valueOf(price)+priceChange){
//        		//TODO correct
//        	}else{
//        		throw new Exception("update price incorrectly!!!");
//        	}
//        }
        
		log.info("=============end================");
		return result;
    }
    
    
    @RequestMapping("/process6_sync_crossreq")
    public String process6_sync_crossreq()  throws Exception{

    	int priceChange = 1;
        
    	//instance 1
//        String price = restTemplate.getForObject("http://rest-service-end:16000/process_end_query?name=p1", String.class);
    	String price = "";
    	if(Math.random()<0.5){
    		price = restTemplate.getForObject("http://rest-service-5:16005/process5_query_1?name=p1", String.class);
    	}else{
    		price = restTemplate.getForObject("http://rest-service-5:16005/process5_query_2?name=p1", String.class);
    	}
        
        //instance 2 (simulated)
        String result_end = restTemplate.getForObject("http://rest-service-1:16001/process_end_update?name=p1&priceChange="+priceChange, String.class);
        
        String result = "";
        if(Math.random()<0.2){
        	//instance 2 (simulated): error
            result = restTemplate.getForObject("http://rest-service-1:16001/process_end_update?name=p1&priceChange="+priceChange, String.class);
        }else{
        	//instance 1 (simulated): correct
//            result = restTemplate.getForObject("http://rest-service-end:16000/process_end_update?name=p1&priceChange="+priceChange, String.class);
        	if(Math.random()<0.5){
        		result = restTemplate.getForObject("http://rest-service-5:16005/process5_update_1?name=p1&priceChange="+priceChange, String.class);
        	}else{
        		result = restTemplate.getForObject("http://rest-service-5:16005/process5_update_2?name=p1&priceChange="+priceChange, String.class);
        	}
        }
        
        if(result.contains("price changed") || result.contains("Empty")){
        	//TODO correct
        }else{
        	double cp = Double.valueOf(result);
        	if(cp == Double.valueOf(price)+priceChange){
        		//TODO correct
        	}else{
        		throw new Exception("update price incorrectly!!!");
        	}
        }
        
		log.info("=============end================");
		return result;
    }
    
    
    @RequestMapping("/process6_sync_restart")
    public String process6_sync_restart()  throws Exception{

    	int priceChange = 1;
        
    	//instance 1
//        String price = restTemplate.getForObject("http://rest-service-end:16000/process_end_query?name=p1", String.class);
    	String price = "";
    	if(Math.random()<0.5){
    		price = restTemplate.getForObject("http://rest-service-5:16005/process5_query_1?name=p1", String.class);
    	}else{
    		price = restTemplate.getForObject("http://rest-service-5:16005/process5_query_2?name=p1", String.class);
    	}
        
        //instance 2 (simulated)
        String result_end = restTemplate.getForObject("http://rest-service-1:16001/process_end_update?name=p1&priceChange="+priceChange, String.class);
        
        //simulate restart rest-service-end instance will get error
        //or it works fine
        long sleep = 30000;
        try {
			Thread.sleep(sleep);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
        
        //instance 1 (simulated): correct
//        String result = restTemplate.getForObject("http://rest-service-end:16000/process_end_update?name=p1&priceChange="+priceChange, String.class);
        String result = "";
        if(Math.random()<0.5){
    		result = restTemplate.getForObject("http://rest-service-5:16005/process5_update_1?name=p1&priceChange="+priceChange, String.class);
    	}else{
    		result = restTemplate.getForObject("http://rest-service-5:16005/process5_update_2?name=p1&priceChange="+priceChange, String.class);
    	}
        
        if(result.contains("price changed") || result.contains("Empty")){
        	//TODO correct
        }else{
        	double cp = Double.valueOf(result);
        	if(cp == Double.valueOf(price)+priceChange){
        		//TODO correct
        	}else{
        		throw new Exception("update price incorrectly!!!");
        	}
        }
        
		log.info("=============end================");
		return result;
    }
    

    @RequestMapping("/process6_async_scaling")
    public String process6_async_scaling()  throws Exception{

    	int priceChange = 1;

        //refreshdb
        Future<String> task = asyncTask.doAsyncQuery_end();
        String price = task.get();
        
        //simulate extra instance update action
        Future<String> task_end = asyncTask.doAsyncUpdate_end(priceChange);
        String result_end = task_end.get();
        
        //simulate restart current instance
        long sleep = 12000;
        try {
			Thread.sleep(sleep);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
        
        Future<String> task1 = asyncTask.doAsyncUpdate(priceChange);
        String result = task1.get();
        
        if(result.contains("price changed") || result.contains("Empty")){
        	//TODO
        }else{
        	double cp = Double.valueOf(result);
        	if(cp == Double.valueOf(price)+priceChange){
        		//TODO
        	}else{
        		throw new Exception("update price incorrectly!!!");
        	}
        }
        
		log.info("=============end================");
		return result;
    }
    
}

