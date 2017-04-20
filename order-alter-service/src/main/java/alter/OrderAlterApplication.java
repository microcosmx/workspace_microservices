package alter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OrderAlterApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(OrderAlterApplication.class, args);
    }
}
