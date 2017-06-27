package hello;

import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpSession;

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
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;

import java.util.UUID;
import java.util.concurrent.Future;

@RestController
public class HelloController {
    
    private static final Logger log = LoggerFactory.getLogger(Application.class);
    @Autowired
	private RestTemplate restTemplate;
    
    @Autowired
	private StringRedisTemplate template;
    
    @RequestMapping("/hello3")
    public String hello3(HttpSession session, 
    		@RequestHeader(value="user-token",required=false) String token, 
    		@RequestParam(value="name", defaultValue="service-6") String name, 
    		@RequestParam(value="optVal", required=false) String optVal) throws Exception {
        
        
    	ValueOperations<String, String> ops = this.template.opsForValue();
		String key = token != null ? token : UUID.randomUUID().toString();
		System.out.println("Found key " + key + ", value=" + ops.get(key));
        
		
		String value = "";
		if("service-6".equals(name)){
			HttpHeaders headers = new HttpHeaders();
			headers.add("user-token", key);
			ResponseEntity<String> exchange = restTemplate.exchange("http://rest-service-1:16001/hello1" + (optVal!=null?("?optVal="+optVal):""), 
					HttpMethod.GET,new HttpEntity<Void>(headers), String.class);
			value = exchange.getBody();
		}else if("service-5".equals(name)){
			value = restTemplate.getForObject("http://rest-service-end:16000/greeting" + (optVal!=null?("?optVal="+optVal):""), String.class);
		}else{
			HttpHeaders headers = new HttpHeaders();
			headers.add("user-token", key);
			ResponseEntity<String> exchange = restTemplate.exchange("http://rest-service-2:16002/hello2" + (optVal!=null?("?optVal="+optVal):""), 
					HttpMethod.GET,new HttpEntity<Void>(headers), String.class);
			value = exchange.getBody();
		}
        
		log.info(value.toString());
		return value;
    }    
    
    
}
