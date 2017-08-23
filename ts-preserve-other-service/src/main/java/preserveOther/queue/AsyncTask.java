package preserveOther.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import preserveOther.domain.GetTripAllDetailResult;

/**
 * Asynchronous Tasks 
 * @author Xu 
 * 
 */  
@Component  
public class AsyncTask {

    @Autowired
    private MsgSendingBean sendingBean;

    
    @Async("mySimpleAsync")
    public void putGetTripAllDetailResultIntoQueue(GetTripAllDetailResult gtdr, int sleepLengh){
        GlobalValue.getTripAllDetailResult = gtdr;
        System.out.println("[Rebook Service][AsyncTask] Put GTDR into queue - ");
    }
      
}  
