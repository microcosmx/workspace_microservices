package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.SpanAdjuster;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.messaging.MessageChannel;

@SpringBootApplication

@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableAsync
@IntegrationComponentScan
public class Application {
	
	@Autowired
	private GreetingController greetingController;
	
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
    
    @Bean
	public SpanAdjuster spanCollector() {
		return new SpanAdjuster() {
			@Override 
			public Span adjust(Span span) {
				System.out.println(span.tags());
				return span.toBuilder()
						.tag("controller_state", "=" + span.tags().get("http.host") + "=" + greetingController.mytoken)
						//.name(span.getName() + "::" + greetingController.mytoken)
						.build();
			}
		};
	}
    
}
