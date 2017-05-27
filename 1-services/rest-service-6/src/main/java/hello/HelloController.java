package hello;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import hello.Application.ScheduledProducer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;

@RestController
public class HelloController {
    
    private static final Logger log = LoggerFactory.getLogger(Application.class);
    @Autowired
	private RestTemplate restTemplate;
    
    @Autowired
	private RabbitTemplate rabbitTemplate;
    
    @Autowired
	private ScheduledProducer scheduledProducer;

    @RequestMapping("/hello6")
    public Value hello6(@RequestParam(value="cal", defaultValue="50") String cal) {

        double cal2 = Math.abs(Double.valueOf(cal));
        log.info(String.valueOf(cal2));
        
        
        String msg = "";
        
        log.info("--------mq message send 1----------");
        msg = "---------current data----------"+cal2+"----------";
        rabbitTemplate.convertAndSend(Application.queueName, msg);
        
        log.info("--------mq message send 2----------");
        msg = "---------current data 2----------"+(cal2+3)+"----------";
        rabbitTemplate.convertAndSend(Application.queueName, msg);
        
        log.info("--------mq message send 3----------");
        msg = "---------current data 3----------"+(cal2+6)+"----------";
        rabbitTemplate.convertAndSend(Application.queueName, msg);
        
        //scheduler & timer
//        log.info("--------mq message send 1----------");
//        scheduledProducer.sendMessage("---------current data----------"+cal2+"----------");
//        log.info("--------mq message send 2----------");
//        scheduledProducer.sendMessage("---------current data 2----------"+(cal2+3)+"----------");
//        log.info("--------mq message send 3----------");
//        scheduledProducer.sendMessage("---------current data 3----------"+(cal2+6)+"----------");
        

        Value value5 = restTemplate.getForObject("http://rest-service-5:16005/hello5?cal="+cal, Value.class);
        Value value4 = restTemplate.getForObject("http://rest-service-4:16004/hello4?cal="+cal, Value.class);
        
        Value value = null;
        if(cal2 < 30){
            value = restTemplate.getForObject("http://rest-service-5:16005/hello5?cal="+cal2, Value.class);
        }else if(cal2 < 60){
            value = restTemplate.getForObject("http://rest-service-4:16004/hello4?cal="+cal2, Value.class);
        }else{
            value = restTemplate.getForObject("http://rest-service-3:16003/hello3?cal="+cal2, Value.class);
        }
        
        
		log.info(value.toString());
		log.info("=============end================");
		return value;
    }
    
    @RequestMapping("/hello6_1")
    public Boolean hello6_1(@RequestParam(value="cal2", defaultValue="50") String cal2) {
        double calx = Math.abs(Double.valueOf(cal2)*2-100);
        log.info("---------callback--------" + calx);
        if(calx < 60){
        	return true;
        }else{
        	return false;
        }
    }
}
