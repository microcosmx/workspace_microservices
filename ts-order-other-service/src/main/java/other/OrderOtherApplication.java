package other;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;
import other.service.OrderOtherServiceImpl;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.SpanAdjuster;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableAsync
@IntegrationComponentScan
public class OrderOtherApplication {

    @Autowired
    OrderOtherServiceImpl orderOtherServiceImpl;


    public static void main(String[] args) throws Exception {
        SpringApplication.run(OrderOtherApplication.class, args);
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
                return span.toBuilder()
                        .tag("controller_state", orderServiceImpl.orderNumberInLastOneHour.get() < 5? "<5":"5")
                        //.name(span.getName() + "--------------------")
                        .build();
            }
        };
    }
}
