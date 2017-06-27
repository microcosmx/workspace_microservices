package hello;

import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


import java.util.UUID;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;


@RestController
public class GreetingController {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

    private final AtomicLong counter = new AtomicLong();
    
    @Autowired
	private RestTemplate restTemplate;
    
    @Autowired
	private StringRedisTemplate template;

    @RequestMapping("/greeting")
    public String greeting(HttpSession session, 
    		@RequestHeader(value="user-token",required=false) String token, 
    		@RequestParam(value="optVal", required=false) String optVal) throws Exception {
        
        
    	ValueOperations<String, String> ops = this.template.opsForValue();
		String key = token != null ? token : UUID.randomUUID().toString();
		if(optVal != null){
			ops.set(key, optVal);
		}
		System.out.println("Found key " + key + ", value=" + ops.get(key));
        
        return ops.get(key);
    }
    
    
}
