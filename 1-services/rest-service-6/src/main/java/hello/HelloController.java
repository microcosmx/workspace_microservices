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
    
    @Autowired
	private StatusBean statusBean;

    @RequestMapping("/hello6")
    public String hello6(@RequestParam(value="cal", defaultValue="50") String cal)  throws Exception{

        double cal2 = Math.abs(Double.valueOf(cal));
        log.info(String.valueOf(cal2));
        
        statusBean.chartMsgs.clear();
        
        //async messages
        Future<String> msg1 = asyncTask.sendAsyncMessage1("msg1");
        Future<String> msg2 = asyncTask.sendAsyncMessage2("msg2");
        
        //async tasks
        Future<String> task1 = asyncTask.doAsyncTask1("task1");
        Future<String> task2 = asyncTask.doAsyncTask2("task2");
        
//        while(true) {  
//            if(task1.isDone() && task2.isDone()) {  
//                log.info("------------Task1 result: {}", task1.get());
//                log.info("------------Task2 result: {}", task2.get());
//                break;  
//            }  
//            Thread.sleep(300);  
//        }  
//        log.info("All tasks finished.");  
        
        //simulate heavy tasks
        long sleep = (long) (Math.random() * 600);
        try {
			Thread.sleep(sleep);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
        
        int index1 = statusBean.chartMsgs.indexOf("msg1");
        int index2 = statusBean.chartMsgs.indexOf("msg2");
        int index3 = statusBean.chartMsgs.indexOf("task1");
        int index4 = statusBean.chartMsgs.indexOf("task2");
        
        log.info(statusBean.chartMsgs.toString());
        log.info("=========" + index1 + "===" + index2 + "===" + index3 + "===" + index4);
        
        
        //match sequence first
        if(index4>-1 && index1>index4){
        	//60% chance error
        	if(Math.random() < 0.6){
        		throw new Exception("chart msg in wrong sequence");
        	}
    	}
        
		log.info("=============end================");
		return statusBean.chartMsgs.toString();
    }
    
    @RequestMapping("/hello6_1")
    public String hello6_1(@RequestParam(value="msg", defaultValue="") String msg) {
    	statusBean.chartMsgs.add(msg);
    	return "callback completed";
    }
    
    @RequestMapping("/hello6_2")
    public String hello6_2(@RequestParam(value="msg", defaultValue="") String msg) {
    	statusBean.chartMsgs.add(msg);
    	return "callback completed";
    }
}

