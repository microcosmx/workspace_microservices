package preserve;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;
import preserve.domain.StatusBean;

import java.util.concurrent.Executor;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableAsync
@IntegrationComponentScan
public class PreserveApplication implements CommandLineRunner {

    @Autowired
    private StatusBean statusBean;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(PreserveApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public StatusBean statusBean() {
        return new StatusBean();
    }

    @Override
    public void run(String... arg0) throws Exception {
        // TODO Auto-generated method stub
        statusBean.chartMsgs.clear();
    }

}
