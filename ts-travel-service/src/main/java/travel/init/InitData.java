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
        info.setTrainTypeId("高铁1号");
        info.setStartingStation("上海");
        info.setStations("北京");
        info.setTerminalStation("太原");
        info.setStartingTime(new Date("Mon May 04 09:51:52 CDT 2013"));
        info.setEndTime(new Date("Mon May 04 15:51:52 CDT 2013"));
        service.create(info);

        info.setTripId("G1235");
        info.setTrainTypeId("高铁2号");
        info.setStartingStation("南京");
        info.setStations("上海虹桥");
        info.setTerminalStation("北京");
        info.setStartingTime(new Date("Mon May 04 08:31:52 CDT 2013"));
        info.setEndTime(new Date("Mon May 04 17:21:52 CDT 2013"));
        service.create(info);

        info.setTripId("D1345");
        info.setTrainTypeId("动车1号");
        info.setStartingStation("太原");
        info.setStations("南京");
        info.setTerminalStation("上海");
        info.setStartingTime(new Date("Mon May 04 07:51:52 CDT 2013"));
        info.setEndTime(new Date("Mon May 04 19:59:52 CDT 2013"));
        service.create(info);
    }
}
