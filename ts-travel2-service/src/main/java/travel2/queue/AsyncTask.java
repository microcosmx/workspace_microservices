package travel2.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import travel2.domain.GetTripAllDetailInfo;
import travel2.domain.GetTripAllDetailResult;
import travel2.service.Travel2Service;

/** 
 * Asynchronous Tasks 
 * @author Xu 
 * 
 */  
@Component  
public class AsyncTask {

    @Autowired
    private MsgSendingBean sendingBean;

    @Autowired
    private Travel2Service service;
    
    @Async("mySimpleAsync")
    public void asyncSearchTripAllDetailInfo(GetTripAllDetailInfo gtdi){
        System.out.println("[Travel Other Service][AsyncTask] Query for GTDI");
    	GetTripAllDetailResult gtdr = service.getTripAllDetailInfo(gtdi);
        sendingBean.returnTravel2DetailInfoResult(gtdr);
    }
      
}  
