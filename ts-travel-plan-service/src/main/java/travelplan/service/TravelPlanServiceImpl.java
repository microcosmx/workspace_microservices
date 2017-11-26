package travelplan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import travelplan.domain.*;
import java.util.ArrayList;

@Service
public class TravelPlanServiceImpl implements TravelPlanService{

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public TransferTravelSearchResult getTransferSearch(TransferTravelSearchInfo info) {

        QueryInfo queryInfoFirstSection = new QueryInfo();
        queryInfoFirstSection.setDepartureTime(info.getTravelDate());
        queryInfoFirstSection.setStartingPlace(info.getFromStationName());
        queryInfoFirstSection.setEndPlace(info.getViaStationName());

        ArrayList<TripResponse> firstSectionFromHighSpeed;
        ArrayList<TripResponse> firstSectionFromNormal;
        firstSectionFromHighSpeed = tripsFromHighSpeed(queryInfoFirstSection);
        firstSectionFromNormal = tripsFromNormal(queryInfoFirstSection);

        QueryInfo queryInfoSecondSectoin = new QueryInfo();
        queryInfoSecondSectoin.setDepartureTime(info.getTravelDate());
        queryInfoSecondSectoin.setStartingPlace(info.getViaStationName());
        queryInfoSecondSectoin.setEndPlace(info.getToStationName());

        ArrayList<TripResponse> secondSectionFromHighSpeed;
        ArrayList<TripResponse> secondSectionFromNormal;
        secondSectionFromHighSpeed = tripsFromHighSpeed(queryInfoSecondSectoin);
        secondSectionFromNormal = tripsFromNormal(queryInfoSecondSectoin);

        ArrayList<TripResponse> firstSection = new ArrayList<>();
        firstSection.addAll(firstSectionFromHighSpeed);
        firstSection.addAll(firstSectionFromNormal);

        ArrayList<TripResponse> secondSection = new ArrayList<>();
        secondSection.addAll(secondSectionFromHighSpeed);
        secondSection.addAll(secondSectionFromNormal);

        TransferTravelSearchResult result = new TransferTravelSearchResult();
        result.setStatus(true);
        result.setMessage("Success.");
        result.setFirstSectionResult(firstSection);
        result.setSecondSectionResult(secondSection);

        return result;
    }

    @Override
    public TravelAdvanceResult getCheapest(QueryInfo info) {
        GetRoutePlanInfo routePlanInfo = new GetRoutePlanInfo();
        routePlanInfo.setNum(5);
        routePlanInfo.setFormStationName(info.getStartingPlace());
        routePlanInfo.setToStationName(info.getEndPlace());
        routePlanInfo.setTravelDate(info.getDepartureTime());
        RoutePlanResults routePlanResults = getRoutePlanResultCheapest(routePlanInfo);

        TravelAdvanceResult travelAdvanceResult = new TravelAdvanceResult();

        if(routePlanResults.isStatus() == true){
            ArrayList<RoutePlanResultUnit> routePlanResultUnits = routePlanResults.getResults();
            travelAdvanceResult.setStatus(true);
            travelAdvanceResult.setMessage("Success");
            ArrayList<TravelAdvanceResultUnit> lists = new ArrayList<>();
            for(int i = 0; i < routePlanResultUnits.size(); i++){
                RoutePlanResultUnit tempUnit = routePlanResultUnits.get(i);
                TravelAdvanceResultUnit newUnit = new TravelAdvanceResultUnit();
                newUnit.setTripId(tempUnit.getTripId());
                newUnit.setTrainTypeId(tempUnit.getTrainTypeId());
                newUnit.setFromStationName(tempUnit.getFromStationName());
                newUnit.setToStationName(tempUnit.getToStationName());
                newUnit.setStopStations(tempUnit.getStopStations());
                newUnit.setPriceForFirstClassSeat(tempUnit.getPriceForFirstClassSeat());
                newUnit.setPriceForSecondClassSeat(tempUnit.getPriceForSecondClassSeat());
                newUnit.setStartingTime(tempUnit.getStartingTime());
                newUnit.setEndTime(tempUnit.getEndTime());
                newUnit.setNumberOfRestTicketFirstClass(50);
                newUnit.setNumberOfRestTicketFirstClass(50);
                lists.add(newUnit);
            }
            travelAdvanceResult.setTravelAdvanceResultUnits(lists);
        }else{
            travelAdvanceResult.setStatus(false);
            travelAdvanceResult.setMessage("Cannot Find");
            travelAdvanceResult.setTravelAdvanceResultUnits(new ArrayList<>());
        }

        return travelAdvanceResult;
}

