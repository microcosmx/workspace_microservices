package train.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import train.domain.Information;
import train.service.TrainService;

/**
 * Created by Chenjie Xu on 2017/6/5.
 */
@Component
public class InitData implements CommandLineRunner{
    @Autowired
    TrainService service;

    @Override
    public void run(String... args) throws Exception {
        Information info = new Information();

        info.setId("高铁1号");
        info.setConfortClass(60);
        info.setEconomyClass(120);
        service.create(info);

        info.setId("高铁2号");
        info.setConfortClass(80);
        info.setEconomyClass(200);
        service.create(info);

        info.setId("动车1号");
        info.setConfortClass(100);
        info.setEconomyClass(300);
        service.create(info);
        System.out.println("after init data");
    }
}
