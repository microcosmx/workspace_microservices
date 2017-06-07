package travel.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import travel.domain.Information;
import travel.service.TravelService;

import java.util.Date;

/**
 * Created by Chenjie Xu on 2017/6/5.
 */
@Component
public class InitData implements CommandLineRunner{

    @Autowired
    TravelService service;

    public void run(String... args)throws Exception{
        Information info = new Information();

        info.setTripId("G1234");
        info.setTrainTypeId("GaoTieOne");
        info.setStartingStation("ShangHai");
        info.setStations("BeiJing");
        info.setTerminalStation("TaiYuan");
        info.setStartingTime(new Date("Mon May 04 09:51:52 CDT 2013"));
        info.setEndTime(new Date("Mon May 04 15:51:52 CDT 2013"));
        service.create(info);

        info.setTripId("G1235");
        info.setTrainTypeId("GaoTieTwo");
        info.setStartingStation("NanJing");
        info.setStations("ShangHaiHongQiao");
        info.setTerminalStation("BeiJing");
        info.setStartingTime(new Date("Mon May 04 08:31:52 CDT 2013"));
        info.setEndTime(new Date("Mon May 04 17:21:52 CDT 2013"));
        service.create(info);

        info.setTripId("D1345");
        info.setTrainTypeId("DongCheOne");
        info.setStartingStation("TaiYua ");
        info.setStations("NanJing");
        info.setTerminalStation("ShangHai");
        info.setStartingTime(new Date("Mon May 04 07:51:52 CDT 2013"));
        info.setEndTime(new Date("Mon May 04 19:59:52 CDT 2013"));
        service.create(info);
    }
}
