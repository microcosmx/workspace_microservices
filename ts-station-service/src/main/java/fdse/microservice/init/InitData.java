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

        info.setName("ShangHai");
        service.create(info);

        info.setName("ShangHaiHongQiao");
        service.create(info);

        info.setName("TaiYuan");
        service.create(info);

        info.setName("BeiJing");
        service.create(info);

        info.setName("NanJing");
        service.create(info);
    }
}
