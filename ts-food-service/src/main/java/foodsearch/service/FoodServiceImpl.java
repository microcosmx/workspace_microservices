package foodsearch.service;

import foodsearch.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FoodServiceImpl implements FoodService{

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public GetAllFoodOfTripResult getAllFood(String date, String startStation, String endStation, String tripId) {
        System.out.println("data=" + date + "start=" + startStation + "end=" + endStation + "tripid=" + tripId);
        GetAllFoodOfTripResult result = new GetAllFoodOfTripResult();

        if(null == tripId || tripId.length() <= 2){
            result.setStatus(false);
            result.setMessage("The tripId is null or too short");
            return result;
        }

        List<TrainFood> trainFoodList = null;
        Map<String, List<FoodStore>> foodStoreListMap = new HashMap<String, List<FoodStore>>();

        QueryTrainFoodInfo qti = new QueryTrainFoodInfo();
        qti.setTripId(tripId);

        GetTrainFoodListResult  trainFoodListResult = restTemplate.postForObject
                                        ("http://ts-food-map-service:18855/foodmap/getTrainFoodOfTrip",
                                                qti, GetTrainFoodListResult.class);
        if( trainFoodListResult.isStatus()){
            trainFoodList = trainFoodListResult.getTrainFoodList();
            System.out.println("[Food Service]Get Train Food List!");
        } else {
            System.out.println("[Food Service]Get the Get Food Request Failed!");
            result.setStatus(false);
            result.setMessage(trainFoodListResult.getMessage());
            return result;
        }
       //车次途经的车站
        GetRouteResult  stationResult= restTemplate.getForObject
                                        ("http://ts-travel-service:12346/travel/getRouteByTripId/"+tripId,
                                                GetRouteResult.class);
        if( stationResult.isStatus() ){
            Route route = stationResult.getRoute();
            for(String s:route.getStations()){
                QueryFoodStoresInfo qsi = new QueryFoodStoresInfo();
                qsi.setStationId(s);
                GetFoodStoresListResult foodStoresListResult = restTemplate.postForObject
                                            ("http://ts-food-map-service:18855/foodmap/getFoodStoresOfStation",
                                                    qsi, GetFoodStoresListResult.class);
                if(foodStoresListResult.isStatus()){
                    if( null != foodStoresListResult.getFoodStoreList()){
                        System.out.println("[Food Service]Get the Food Store!");
                        foodStoreListMap.put(s, foodStoresListResult.getFoodStoreList());
                    }
                } else {
                    result.setStatus(false);
                    result.setMessage(foodStoresListResult.getMessage());
                    return result;
                }
            }
        } else {
            result.setStatus(false);
            result.setMessage(stationResult.getMessage());
            return result;
        }

        result.setStatus(true);
        result.setMessage("Successed");
        result.setTrainFoodList(trainFoodList);
        result.setFoodStoreListMap(foodStoreListMap);

        return result;
    }
}
