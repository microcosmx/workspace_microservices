package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
public class HelloController {

    @Autowired
	private RestTemplate restTemplate;

    @RequestMapping(value="/helloRestServiceSubThree", method = RequestMethod.GET)
    public String hello2(@RequestHeader HttpHeaders httpHeaders) throws Exception{

        Map<String,String> headerList = httpHeaders.toSingleValueMap();
        System.out.println("[----------Print Header----------]");
        for(Map.Entry<String,String> entry : headerList.entrySet()){
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }

        Thread.sleep(50);

        return "Hello From Rest-Service-Sub-3";
    }
}
