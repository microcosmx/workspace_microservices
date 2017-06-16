package price.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import price.domain.CreateInfo;
import price.service.PriceService;


/**
 * Created by Chenjie Xu on 2017/6/12.
 */
@Component
public class InitData implements CommandLineRunner {

    @Autowired
    PriceService service;

    public void run(String... args)throws Exception{
        CreateInfo info = new CreateInfo();

        info.setPlaceA("shanghai");
        info.setPlaceB("beijing");
        info.setDistance(100);
        service.create(info);

        info.setPlaceA("shanghai");
        info.setPlaceB("taiyuan");
        info.setDistance(300);
        service.create(info);

        info.setPlaceA("beijing");
        info.setPlaceB("taiyuan");
        info.setDistance(200);
        service.create(info);

        info.setPlaceA("nanjing");
        info.setPlaceB("taiyuan");
        info.setDistance(50);
        service.create(info);

        info.setPlaceA("nanjing");
        info.setPlaceB("shanghai");
        info.setDistance(50);
        service.create(info);

        info.setPlaceA("nanjing");
        info.setPlaceB("shanghaihongqiao");
        info.setDistance(50);
        service.create(info);

        info.setPlaceA("beijing");
        info.setPlaceB("shanghaihongqiao");
        info.setDistance(100);
        service.create(info);
    }
}
