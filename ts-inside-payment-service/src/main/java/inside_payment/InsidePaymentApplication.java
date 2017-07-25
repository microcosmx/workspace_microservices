package inside_payment;

import inside_payment.service.InsidePaymentService;
import inside_payment.service.InsidePaymentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.SpanAdjuster;

/**
 * Created by Administrator on 2017/6/20.
 */
@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableAsync
@IntegrationComponentScan
public class InsidePaymentApplication {

    @Autowired
    InsidePaymentServiceImpl insidePaymentServiceImpl;

    public static void main(String[] args) {
        SpringApplication.run(InsidePaymentApplication.class, args);
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
                        .tag("controller_state", "tempAddMoney:" + (insidePaymentServiceImpl.tempAddMoney.equals("0")?false:true))
                        //.name(span.getName() + "::" + greetingController.mytoken)
                        .build();
            }
        };
    }
}