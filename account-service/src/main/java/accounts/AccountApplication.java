package accounts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
//@EnableDiscoveryClient

public class AccountApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(AccountApplication.class, args);
    }
}
