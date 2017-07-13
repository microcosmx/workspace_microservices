package fdse.microservice.service;

import fdse.microservice.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

/**
 * Created by Chenjie Xu on 2017/6/6.
 */
@Service
public class BasicServiceImpl implements BasicService{

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ResultForTravel queryForTravel(QueryForTravel info){

        ResultForTravel result = new ResultForTravel();
        result.setStatus(true);
        //车站站名服务
        Boolean startingPlaceExist = restTemplate.postForObject(
                "http://ts-station-service:12345/station/exist", new QueryStation(info.getStartingPlace()), Boolean.class);
        Boolean endPlaceExist = restTemplate.postForObject(
                "http://ts-station-service:12345/station/exist", new QueryStation(info.getEndPlace()),  Boolean.class);
        if(!startingPlaceExist || !endPlaceExist){
            result.setStatus(false);
        }

        String startingPlaceId = restTemplate.postForObject(
                "http://ts-station-service:12345/station/queryForId", new QueryStation(info.getStartingPlace()), String.class);
        String endPlaceId = restTemplate.postForObject(
                "http://ts-station-service:12345/station/queryForId", new QueryStation(info.getEndPlace()),  String.class);


        //配置
        //查询车票配比，以车站ABC为例，A是始发站，B是途径的车站，C是终点站，分配AC 50%，如果总票数100，那么AC有50张票，AB和BC也各有
        //50张票，因为AB和AC拼起来正好是一张AC。
        String proportion = restTemplate.postForObject("http://ts-config-service:15679/config/query",
                new QueryConfig("DirectTicketAllocationProportion"), String.class
        );
        double percent = 1.0;
        if(proportion.contains("%")) {
            proportion = proportion.replaceAll("%", "");
            percent = Double.valueOf(proportion)/100;
            result.setPercent(percent);
        }else{
            result.setStatus(false);
        }

        //车服务
        TrainType trainType = restTemplate.postForObject(
                "http://ts-train-service:14567/train/retrieve", new QueryTrainType(info.getTrip().getTrainTypeId()), TrainType.class
        );
        if(trainType == null){
            System.out.println("traintype doesn't exist");
            result.setStatus(false);
        }else{
            result.setTrainType(trainType);
        }

        //票价服务
        QueryPriceInfo queryPriceInfo = new QueryPriceInfo();
        queryPriceInfo.setStartingPlaceId(startingPlaceId);
        queryPriceInfo.setEndPlaceId(endPlaceId);
        queryPriceInfo.setTrainTypeId(trainType.getId());
        queryPriceInfo.setSeatClass("economyClass");
        String priceForEconomyClass = restTemplate.postForObject(
                "http://ts-price-service:16579/price/query",queryPriceInfo , String.class
        );

        queryPriceInfo.setSeatClass("confortClass");
        String priceForConfortClass = restTemplate.postForObject(
                "http://ts-price-service:16579/price/query", queryPriceInfo, String.class
        );
        HashMap<String,String> prices = new HashMap<String,String>();
        prices.put("economyClass",priceForEconomyClass);
        prices.put("confortClass",priceForConfortClass);
        result.setPrices(prices);

        return result;
    }

    @Override
    public String queryForStationId(QueryForStationId info){
        String id = restTemplate.postForObject(
                "http://ts-station-service:12345/station/queryForId", info, String.class);
        return id;
    }
}
