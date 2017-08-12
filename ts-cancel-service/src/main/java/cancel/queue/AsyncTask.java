package cancel.queue;

import cancel.domain.ChangeOrderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component  
public class AsyncTask {

    @Autowired
    private MsgSendingBean sendingBean;

    @Async("mySimpleAsync")
    public void asyncSendLoginInfoDataToSso(ChangeOrderInfo info){
        System.out.println("[Cancel Service][Async Send Login Info]");
        sendingBean.sendCancelInfoToOrderOther(info);
    }
      
}  
