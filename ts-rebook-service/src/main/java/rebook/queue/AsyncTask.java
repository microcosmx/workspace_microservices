package rebook.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import rebook.domain.GetTripAllDetailResult;
import rebook.globalValue.GlobalValue;

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
    public void putGetTripAllDetailResultIntoQueue(GetTripAllDetailResult gtdr,int sleepLengh){
        try{
            Thread.sleep(sleepLengh);
        }catch (Exception e){
            e.printStackTrace();
        }
        GlobalValue.offerGtdr(gtdr);
        System.out.println("[Rebook Service][AsyncTask] Put GTDR into queue");
    }
      
}  
