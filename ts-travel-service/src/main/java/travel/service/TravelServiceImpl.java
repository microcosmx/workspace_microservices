package travel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import travel.domain.*;
import travel.repository.TripRepository;

import java.util.*;

/**
 * Created by Chenjie Xu on 2017/5/9.
 */
@Service
public class TravelServiceImpl implements TravelService{
    @Autowired
    TripRepository repository;


    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public String create(Information info){
        if(repository.findByTripId(info.getTripId()) == null){
            Trip trip = new Trip(info.getTripId(),info.getTrainTypeId(),info.getStartingStation(),
                    info.getStations(),info.getTerminalStation(),info.getStartingTime(),info.getEndTime());
            repository.save(trip);
            return "Create trip:" +info.getTripId().toString()+ ".";
        }else{
            return "Trip "+ info.getTripId().toString() +" already exists";
        }
    }

    @Override
    public Trip retrieve(Information2 info){
        if(repository.findByTripId(info.getTripId()) != null){
            return repository.findByTripId(info.getTripId());
        }else{
            return null;
        }
    }

    @Override
    public String update(Information info){
        if(repository.findByTripId(info.getTripId()) != null){
            Trip trip = new Trip(info.getTripId(),info.getTrainTypeId(),info.getStartingStation(),
                    info.getStations(),info.getTerminalStation(),info.getStartingTime(),info.getEndTime());
            repository.save(trip);
            return "Update trip:" + info.getTripId().toString();
        }else{
            return "Trip "+ info.getTripId().toString() +" doesn't exists";
        }
    }

    @Override
    public String delete(Information2 info){
        if(repository.findByTripId(info.getTripId()) != null){
            repository.deleteByTripId(info.getTripId());
            return "Delete trip:" +info.getTripId().toString()+ ".";
        }else{
            return "Trip "+info.getTripId().toString()+" doesn't exist.";
        }
    }

    @Override
    public List<TripResponse> query(QueryInfo info){
        List<TripResponse> list = new ArrayList<TripResponse>();
        String startingPlace = info.getStartingPlace();
        String endPlace = info.getEndPlace();
        Date departureTime = info.getDepartureTime();
        List<Trip> list1 = repository.findByStartingStationAndTerminalStation(startingPlace,endPlace);
        List<Trip> list2 = repository.findByStartingStationAndStations(startingPlace,endPlace);
        List<Trip> list3 = repository.findByStationsAndTerminalStation(startingPlace,endPlace);

        Iterator<Trip> sListIterator1 = list1.iterator();
        Iterator<Trip> sListIterator2 = list2.iterator();
        Iterator<Trip> sListIterator3 = list3.iterator();

        while(sListIterator1.hasNext()) {
            Trip trip = sListIterator1.next();
            TripResponse response = getTickets(trip,startingPlace,endPlace,departureTime);
            list.add(response);
        }

        while(sListIterator2.hasNext()) {
            Trip trip = sListIterator2.next();
            TripResponse response = getTickets(trip,startingPlace,endPlace,departureTime);
            list.add(response);
        }

        while(sListIterator3.hasNext()) {
            Trip trip = sListIterator3.next();
            TripResponse response = getTickets(trip,startingPlace,endPlace,departureTime);
            list.add(response);
        }

        return list;
    }

    private TripResponse getTickets(Trip trip,String startingPlace, String endPlace, Date departureTime){
        //车次查询_高铁动车（sso） －》 车站站名服务 －》 配置 －》 车服务 －》 车票订单_高铁动车（已购票数）
        //车站站名服务
        Boolean startingPlaceExist = restTemplate.postForObject(
                "http://10.141.212.21:12345/station/exist", new StationInformation(startingPlace), Boolean.class);
        //
        Boolean endPlaceExist = restTemplate.postForObject(
                "http://10.141.212.21:12345/station/exist", new StationInformation(endPlace),  Boolean.class);
        System.out.println(startingPlace);
        System.out.println(endPlace);
        //配置


        //车服务
        TrainType trainType = restTemplate.postForObject(
                "http://10.141.212.21:14567/train/retrieve", new TrainTypeInfo(trip.getTrainTypeId()), TrainType.class
        );

        //车票订单_高铁动车（已购票数）
        CalculateSoldTicketInfo information = new CalculateSoldTicketInfo(departureTime,trip.getTripId().toString());
        CalculateSoldTicketResult result = restTemplate.postForObject(
                "http://10.141.212.21:12031/calculateSoldTickets", information ,CalculateSoldTicketResult.class);

        //设置返回的车票信息
        TripResponse response = new TripResponse();
        response.setConfortClass(trainType.getConfortClass() - result.getFirstClassSeat());
        System.out.println("trainType.getConfortClass()"+trainType.getConfortClass());
        System.out.println("result.getFirstClassSeat()"+result.getFirstClassSeat());
        System.out.println(trainType.getConfortClass() - result.getFirstClassSeat());
        response.setEconomyClass(trainType.getEconomyClass() - result.getSecondClassSeat());
        response.setStartingStation(startingPlace);
        response.setTerminalStation(endPlace);
        response.setStartingTime(trip.getStartingTime());
        response.setEndTime(trip.getEndTime());
        response.setTripId(new TripId(result.getTrainNumber()));

        return response;
    }



}
