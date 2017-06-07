package travel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import travel.domain.Information;
import travel.service.TravelService;
import travel.service.TravelServiceImpl;

/**
 * Created by Chenjie Xu on 2017/5/9.
 */
@SpringBootApplication
public class TravelApplication {
    public static void main(String[] args) {
        SpringApplication.run(TravelApplication.class, args);
    }
}
