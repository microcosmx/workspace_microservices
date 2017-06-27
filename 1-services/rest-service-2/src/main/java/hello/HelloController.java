package hello;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServletServerHttpRequest;

import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class HelloController {
    
    private static final Logger log = LoggerFactory.getLogger(Application.class);
    @Autowired
	private RestTemplate restTemplate;
    
    @Autowired
	private StringRedisTemplate template;

    @RequestMapping("/hello2")
    public String hello2(HttpSession session, 
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
