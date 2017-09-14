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
        info.setStartingStationId("shanghai");
        info.setStationsId("beijing");
        info.setTerminalStationId("taiyuan");
        info.setStartingTime(new Date("Mon May 04 09:00:00 GMT+0800 2013"));
        info.setEndTime(new Date("Mon May 04 15:51:52 GMT+0800 2013"));
        service.create(info);

        info.setTripId("G1235");
        info.setTrainTypeId("GaoTieOne");
        info.setStartingStationId("shanghai");
        info.setStationsId("beijing");
        info.setTerminalStationId("taiyuan");
        info.setStartingTime(new Date("Mon May 04 12:00:00 GMT+0800 2013"));
        info.setEndTime(new Date("Mon May 04 17:51:52 GMT+0800 2013"));
        service.create(info);

        info.setTripId("G1236");
        info.setTrainTypeId("GaoTieOne");
        info.setStartingStationId("shanghai");
        info.setStationsId("beijing");
        info.setTerminalStationId("taiyuan");
        info.setStartingTime(new Date("Mon May 04 14:00:00 GMT+0800 2013"));
        info.setEndTime(new Date("Mon May 04 20:51:52 GMT+0800 2013"));
        service.create(info);

        info.setTripId("G1237");
        info.setTrainTypeId("GaoTieTwo");
        info.setStartingStationId("nanjing");
        info.setStationsId("shanghaihongqiao");
        info.setTerminalStationId("beijing");
        info.setStartingTime(new Date("Mon May 04 08:00:00 GMT+0800 2013"));
        info.setEndTime(new Date("Mon May 04 17:21:52 GMT+0800 2013"));
        service.create(info);

        info.setTripId("D1345");
        info.setTrainTypeId("DongCheOne");
        info.setStartingStationId("taiyuan");
        info.setStationsId("nanjing");
        info.setTerminalStationId("shanghai");
        info.setStartingTime(new Date("Mon May 04 07:00:00 GMT+0800 2013"));
        info.setEndTime(new Date("Mon May 04 19:59:52 GMT+0800 2013"));
        service.create(info);
    }
}
