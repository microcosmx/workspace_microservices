package hello;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
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

    @RequestMapping("/hello4")
    public Value hello4(@RequestParam(value="cal", defaultValue="50") String cal) throws InterruptedException, ExecutionException, TimeoutException {

        double cal2 = (Double.valueOf(cal) + 10)/1.1; 
        log.info(String.valueOf(cal2));
        
        //async messages
        Future<Value> task1 = asyncTask.sendAsyncCal(cal2);
        
        Value value = task1.get(2000, TimeUnit.MILLISECONDS);// 设定在2000毫秒的时间内完成
//		task1.cancel(true);

		log.info(value.toString());
		return value;
    }
}
