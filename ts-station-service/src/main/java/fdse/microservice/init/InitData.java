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
        info.setStayTime(10);
        service.create(info);

        info.setId("shanghaihongqiao");
        info.setName("上海虹桥");
        info.setStayTime(10);
        service.create(info);

        info.setId("taiyuan");
        info.setName("太原");
        info.setStayTime(5);
        service.create(info);

        info.setId("beijing");
        info.setName("北京");
        info.setStayTime(10);
        service.create(info);

        info.setId("nanjing");
        info.setName("南京");
        info.setStayTime(8);
        service.create(info);

        info.setId("shijiazhuang");
        info.setName("石家庄");
        info.setStayTime(8);
        service.create(info);

        info.setId("xuzhou");
        info.setName("徐州");
        info.setStayTime(7);
        service.create(info);


        info.setId("jinan");
        info.setName("济南");
        info.setStayTime(5);
        service.create(info);

        info.setId("hangzhou");
        info.setName("杭州");
        info.setStayTime(9);
        service.create(info);

        info.setId("jiaxingnan");
        info.setName("嘉兴南");
        info.setStayTime(2);
        service.create(info);

        info.setId("zhenjiang");
        info.setName("镇江");
        info.setStayTime(2);
        service.create(info);

        info.setId("wuxi");
        info.setName("无锡");
        info.setStayTime(3);
        service.create(info);

        info.setId("suzhou");
        info.setName("苏州");
        info.setStayTime(3);
        service.create(info);

    }
}
