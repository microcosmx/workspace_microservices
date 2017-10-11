package seat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import seat.domain.LeftTicketInfo;
import seat.domain.SeatRequest;
import seat.domain.Ticket;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Service
public class SeatServiceImpl implements SeatService {
    @Autowired
    RestTemplate restTemplate;

    @Override
    public Ticket distributeSeat(SeatRequest seatRequest){

        //调用微服务，查询获得车次的所有站点信息（区分G和D开头以及其它类型的）
        List stationList = restTemplate.postForObject(
                "http://ts-order-service:12031/order/calculate : to modify", seatRequest.getTrainNumber() ,ArrayList.class);

        //调用微服务，查询该车次指定座型总数量
        LeftTicketInfo leftTicketInfo = restTemplate.postForObject(
                "http://ts-order-service:12031/order/calculate : to modify", seatRequest ,LeftTicketInfo.class);

        //调用微服务，查询获得余票信息：该车次指定座型已售Ticket的set集合（区分G和D开头以及其它类型的）
        int seatTotalNum = restTemplate.postForObject(
                "http://ts-order-service:12031/order/calculate : to modify", seatRequest ,Integer.class);

        //分配座位
        String startStation = seatRequest.getStartStation();
        Ticket ticket = new Ticket();
        ticket.setStartStation(startStation);
        ticket.setDestStation(seatRequest.getDestStation());
        Set<Ticket> soldTickets = leftTicketInfo.getSoldTickets();

        //优先分配已经售出的票
        for(Ticket soldTicket : soldTickets){
            String soldTicketDestStation = soldTicket.getDestStation();
            //售出的票的终点站在请求的起点之前，则可以分配出去
            if(stationList.indexOf(soldTicketDestStation) < stationList.indexOf(startStation)){
                ticket.setSeatNo(soldTicket.getSeatNo());
                return ticket;
            }
        }

        //分配新的票
        Random rand = new Random();
        int range = seatTotalNum;
        int seat = rand.nextInt(range) + 1;
        while (soldTickets.contains(seat)){
            seat = rand.nextInt(range) + 1;
        }
        ticket.setSeatNo(seat);
        return ticket;
    }
}
