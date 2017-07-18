package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.SpanAdjuster;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.util.concurrent.Executor;

import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.integration.annotation.IntegrationComponentScan;

@SpringBootApplication

@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableAsync
@IntegrationComponentScan
public class Application extends AsyncConfigurerSupport {

	private static final Logger log = LoggerFactory.getLogger(Application.class);
	
	@Autowired
	private HelloController helloController;

	public static void main(String args[]) {
		SpringApplication.run(Application.class);
	}
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	
	@Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("restback-");
        executor.initialize();
        return executor;
    }
	
	@Bean
	public SpanAdjuster spanCollector() {
		return new SpanAdjuster() {
			@Override 
			public Span adjust(Span span) {
				System.out.println(span.tags());
				return span.toBuilder()
						.tag("controller_state", "=" + span.tags().get("http.host") + "=" + helloController.mytoken)
						//.name(span.getName() + "::" + helloController.mytoken)
						.build();
			}
		};
	}
}