package travel2.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import travel2.domain.Information;
import travel2.service.Travel2Service;

import java.util.Date;

/**
 * Created by Chenjie Xu on 2017/6/7.
 */
@Component
public class InitData implements CommandLineRunner {

    @Autowired
    Travel2Service service;

    public void run(String... args)throws Exception{
        Information info = new Information();

        info.setTripId("Z1234");
        info.setTrainTypeId("ZhiDa");
        info.setStartingStationId("shanghai");
        info.setStationsId("beijing");
        info.setTerminalStationId("taiyuan");
        info.setStartingTime(new Date("Mon May 04 09:51:52 GMT+0800 2013"));
        info.setEndTime(new Date("Mon May 04 15:51:52 GMT+0800 2013"));
        service.create(info);

        info.setTripId("Z1235");
        info.setTrainTypeId("ZhiDa");
        info.setStartingStationId("shanghai");
        info.setStationsId("beijing");
        info.setTerminalStationId("taiyuan");
        info.setStartingTime(new Date("Mon May 04 11:31:52 GMT+0800 2013"));
        info.setEndTime(new Date("Mon May 04 17:51:52 GMT+0800 2013"));
        service.create(info);

        info.setTripId("Z1236");
        info.setTrainTypeId("ZhiDa");
        info.setStartingStationId("shanghai");
        info.setStationsId("beijing");
        info.setTerminalStationId("taiyuan");
        info.setStartingTime(new Date("Mon May 04 7:05:52 GMT+0800 2013"));
        info.setEndTime(new Date("Mon May 04 12:51:52 GMT+0800 2013"));
        service.create(info);

        info.setTripId("T1235");
        info.setTrainTypeId("TeKuai");
        info.setStartingStationId("nanjing");
        info.setStationsId("shanghaihongqiao");
        info.setTerminalStationId("beijing");
        info.setStartingTime(new Date("Mon May 04 08:31:52 GMT+0800 2013"));
        info.setEndTime(new Date("Mon May 04 17:21:52 GMT+0800 2013"));
        service.create(info);

        info.setTripId("K1345");
        info.setTrainTypeId("KuaiSu");
        info.setStartingStationId("taiyuan");
        info.setStationsId("nanjing");
        info.setTerminalStationId("shanghai");
        info.setStartingTime(new Date("Mon May 04 07:51:52 GMT+0800 2013"));
        info.setEndTime(new Date("Mon May 04 19:59:52 GMT+0800 2013"));
        service.create(info);
    }
}
