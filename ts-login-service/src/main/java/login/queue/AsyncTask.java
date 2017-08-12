package login.queue;

import login.domain.LoginInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

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
    public void asyncSendLoginInfoDataToSso(LoginInfo loginInfo){
        System.out.println("[Login Service][Async Send Login Info]");
        sendingBean.sendLoginInfoToSso(loginInfo);
    }
      
}  
