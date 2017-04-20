package register;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class RegisterApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(RegisterApplication.class, args);
    }

}
