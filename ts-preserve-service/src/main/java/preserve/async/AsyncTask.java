package preserve.async;

import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import preserve.domain.GetTripAllDetailInfo;
import preserve.domain.GetTripAllDetailResult;

/** 
 * Asynchronous Tasks 
 * @author Xu 
 * 
 */  
@Component  
public class AsyncTask {  
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());  
    
    @Autowired
	private RestTemplate restTemplate;
    
    @Async("mySimpleAsync")
    public Future<String> sendAsyncUpdate1(String oldState, String newState) throws InterruptedException{
        String result = restTemplate.getForObject("http://rest-service-5:16005/hello5_1?oldState="+oldState+"&newState="+newState, String.class);
        return new AsyncResult<>("update1 sended");
    }
    @Async("mySimpleAsync")
    public Future<String> sendAsyncUpdate2(String oldState, String newState) throws InterruptedException{
        String result = restTemplate.getForObject("http://rest-service-5:16005/hello5_1?oldState="+oldState+"&newState="+newState, String.class);
        return new AsyncResult<>("update2 sended");
    }

    @Async("mySimpleAsync")
    public Future<GetTripAllDetailResult> getTripAllDetailInformation(GetTripAllDetailInfo gtdi){
        System.out.println("[Preserve Service][Get Trip All Detail Information] Getting....");
        GetTripAllDetailResult gtdr = restTemplate.postForObject(
                "http://ts-travel-service:12346/travel/getTripAllDetailInfo/"
                ,gtdi,GetTripAllDetailResult.class);
        return new AsyncResult<>(gtdr);
    }
    
    
      
    @Async("myAsync")  
    public Future<String> doAsyncQuery(String lastName) throws InterruptedException{  
        String result = restTemplate.getForObject("http://rest-service-5:16005/hello5_2?lastName="+lastName, String.class);
        return new AsyncResult<>(result);  
    }  
      
}  
