package seat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import seat.domain.*;

import java.util.List;
import java.util.Random;
import java.util.Set;

@Service
public class SeatServiceImpl implements SeatService {
    @Autowired
    RestTemplate restTemplate;

    @Override
    public Ticket distributeSeat(SeatRequest seatRequest){
        GetRouteResult routeResult;
        GetTrainTypeResult trainTypeResult;
        LeftTicketInfo leftTicketInfo;

        //区分G\D开头和其它车次
        String trainNumber = seatRequest.getTrainNumber();
        if(trainNumber.startsWith("G") || trainNumber.startsWith("D") ){
            System.out.println("[SeatService] TrainNumber start with G|D");

            //调用微服务，查询获得车次的所有站点信息
            routeResult = restTemplate.getForObject(
                    "http://ts-travel-service:12346/travel/getRouteByTripId/"+ seatRequest.getTrainNumber() ,GetRouteResult.class);
            System.out.println("[SeatService] The result of getRouteResult is " + routeResult.getMessage());

            //调用微服务，查询获得余票信息：该车次指定座型已售Ticket的set集合
            leftTicketInfo = restTemplate.postForObject(
                    "http://ts-order-service:12031/order/getTicketListByDateAndTripId", seatRequest ,LeftTicketInfo.class);

            //调用微服务，查询该车次指定座型总数量
            trainTypeResult = restTemplate.getForObject(
                    "http://ts-travel-service:12346/travel/getTrainTypeByTripId/" + seatRequest.getTrainNumber() ,GetTrainTypeResult.class);
            System.out.println("[SeatService] The result of getTrainTypeResult is " + trainTypeResult.getMessage());
        }
        else{
            System.out.println("[SeatService] TrainNumber start with other capital");
            //调用微服务，查询获得车次的所有站点信息
            routeResult = restTemplate.getForObject(
                    "http://ts-travel2-service:16346/travel/getRouteByTripId/" + seatRequest.getTrainNumber() ,GetRouteResult.class);
            System.out.println("[SeatService] The result of getRouteResult is " + routeResult.getMessage());

            //调用微服务，查询获得余票信息：该车次指定座型已售Ticket的set集合
            leftTicketInfo = restTemplate.postForObject(
                    "http://ts-order-other-service:12032/order/getTicketListByDateAndTripId", seatRequest ,LeftTicketInfo.class);

            //调用微服务，查询该车次指定座型总数量
            trainTypeResult = restTemplate.getForObject(
                    "http://ts-travel2-service:16346/travel/getTrainTypeByTripId/" + seatRequest.getTrainNumber(), GetTrainTypeResult.class);
            System.out.println("[SeatService] The result of getTrainTypeResult is " + trainTypeResult.getMessage());
        }


        //分配座位
        List<String> stationList = routeResult.getRoute().getStations();
        int seatTotalNum;
        if(seatRequest.getSeatType() == SeatClass.FIRSTCLASS.getCode()) {
            seatTotalNum = trainTypeResult.getTrainType().getConfortClass();
            System.out.println("[SeatService] The request seat type is confortClass and the total num is " + seatTotalNum);
        }
        else {
            seatTotalNum = trainTypeResult.getTrainType().getEconomyClass();
            System.out.println("[SeatService] The request seat type is economyClass and the total num is " + seatTotalNum);
        }
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
                System.out.println("[SeatService] Use the previous distributed seat number!" + soldTicket.getSeatNo());
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
        System.out.println("[SeatService] Use a new seat number!" + seat);
        return ticket;
    }
}
