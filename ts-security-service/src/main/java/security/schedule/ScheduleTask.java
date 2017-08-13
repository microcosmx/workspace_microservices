package security.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Administrator on 2017/8/13.
 */
@Component
public class ScheduleTask {

    @Autowired
    private RestTemplate restTemplate;

    //每天凌晨1点调用inside payment
    @Scheduled(cron="0 0 1 * * ?")
//    @Scheduled(fixedRate = 5000)
    public void callInsidePayment() {
        Boolean result = restTemplate.getForObject("http://ts-inside-payment-service:18673/inside_payment/check",Boolean.class);
    }
}
