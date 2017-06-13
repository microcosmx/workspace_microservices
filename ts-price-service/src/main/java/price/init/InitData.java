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

        info.setPlaceA("ShangHai");
        info.setPlaceB("BeiJing");
        info.setDistance(100);
        service.create(info);

        info.setPlaceA("ShangHai");
        info.setPlaceB("TaiYuan");
        info.setDistance(300);
        service.create(info);

        info.setPlaceA("BeiJing");
        info.setPlaceB("TaiYuan");
        info.setDistance(200);
        service.create(info);

        info.setPlaceA("Nanjing");
        info.setPlaceB("TaiYuan");
        info.setDistance(50);
        service.create(info);

        info.setPlaceA("Nanjing");
        info.setPlaceB("ShangHai");
        info.setDistance(50);
        service.create(info);

        info.setPlaceA("Nanjing");
        info.setPlaceB("ShangHaiHongQiao");
        info.setDistance(50);
        service.create(info);

        info.setPlaceA("BeiJing");
        info.setPlaceB("ShangHaiHongQiao");
        info.setDistance(100);
        service.create(info);
    }
}
