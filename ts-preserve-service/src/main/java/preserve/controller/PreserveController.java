package preserve.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import preserve.domain.*;
import preserve.service.PreserveService;

import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
public class PreserveController {

    @Autowired
    private PreserveService preserveService;

    @Autowired
    private StatusBean statusBean;

    @CrossOrigin(origins = "*")
    @RequestMapping(value="/preserve", method = RequestMethod.POST)
    public OrderTicketsResult preserve(@RequestBody OrderTicketsInfo oti,@CookieValue String loginId,@CookieValue String loginToken) throws Exception {
        System.out.println("[Preserve Service][Preserve] Account " + loginId + " order from " +
            oti.getFrom() + " -----> " + oti.getTo() + " at " + oti.getDate());

        //add this request to the queue of requests
        UUID uuid = UUID.randomUUID();
        PreserveNode pn = new PreserveNode(uuid, loginId);
        statusBean.chartMsgs.add(pn);

        Future<OrderTicketsResult> otr = preserveService.preserve(oti,loginId,loginToken);
        //wait the task done
        while(true) {
            if(otr.isDone()) {
                System.out.println("------preserveService uuid = " + uuid  +  " done--------");
                break;
            }
            Thread.sleep(300);
        }

        OrderTicketsResult result = otr.get();
        int index = statusBean.chartMsgs.indexOf(pn);
        //some error happened that beyond image
        if(index < 0){
            statusBean.chartMsgs.remove(pn);
            System.out.println("-----cannot find the current preserve node.------");
            throw new Exception("cannot find the current preserve node.");
        } else {
            //check if there exists any request from the same loginId that haven't return
            for(int i = 0; i < index; i++){
               if(statusBean.chartMsgs.get(i).getLoginId().equals(loginId)){
                   statusBean.chartMsgs.remove(pn);
                   System.out.println("-----This OrderTicketsResult return before the last loginId request.------");
                   throw new Exception("This OrderTicketsResult return before the last loginId request.");
               }
            }
        }

        System.out.println("-----This OrderTicketsResult return in the sequence.------");
        statusBean.chartMsgs.remove(pn);

        return result;
    }

}
