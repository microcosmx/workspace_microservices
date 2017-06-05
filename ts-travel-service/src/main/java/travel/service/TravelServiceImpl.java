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
        TripId ti = new TripId(info.getTripId());
        if(repository.findByTripId(ti) == null){
            Trip trip = new Trip(ti,info.getTrainTypeId(),info.getStartingStation(),
                    info.getStations(),info.getTerminalStation(),info.getStartingTime(),info.getEndTime());
            repository.save(trip);
            return "Create trip:" + ti.toString() + ".";
        }else{
            return "Trip "+ info.getTripId().toString() +" already exists";
        }
    }

    @Override
    public Trip retrieve(Information2 info){
        TripId ti = new TripId(info.getTripId());
        if(repository.findByTripId(ti) != null){
            return repository.findByTripId(ti);
        }else{
            return null;
        }
    }

    @Override
    public String update(Information info){
        TripId ti = new TripId(info.getTripId());
        if(repository.findByTripId(ti) != null){
            Trip trip = new Trip(ti,info.getTrainTypeId(),info.getStartingStation(),
                    info.getStations(),info.getTerminalStation(),info.getStartingTime(),info.getEndTime());
            repository.save(trip);
            return "Update trip:" + ti.toString();
        }else{
            return "Trip "+ info.getTripId().toString() +" doesn't exists";
        }
    }

    @Override
    public String delete(Information2 info){
        TripId ti = new TripId(info.getTripId());
        if(repository.findByTripId(ti) != null){
            repository.deleteByTripId(ti);
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

    @Override
    public GetTripAllDetailResult getTripAllDetailInfo(GetTripAllDetailInfo gtdi){
        GetTripAllDetailResult gtdr = new GetTripAllDetailResult();
        Trip trip = repository.findByTripId(new TripId(gtdi.getTripId()));
        if(trip == null){
            gtdr.setStatus(false);
            gtdr.setMessage("Trip Not Exist");
            gtdr.setTripResponse(null);
        }else{
            TripResponse tripResponse = getTickets(trip,gtdi.getFrom(),gtdi.getTo(),gtdi.getTravelDate());
            if(tripResponse == null){
                gtdr.setStatus(false);
                gtdr.setMessage("Cannot found TripResponse");
                gtdr.setTripResponse(null);
            }else{
                gtdr.setStatus(true);
                gtdr.setMessage("Success");
                gtdr.setTripResponse(tripResponse);
            }
        }
        return gtdr;
    }

    private TripResponse getTickets(Trip trip,String startingPlace, String endPlace, Date departureTime){
        //车次查询_高铁动车（sso） －》 车站站名服务 －》 配置 －》 车服务 －》 车票订单_高铁动车（已购票数）
        //车站站名服务
        Boolean startingPlaceExist = restTemplate.postForObject(
                "http://ts-station-service:12345/station/exist", new QueryStation(startingPlace), Boolean.class);
        Boolean endPlaceExist = restTemplate.postForObject(
                "http://ts-station-service:12345/station/exist", new QueryStation(endPlace),  Boolean.class);
        if(!startingPlaceExist || !endPlaceExist){
            return null;
        }
        //配置
        //查询车票配比，以车站ABC为例，A是始发站，B是途径的车站，C是终点站，分配AC 50%，如果总票数100，那么AC有50张票，AB和BC也各有
        //50张票，因为AB和AC拼起来正好是一张AC。
        String proportion = restTemplate.postForObject("http://ts-config-service:15679/config/query",
                new QueryConfig("直达车票分配比例"), String.class
        );
        double percent = 1.0;
        if(proportion.contains("%")) {
            proportion = proportion.replaceAll("%", "");
            percent = Double.valueOf(proportion)/100;
        }
        //车服务
        TrainType trainType = restTemplate.postForObject(
                "http://ts-train-service:14567/train/retrieve", new QueryTrainType(trip.getTrainTypeId()), TrainType.class
        );
        if(trainType == null){
            System.out.println("traintype doesn't exist");
            return null;
        }
        //车票订单_高铁动车（已购票数）
        QuerySoldTicket information = new QuerySoldTicket(departureTime,trip.getTripId().toString());
        ResultSoldTicket result = restTemplate.postForObject(
                "http://ts-order-service:12031/order/calculate", information ,ResultSoldTicket.class);
        if(result == null){
            System.out.println("soldticketInfo doesn't exist");
            return null;
        }
        //设置返回的车票信息
        TripResponse response = new TripResponse();
        if(startingPlace.equals(trip.getStartingStation()) && endPlace.equals(trip.getTerminalStation())){
            int confort = (int)(trainType.getConfortClass()*percent - result.getFirstClassSeat());
            int economy = (int)(trainType.getEconomyClass()*percent - result.getSecondClassSeat());
            response.setConfortClass(confort);
            response.setEconomyClass(economy);
        }else{
            int confort = (int)(trainType.getConfortClass()*(1-percent) - result.getFirstClassSeat());
            int economy = (int)(trainType.getEconomyClass()*(1-percent) - result.getSecondClassSeat());
            response.setConfortClass(confort);
            response.setEconomyClass(economy);
        }
        response.setStartingStation(startingPlace);
        response.setTerminalStation(endPlace);
        response.setStartingTime(trip.getStartingTime());
        response.setEndTime(trip.getEndTime());
        response.setTripId(new TripId(result.getTrainNumber()));
        return response;
    }
}
