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

    @Autowired
    RestTemplate restTemplate;

    @Override
    public String create(Information info){
        TripId ti = new TripId(info.getTripId());
        if(repository.findByTripId(ti) == null){
            Trip trip = new Trip(ti,info.getTrainTypeId(),info.getStartingStationId(),
                    info.getStationsId(),info.getTerminalStationId(),info.getStartingTime(),info.getEndTime());
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
            Trip trip = new Trip(ti,info.getTrainTypeId(),info.getStartingStationId(),
                    info.getStationsId(),info.getTerminalStationId(),info.getStartingTime(),info.getEndTime());
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
        String startingPlaceId = queryForStationId(startingPlace);
        String endPlaceId = queryForStationId(endPlace);

        Date departureTime = info.getDepartureTime();
        List<Trip> list1 = repository.findByStartingStationIdAndStationsId(startingPlaceId,endPlaceId);
        List<Trip> list2 = repository.findByStartingStationIdAndTerminalStationId(startingPlaceId,endPlaceId);
        List<Trip> list3 = repository.findByStationsIdAndTerminalStationId(startingPlaceId,endPlaceId);

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
        System.out.println("[TravelService] [GetTripAllDetailInfo] TripId:" + gtdi.getTripId());
        Trip trip = repository.findByTripId(new TripId(gtdi.getTripId()));
        if(trip == null){
            gtdr.setStatus(false);
            gtdr.setMessage("Trip Not Exist");
            gtdr.setTripResponse(null);
            gtdr.setTrip(null);
        }else{
            TripResponse tripResponse = getTickets(trip,gtdi.getFrom(),gtdi.getTo(),gtdi.getTravelDate());
            if(tripResponse == null){
                gtdr.setStatus(false);
                gtdr.setMessage("Cannot found TripResponse");
                gtdr.setTripResponse(null);
                gtdr.setTrip(null);
            }else{
                gtdr.setStatus(true);
                gtdr.setMessage("Success");
                gtdr.setTripResponse(tripResponse);
                gtdr.setTrip(repository.findByTripId(new TripId(gtdi.getTripId())));
            }
        }
        return gtdr;
    }

    private TripResponse getTickets(Trip trip,String startingPlace, String endPlace, Date departureTime){

        //判断所查日期是否在当天及之后
        if(!afterToday(departureTime)){
            return null;
        }

        QueryForTravel query = new QueryForTravel();
        query.setTrip(trip);
        query.setStartingPlace(startingPlace);
        query.setEndPlace(endPlace);
        query.setDepartureTime(departureTime);

        ResultForTravel resultForTravel = restTemplate.postForObject(
                "http://ts-ticketinfo-service:15681/ticketinfo/queryForTravel", query ,ResultForTravel.class);
        double percent = 1.0;
        TrainType trainType;
        if(resultForTravel.isStatus()){
            percent = resultForTravel.getPercent();
            trainType = resultForTravel.getTrainType();
        }else{
            return null;
        }

        //车票订单_高铁动车（已购票数）
        QuerySoldTicket information = new QuerySoldTicket(departureTime,trip.getTripId().toString());
        ResultSoldTicket result = restTemplate.postForObject(
                "http://ts-order-service:12031/order/calculate", information ,ResultSoldTicket.class);
        if(result == null){
            System.out.println("soldticket Info doesn't exist");
            return null;
        }
        //设置返回的车票信息
        TripResponse response = new TripResponse();
        if(queryForStationId(startingPlace).equals(trip.getStartingStationId()) && queryForStationId(endPlace).equals(trip.getTerminalStationId())){
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
        response.setTrainTypeId(trainType.getId());
        response.setPriceForConfortClass(resultForTravel.getPrices().get("confortClass"));
        response.setPriceForEconomyClass(resultForTravel.getPrices().get("economyClass"));

        return response;
}

    @Override
    public List<Trip> queryAll(){
        return repository.findAll();
    }

    private static boolean afterToday(Date date) {
        Calendar calDateA = Calendar.getInstance();
        Date today = new Date();
        calDateA.setTime(today);

        Calendar calDateB = Calendar.getInstance();
        calDateB.setTime(date);

        if(calDateA.get(Calendar.YEAR) > calDateB.get(Calendar.YEAR)){
            return false;
        }else if(calDateA.get(Calendar.YEAR) == calDateB.get(Calendar.YEAR)){
            if(calDateA.get(Calendar.MONTH) > calDateB.get(Calendar.MONTH)){
                return false;
            }else if(calDateA.get(Calendar.MONTH) == calDateB.get(Calendar.MONTH)){
                if(calDateA.get(Calendar.DAY_OF_MONTH) > calDateB.get(Calendar.DAY_OF_MONTH)){
                    return false;
                }else{
                    return true;
                }
            }else{
                return true;
            }
        }else{
            return true;
        }
    }

    private String queryForStationId(String stationName){
        QueryForStationId query = new QueryForStationId();
        query.setName(stationName);
        String id = restTemplate.postForObject(
                "http://ts-ticketinfo-service:15681/ticketinfo/queryForStationId", query ,String.class);
        return id;
    }
}
