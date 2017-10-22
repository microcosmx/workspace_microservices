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

        ArrayList<TripResponse> firstSectionFromHighSpeed = new ArrayList<>();
        ArrayList<TripResponse> firstSectionFromNormal = new ArrayList<>();
        firstSectionFromHighSpeed = tripsFromHighSpeed(queryInfoFirstSection);
        firstSectionFromNormal = tripsFromNormal(queryInfoFirstSection);

        QueryInfo queryInfoSecondSectoin = new QueryInfo();
        queryInfoSecondSectoin.setDepartureTime(info.getTravelDate());
        queryInfoSecondSectoin.setStartingPlace(info.getViaStationName());
        queryInfoSecondSectoin.setEndPlace(info.getToStationName());

        ArrayList<TripResponse> secondSectionFromHighSpeed = new ArrayList<>();
        ArrayList<TripResponse> secondSectionFromNormal = new ArrayList<>();
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

    ArrayList<TripResponse> tripsFromHighSpeed(QueryInfo info){
        ArrayList<TripResponse> result = new ArrayList<>();
        result = restTemplate.postForObject("http://ts-travel-service:12346/travel/query",info,result.getClass());
        return result;
    }

    ArrayList<TripResponse> tripsFromNormal(QueryInfo info){
        ArrayList<TripResponse> result = new ArrayList<>();
        result = restTemplate.postForObject("http://ts-travel2-service:16346/travel2/query",info,result.getClass());
        return result;
    }
}
