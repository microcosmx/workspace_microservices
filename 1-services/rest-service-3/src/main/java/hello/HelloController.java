package hello;

import java.util.concurrent.Future;

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
    private RestbackService restbackService;

    @RequestMapping("/hello3")
    public String hello3(@RequestParam(value="cal", defaultValue="50") String cal) throws Exception {

        double cal2 = Math.abs(Double.valueOf(cal)); 
        log.info(String.valueOf(cal2));

        String value = null;
        if(cal2 < 60){
            value = restTemplate.getForObject("http://rest-service-2:16002/hello2?cal="+cal2, String.class);
        }else if(cal2 < 100){
        	value = restTemplate.getForObject("http://rest-service-1:16001/hello1?cal="+cal2, String.class);
        }else{
        	// throw new Exception("unexpected input scope");
            value = restTemplate.getForObject("http://rest-service-end:16000/greeting?cal="+cal2, String.class);
        }
        
		log.info(value.toString());
        
		return value;
    }
    
    
}
