package security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.SpanAdjuster;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import security.service.SecurityServiceImpl;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableAsync
@IntegrationComponentScan
@EnableScheduling
public class SecurityApplication {
    @Autowired
    SecurityServiceImpl securityServiceImpl;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SecurityApplication.class, args);
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
                String state;
                if(securityServiceImpl.time.get() >= 4){
                    state = "Check";
                }else{
                    state = "NoCheck";
                }
                return span.toBuilder()
                        .tag("controller_state", state)
                        //.name(span.getName() + "::" + greetingController.mytoken)
                        .build();
            }
        };
    }
}
