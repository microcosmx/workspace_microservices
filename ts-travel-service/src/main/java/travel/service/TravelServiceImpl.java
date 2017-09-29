package travel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import travel.domain.*;
import travel.repository.TripRepository;
import java.util.*;

@Service
public class TravelServiceImpl implements TravelService{

    @Autowired
    private TripRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String create(Information info){
        TripId ti = new TripId(info.getTripId());
        if(repository.findByTripId(ti) == null){
            Trip trip = new Trip(ti,info.getTrainTypeId(),info.getStartingStationId(),
                    info.getStationsId(),info.getTerminalStationId(),info.getStartingTime(),info.getEndTime());
            trip.setRouteId(info.getRouteId());
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
            trip.setRouteId(info.getRouteId());
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

        ArrayList<Trip> allTripList = repository.findAll();
        for(Trip tempTrip : allTripList){
            String routeId = tempTrip.getRouteId();
            Route tempRoute = getRouteByRouteId(routeId);
            if(tempRoute.getStations().contains(startingPlaceId) &&
                    tempRoute.getStations().contains(endPlaceId) &&
                    tempRoute.getStations().indexOf(startingPlaceId) < tempRoute.getStations().indexOf(endPlaceId)){
                TripResponse response = getTickets(tempTrip,startingPlace,endPlace,departureTime);
                list.add(response);
            }
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
//        double percent = 1.0;
//        TrainType trainType;
//        if(resultForTravel.isStatus()){
//            percent = resultForTravel.getPercent();
//            trainType = resultForTravel.getTrainType();
//        }else{
//            return null;
//        }

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
//            int confort = (int)(trainType.getConfortClass()*percent - result.getFirstClassSeat());
//            int economy = (int)(trainType.getEconomyClass()*percent - result.getSecondClassSeat());
//            response.setConfortClass(confort);
//            response.setEconomyClass(economy);
            response.setConfortClass(50);
            response.setEconomyClass(50);
        }else{
//            int confort = (int)(trainType.getConfortClass()*(1-percent) - result.getFirstClassSeat());
//            int economy = (int)(trainType.getEconomyClass()*(1-percent) - result.getSecondClassSeat());
//            response.setConfortClass(confort);
//            response.setEconomyClass(economy);
            response.setConfortClass(50);
            response.setEconomyClass(50);
        }
        response.setStartingStation(startingPlace);
        response.setTerminalStation(endPlace);

        response.setStartingTime(trip.getStartingTime());
        response.setEndTime(trip.getEndTime());

        response.setTripId(new TripId(result.getTrainNumber()));
        response.setTrainTypeId(trip.getTrainTypeId());
        response.setPriceForConfortClass(resultForTravel.getPrices().get("confortClass"));
        response.setPriceForEconomyClass(resultForTravel.getPrices().get("economyClass"));
//        response.setPriceForConfortClass("500");
//        response.setPriceForEconomyClass("300");

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

    private Route getRouteByRouteId(String routeId){
        System.out.println("[Travel Service][Get Route By Id] Route ID：" + routeId);
        GetRouteByIdResult result = restTemplate.getForObject(
                "http://ts-route-service:11178/route/queryById/" + routeId,
                GetRouteByIdResult.class);
        if(result.isStatus() == false){
            System.out.println("[Travel Service][Get Route By Id] Fail." + result.getMessage());
            return null;
        }else{
            System.out.println("[Travel Service][Get Route By Id] Success.");
            return result.getRoute();
        }
    }
}
