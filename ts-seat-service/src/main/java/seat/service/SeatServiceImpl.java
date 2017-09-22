package seat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import seat.domain.LeftTicketInfo;
import seat.domain.SeatRequest;

import java.util.Random;
import java.util.Set;

@Service
public class SeatServiceImpl implements SeatService {
    @Autowired
    RestTemplate restTemplate;

    @Override
    public int distributeSeat(SeatRequest seatRequest){

        //调用微服务，查询获得余票信息：该车次指定座型总数量以及已售车票的set集合
        LeftTicketInfo leftTicketInfo = restTemplate.postForObject(
                "http://ts-order-service:12031/order/calculate : to modify", seatRequest ,LeftTicketInfo.class);

        //随机分配座位
        Random rand = new Random();
        int range = leftTicketInfo.getSeatNum();
        Set<Integer> soldTickets = leftTicketInfo.getSoldTickets();
        int seat = rand.nextInt(range) + 1;
        while (soldTickets.contains(seat)){
            seat = rand.nextInt(range) + 1;
        }

        return seat;
    }
}
