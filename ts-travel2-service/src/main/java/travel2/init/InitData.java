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
        info.setStartingStation("ShangHai");
        info.setStations("BeiJing");
        info.setTerminalStation("TaiYuan");
        info.setStartingTime(new Date("Mon May 04 09:51:52 CDT 2013"));
        info.setEndTime(new Date("Mon May 04 15:51:52 CDT 2013"));
        service.create(info);

        info.setTripId("Z1235");
        info.setTrainTypeId("ZhiDa");
        info.setStartingStation("ShangHai");
        info.setStations("BeiJing");
        info.setTerminalStation("TaiYuan");
        info.setStartingTime(new Date("Mon May 04 11:31:52 CDT 2013"));
        info.setEndTime(new Date("Mon May 04 17:51:52 CDT 2013"));
        service.create(info);

        info.setTripId("Z1236");
        info.setTrainTypeId("ZhiDa");
        info.setStartingStation("ShangHai");
        info.setStations("BeiJing");
        info.setTerminalStation("TaiYuan");
        info.setStartingTime(new Date("Mon May 04 7:05:52 CDT 2013"));
        info.setEndTime(new Date("Mon May 04 12:51:52 CDT 2013"));
        service.create(info);

        info.setTripId("T1235");
        info.setTrainTypeId("TeKuai");
        info.setStartingStation("NanJing");
        info.setStations("ShangHaiHongQiao");
        info.setTerminalStation("BeiJing");
        info.setStartingTime(new Date("Mon May 04 08:31:52 CDT 2013"));
        info.setEndTime(new Date("Mon May 04 17:21:52 CDT 2013"));
        service.create(info);

        info.setTripId("K1345");
        info.setTrainTypeId("KuaiSu");
        info.setStartingStation("TaiYuan");
        info.setStations("NanJing");
        info.setTerminalStation("ShangHai");
        info.setStartingTime(new Date("Mon May 04 07:51:52 CDT 2013"));
        info.setEndTime(new Date("Mon May 04 19:59:52 CDT 2013"));
        service.create(info);
    }
}
