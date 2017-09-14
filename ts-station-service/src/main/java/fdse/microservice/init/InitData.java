package fdse.microservice.init;

import fdse.microservice.domain.Information;
import fdse.microservice.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created by Chenjie Xu on 2017/6/5.
 */
@Component
public class InitData implements CommandLineRunner {
    @Autowired
    StationService service;

    @Override
    public void run(String... args) throws Exception{
        Information info = new Information();

        info.setId("shanghai");
        info.setName("Shang Hai");
        service.create(info);

        info.setId("shanghaihongqiao");
        info.setName("Shang Hai Hong Qiao");
        service.create(info);

        info.setId("taiyuan");
        info.setName("Tai Yuan");
        service.create(info);

        info.setId("beijing");
        info.setName("Bei Jing");
        service.create(info);

        info.setId("nanjing");
        info.setName("Nan Jing");
        service.create(info);
    }
}