    @Override
    public TravelAdvanceResult getQuickest(QueryInfo info) {
        GetRoutePlanInfo routePlanInfo = new GetRoutePlanInfo();
        routePlanInfo.setNum(5);
        routePlanInfo.setFormStationName(info.getStartingPlace());
        routePlanInfo.setToStationName(info.getEndPlace());
        routePlanInfo.setTravelDate(info.getDepartureTime());
        RoutePlanResults routePlanResults = getRoutePlanResultQuickest(routePlanInfo);

        TravelAdvanceResult travelAdvanceResult = new TravelAdvanceResult();

        if(routePlanResults.isStatus() == true){
            ArrayList<RoutePlanResultUnit> routePlanResultUnits = routePlanResults.getResults();
            travelAdvanceResult.setStatus(true);
            travelAdvanceResult.setMessage("Success");
            ArrayList<TravelAdvanceResultUnit> lists = new ArrayList<>();
            for(int i = 0; i < routePlanResultUnits.size(); i++){
                RoutePlanResultUnit tempUnit = routePlanResultUnits.get(i);
                TravelAdvanceResultUnit newUnit = new TravelAdvanceResultUnit();
                newUnit.setTripId(tempUnit.getTripId());
                newUnit.setTrainTypeId(tempUnit.getTrainTypeId());
                newUnit.setFromStationName(tempUnit.getFromStationName());
                newUnit.setToStationName(tempUnit.getToStationName());
                newUnit.setStopStations(tempUnit.getStopStations());
                newUnit.setPriceForFirstClassSeat(tempUnit.getPriceForFirstClassSeat());
                newUnit.setPriceForSecondClassSeat(tempUnit.getPriceForSecondClassSeat());
                newUnit.setStartingTime(tempUnit.getStartingTime());
                newUnit.setEndTime(tempUnit.getEndTime());
                newUnit.setNumberOfRestTicketFirstClass(50);
                newUnit.setNumberOfRestTicketFirstClass(50);
                lists.add(newUnit);
            }
            travelAdvanceResult.setTravelAdvanceResultUnits(lists);
        }else{
            travelAdvanceResult.setStatus(false);
            travelAdvanceResult.setMessage("Cannot Find");
            travelAdvanceResult.setTravelAdvanceResultUnits(new ArrayList<>());
        }

        return travelAdvanceResult;

    }

    @Override
    public TravelAdvanceResult getMinStation(QueryInfo info) {
        GetRoutePlanInfo routePlanInfo = new GetRoutePlanInfo();
        routePlanInfo.setNum(5);
        routePlanInfo.setFormStationName(info.getStartingPlace());
        routePlanInfo.setToStationName(info.getEndPlace());
        routePlanInfo.setTravelDate(info.getDepartureTime());
        RoutePlanResults routePlanResults = getRoutePlanResultMinStation(routePlanInfo);
        TravelAdvanceResult travelAdvanceResult = new TravelAdvanceResult();

        if(routePlanResults.isStatus() == true){
            ArrayList<RoutePlanResultUnit> routePlanResultUnits = routePlanResults.getResults();
            travelAdvanceResult.setStatus(true);
            travelAdvanceResult.setMessage("Success");
            ArrayList<TravelAdvanceResultUnit> lists = new ArrayList<>();
            for(int i = 0; i < routePlanResultUnits.size(); i++){
                RoutePlanResultUnit tempUnit = routePlanResultUnits.get(i);
                TravelAdvanceResultUnit newUnit = new TravelAdvanceResultUnit();
                newUnit.setTripId(tempUnit.getTripId());
                newUnit.setTrainTypeId(tempUnit.getTrainTypeId());
                newUnit.setFromStationName(tempUnit.getFromStationName());
                newUnit.setToStationName(tempUnit.getToStationName());
                newUnit.setStopStations(tempUnit.getStopStations());
                newUnit.setPriceForFirstClassSeat(tempUnit.getPriceForFirstClassSeat());
                newUnit.setPriceForSecondClassSeat(tempUnit.getPriceForSecondClassSeat());
                newUnit.setStartingTime(tempUnit.getStartingTime());
                newUnit.setEndTime(tempUnit.getEndTime());
                newUnit.setNumberOfRestTicketFirstClass(50);
                newUnit.setNumberOfRestTicketFirstClass(50);
                lists.add(newUnit);
            }
            travelAdvanceResult.setTravelAdvanceResultUnits(lists);
        }else{
            travelAdvanceResult.setStatus(false);
            travelAdvanceResult.setMessage("Cannot Find");
            travelAdvanceResult.setTravelAdvanceResultUnits(new ArrayList<>());
        }

        return travelAdvanceResult;
    }

    private RoutePlanResults getRoutePlanResultCheapest(GetRoutePlanInfo info){
        RoutePlanResults routePlanResults =
                restTemplate.postForObject(
                        "http://ts-route-plan-service:14578/routePlan/cheapestRoute",
                        info,RoutePlanResults.class
                );
        return routePlanResults;
    }

    private RoutePlanResults getRoutePlanResultQuickest(GetRoutePlanInfo info){
        RoutePlanResults routePlanResults =
                restTemplate.postForObject(
                        "http://ts-route-plan-service:14578/routePlan/quickestRoute",
                        info,RoutePlanResults.class
                );
        return routePlanResults;
    }

    private RoutePlanResults getRoutePlanResultMinStation(GetRoutePlanInfo info){
        RoutePlanResults routePlanResults =
                restTemplate.postForObject(
                        "http://ts-route-plan-service:14578/routePlan/minStopStations",
                        info,RoutePlanResults.class
                );
        return routePlanResults;
    }

    private ArrayList<TripResponse> tripsFromHighSpeed(QueryInfo info){
        ArrayList<TripResponse> result = new ArrayList<>();
        result = restTemplate.postForObject("http://ts-travel-service:12346/travel/query",info,result.getClass());
        return result;
    }

    private ArrayList<TripResponse> tripsFromNormal(QueryInfo info){
        ArrayList<TripResponse> result = new ArrayList<>();
        result = restTemplate.postForObject("http://ts-travel2-service:16346/travel2/query",info,result.getClass());
        return result;
    }
}
