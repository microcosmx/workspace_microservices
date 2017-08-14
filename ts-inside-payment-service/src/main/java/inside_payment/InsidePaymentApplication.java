package inside_payment;

import inside_payment.async.AsyncTask;
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
import org.springframework.web.client.RestTemplate;

/**
 * Created by Administrator on 2017/6/20.
 */
@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableAsync
@IntegrationComponentScan
public class InsidePaymentApplication {

    @Autowired
    AsyncTask asyncTask;

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
//                if(asyncTask == null){
//                    System.out.println("asyncTask:"+null);
//                }
//                if(asyncTask.equal == null){
//                    System.out.println("asyncTask.equal:"+null);
//                }
//                System.out.println("asyncTask.equal.get()" + asyncTask.equal.get());
                return span.toBuilder()
                        .tag("controller_state", asyncTask.equal.get()==1?"equal":"notEqual")
                        //.name(span.getName() + "--------------------")
                        .build();
            }
        };
    }
}