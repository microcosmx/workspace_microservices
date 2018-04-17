package hello.async;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.concurrent.Future;

@Component  
public class AsyncTask {
    
    @Autowired
	private RestTemplate restTemplate;

    @Async("myAsync")
    public Future<String> sayHelloAsync(HttpHeaders headers) throws InterruptedException{

        HttpEntity requestEntity = new HttpEntity(null,headers);
        System.out.println("==========Async Task===============");
        ResponseEntity<String> re = restTemplate.exchange(
                "http://rest-service-2:16002/hello",
                HttpMethod.GET,
                requestEntity,
                String.class);
        String str2 = re.getBody();

        return new AsyncResult<>(str2);
    }

    @Async("myAsync")
    public Future<String> sayHelloAsyncSubOne(HttpHeaders headers) throws InterruptedException{

        headers.add("Cookie","jichao=dododo");
        //headers.add("AsyncJichao","asyncJichao");
        HttpEntity requestEntity = new HttpEntity(null,headers);
        System.out.println("==========Async Task===============");
        ResponseEntity<String> re = restTemplate.exchange(
                "http://rest-service-sub-1:16101/helloRestServiceSubOne",
                HttpMethod.GET,
                requestEntity,
                String.class);
        String str2 = re.getBody();

        return new AsyncResult<>(str2);
    }

    @Async("myAsync")
    public Future<String> sayHelloAsyncSubOneWithoutTag(HttpHeaders headers) throws InterruptedException{

        HttpEntity requestEntity = new HttpEntity(null,headers);
        System.out.println("==========Async Task===============");
        ResponseEntity<String> re = restTemplate.exchange(
                "http://rest-service-sub-1:16101/helloRestServiceSubOne",
                HttpMethod.GET,
                requestEntity,
                String.class);
        String str2 = re.getBody();

        return new AsyncResult<>(str2);
    }

    @Async("myAsync")
    public Future<String> sayHelloAsyncSubTwo(HttpHeaders headers) throws InterruptedException{

        headers.add("Cookie","jichao=dododo");
        HttpEntity requestEntity = new HttpEntity(null,headers);
        System.out.println("==========Async Task===============");
        ResponseEntity<String> re = restTemplate.exchange(
                "http://rest-service-sub-2:16102/helloRestServiceSubTwo",
                HttpMethod.GET,
                requestEntity,
                String.class);
        String str2 = re.getBody();

        return new AsyncResult<>(str2);
    }

    @Async("myAsync")
    public Future<String> sayHelloAsyncSubTwoWithoutTag(HttpHeaders headers) throws InterruptedException{

        HttpEntity requestEntity = new HttpEntity(null,headers);
        System.out.println("==========Async Task===============");
        ResponseEntity<String> re = restTemplate.exchange(
                "http://rest-service-sub-2:16102/helloRestServiceSubTwo",
                HttpMethod.GET,
                requestEntity,
                String.class);
        String str2 = re.getBody();

        return new AsyncResult<>(str2);
    }

    @Async("myAsync")
    public Future<String> sayHelloAsyncSubThree(HttpHeaders headers) throws InterruptedException{

        headers.add("Cookie","jichao=dododo");
        HttpEntity requestEntity = new HttpEntity(null,headers);
        System.out.println("==========Async Task===============");
        ResponseEntity<String> re = restTemplate.exchange(
                "http://rest-service-sub-3:16103/helloRestServiceSubThree",
                HttpMethod.GET,
                requestEntity,
                String.class);
        String str2 = re.getBody();

        return new AsyncResult<>(str2);
    }

    @Async("myAsync")
    public Future<String> sayHelloAsyncSubThreeWithoutTag(HttpHeaders headers) throws InterruptedException{

        HttpEntity requestEntity = new HttpEntity(null,headers);
        System.out.println("==========Async Task===============");
        ResponseEntity<String> re = restTemplate.exchange(
                "http://rest-service-sub-3:16103/helloRestServiceSubThree",
                HttpMethod.GET,
                requestEntity,
                String.class);
        String str2 = re.getBody();

        return new AsyncResult<>(str2);
    }

}  
