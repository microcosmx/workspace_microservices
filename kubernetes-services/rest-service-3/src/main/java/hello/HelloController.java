package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HelloController {

	@Autowired
	private RestTemplate restTemplate;


	@RequestMapping(value="/hello", method = RequestMethod.GET)
	public String hello(@RequestHeader HttpHeaders headers) {

		headers.add("ISTIO_TEST_FROM_REST_SERVICE_3", "jichao");
		HttpEntity requestEntity = new HttpEntity(null,headers);
		ResponseEntity<String> re = restTemplate.exchange(
				"http://rest-service-1:16001/hello",
				HttpMethod.GET,
				requestEntity,
				String.class);

		String str3 = re.getBody();
//		String str3 = restTemplate.getForObject("http://rest-service-1:16001/hello",String.class);
		return str3 + " | Hello From Rest-Service-3";
	}

	@RequestMapping(value="/helloDirectly", method = RequestMethod.GET)
	public String helloDirectly(@RequestHeader HttpHeaders headers) {

		headers.add("ISTIO_TEST_FROM_REST_SERVICE_3_Directly", "jichao");
		HttpEntity requestEntity = new HttpEntity(null,headers);
		ResponseEntity<String> re = restTemplate.exchange(
				"http://rest-service-sub-1:16101/helloRestServiceSubOne",
				HttpMethod.GET,
				requestEntity,
				String.class);

		String str3 = re.getBody();
//		String str3 = restTemplate.getForObject("http://rest-service-1:16001/hello",String.class);
		return str3 + " | Hello From Rest-Service-3-Directly";
	}
}
