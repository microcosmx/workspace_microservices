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
        info.setName("上海");
        service.create(info);

        info.setId("shanghaihongqiao");
        info.setName("上海虹桥");
        service.create(info);

        info.setId("taiyuan");
        info.setName("太原");
        service.create(info);

        info.setId("beijing");
        info.setName("北京");
        service.create(info);

        info.setId("nanjing");
        info.setName("南京");
        service.create(info);

        info.setId("shijiazhuang");
        info.setName("石家庄");
        service.create(info);

        info.setId("xuzhou");
        info.setName("徐州");
        service.create(info);


        info.setId("jinan");
        info.setName("济南");
        service.create(info);

        info.setId("hangzhou");
        info.setName("杭州");
        service.create(info);

        info.setId("jiaxingnan");
        info.setName("嘉兴南");
        service.create(info);

        info.setId("zhenjiang");
        info.setName("镇江");
        service.create(info);

        info.setId("wuxi");
        info.setName("无锡");
        service.create(info);

        info.setId("suzhou");
        info.setName("苏州");
        service.create(info);

    }
}
