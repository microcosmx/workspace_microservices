package hello;

import hello.async.AsyncTask;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.concurrent.Future;

@RestController
public class HelloController {

	@Autowired
	private AsyncTask asyncTask;


	@RequestMapping(value="/hello", method = RequestMethod.GET)
	public String hello(@RequestHeader HttpHeaders headers) throws Exception {

		headers.add("ISTIO_TEST_FROM_REST_SERVICE_1", "jichao");

		Future<String> taskSayHelloOnce = asyncTask.sayHelloAsyncSubOne(headers);

		Future<String> taskSayHelloTwice = asyncTask.sayHelloAsyncSubTwo(headers);

		Future<String> taskSayHelloThird = asyncTask.sayHelloAsyncSubThree(headers);

		String str2 = "";

		boolean one = false;
		boolean two = false;
		boolean three = false;

		while(true){
			if(taskSayHelloOnce.isDone() == true && one == false){
				one = true;
				str2 += "| taskSayHelloOnce |";
				System.out.println("[=====] Service One Complete");
			}
			if(taskSayHelloTwice.isDone() == true && two == false){
				two = true;
				str2 += "| taskSayHelloTwice |";
				System.out.println("[=====] Service Two Complete");
			}
			if(taskSayHelloThird.isDone() == true && three == false){
				three = true;
				str2 += "| taskSayHelloThird |";
				System.out.println("[=====] Service Three Complete");

			}
			if(one && two && three){
				break;
			}
		}

//		HttpEntity requestEntity = new HttpEntity(null,headers);
//		ResponseEntity<String> re = restTemplate.exchange(
//				"http://rest-service-2:16002/hello",
//				HttpMethod.GET,
//				requestEntity,
//				String.class);
//
//		String str2 = re.getBody();
//		String str2 = restTemplate.getForObject("http://rest-service-2:16002/hello",String.class);
		return str2 + " | Hello From Rest-Service-1";
	}


	@RequestMapping(value="/hello2", method = RequestMethod.GET)
	public String hello2(@RequestHeader HttpHeaders headers) throws Exception {

		headers.add("ISTIO_TEST_FROM_REST_SERVICE_1", "jichao");

		Future<String> taskSayHelloOnce = asyncTask.sayHelloAsyncSubOneWithoutTag(headers);

		Future<String> taskSayHelloTwice = asyncTask.sayHelloAsyncSubTwoWithoutTag(headers);

		Future<String> taskSayHelloThird = asyncTask.sayHelloAsyncSubThreeWithoutTag(headers);

		String str2 = "";

		boolean one = false;
		boolean two = false;
		boolean three = false;

		while(true){
			if(taskSayHelloOnce.isDone() == true && one == false){
				one = true;
				str2 += "| taskSayHelloOnce |";
				System.out.println("[=====] Service One Complete");
			}
			if(taskSayHelloTwice.isDone() == true && two == false){
				two = true;
				str2 += "| taskSayHelloTwice |";
				System.out.println("[=====] Service Two Complete");
			}
			if(taskSayHelloThird.isDone() == true && three == false){
				three = true;
				str2 += "| taskSayHelloThird |";
				System.out.println("[=====] Service Three Complete");

			}
			if(one && two && three){
				break;
			}
		}

//		HttpEntity requestEntity = new HttpEntity(null,headers);
//		ResponseEntity<String> re = restTemplate.exchange(
//				"http://rest-service-2:16002/hello",
//				HttpMethod.GET,
//				requestEntity,
//				String.class);
//
//		String str2 = re.getBody();
//		String str2 = restTemplate.getForObject("http://rest-service-2:16002/hello",String.class);
		return str2 + " | Hello From Rest-Service-1";
	}
}
