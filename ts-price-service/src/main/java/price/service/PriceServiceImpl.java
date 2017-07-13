package price.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import price.domain.*;
import price.repository.DistanceRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.*;

/**
 * Created by Chenjie Xu on 2017/6/9.
 */
@Service
public class PriceServiceImpl implements PriceService{
    @Autowired
    DistanceRepository repository;

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String query(QueryInfo info){
        System.out.println("[Price Service] From:" + info.getStartingPlaceId() + " To:" + info.getEndPlaceId());
        Distance distance = repository.findByPlaceAAndPlaceB(info.getStartingPlaceId(),info.getEndPlaceId());
        String priceRate = restTemplate.postForObject("http://ts-config-service:15679/config/query",
                new QueryConfig(info.getTrainTypeId() + "_" + info.getSeatClass()+ "_priceRate"), String.class
        );
        System.out.println("[Price Service][Query] Price Rate:" + priceRate);
        System.out.println("[Price Service][Distance] Distant:" + distance);
        return price(distance,priceRate);
    }

    @Override
    public List<ResultPrice> queryAll(){
        List<Distance> distances = repository.findAll();
        List<ResultPrice> prices = new ArrayList<ResultPrice>();
        Iterator<Distance> iterator = distances.iterator();
//        List<TrainType> trainTypes = restTemplate.getForObject("http://ts-train-service:14567/train/query", List.class);
        List<LinkedHashMap> trainTypes = restTemplate.getForObject("http://ts-train-service:14567/train/query", List.class);

        while(iterator.hasNext()){
            Distance distance = iterator.next();
            Iterator<LinkedHashMap> trainTypeIterator = trainTypes.iterator();

            while(trainTypeIterator.hasNext()){
                LinkedHashMap type = trainTypeIterator.next();
                Iterator<Map.Entry> iterator1= type.entrySet().iterator();
                Map.Entry entry = iterator1.next();

                String priceRateConfort = restTemplate.postForObject("http://ts-config-service:15679/config/query",
                        new QueryConfig((String)entry.getValue() + "_confortClass_priceRate"), String.class
                );
                ResultPrice priceConfort = new ResultPrice();
                priceConfort.setPlaceA(distance.getPlaceA());
                priceConfort.setPlaceB(distance.getPlaceB());
                priceConfort.setSeatClass("confortClass");
                priceConfort.setTrainTypeId((String)entry.getValue());
                priceConfort.setPrice(price(distance,priceRateConfort));
                prices.add(priceConfort);

                String priceRateEconomy = restTemplate.postForObject("http://ts-config-service:15679/config/query",
                        new QueryConfig((String)entry.getValue() + "_economyClass_priceRate"), String.class
                );
                ResultPrice priceEconomy = new ResultPrice();
                priceEconomy.setPlaceA(distance.getPlaceA());
                priceEconomy.setPlaceB(distance.getPlaceB());
                priceEconomy.setSeatClass("economyClass");
                priceEconomy.setTrainTypeId((String)entry.getValue());
                priceEconomy.setPrice(price(distance,priceRateEconomy));
                prices.add(priceEconomy);
            }
        }
        return prices;
    }

    @Override
    public String create(CreateInfo info){
        if(repository.findByPlaceAAndPlaceB(info.getPlaceA(),info.getPlaceB()) == null){
            Distance distance = new Distance();
            distance.setPlaceA(info.getPlaceA());
            distance.setPlaceB(info.getPlaceB());
            distance.setDistance(info.getDistance());
            repository.save(distance);
            distance.setPlaceA(info.getPlaceB());
            distance.setPlaceB(info.getPlaceA());
            repository.save(distance);
            return "true";
        }else{
            return "Distance between " +info.getPlaceA()+" and "+ info.getPlaceB() +" already exist!";
        }

    }

    @Override
    public boolean delete(DeleteInfo info){
        return repository.deleteByPlaceAAndPlaceB(info.getPlaceA(),info.getPlaceB())
                & repository.deleteByPlaceAAndPlaceB(info.getPlaceB(),info.getPlaceA());
    }

    @Override
    public String update(CreateInfo info){
        if(repository.findByPlaceAAndPlaceB(info.getPlaceA(),info.getPlaceB()) != null){
//            Distance distance = new Distance();
//            distance.setPlaceA(info.getPlaceA());
//            distance.setPlaceB(info.getPlaceB());
//            distance.setDistance(info.getDistance());
//            repository.save(distance);
//            distance.setPlaceA(info.getPlaceB());
//            distance.setPlaceB(info.getPlaceA());
//            repository.save(distance);
            Query query1 = new Query(Criteria.where("placeA").is(info.getPlaceA()).and("placeB").is(info.getPlaceB()));
            mongoTemplate.updateMulti(query1,Update.update("distance",info.getDistance()),Distance.class);
            Query query2 = new Query(Criteria.where("placeA").is(info.getPlaceB()).and("placeB").is(info.getPlaceA()));
            mongoTemplate.updateMulti(query2,Update.update("distance",info.getDistance()),Distance.class);
            return "true";
        }else{
            return "Distance between " +info.getPlaceA()+" and "+ info.getPlaceB() +" doesn't exist!";
        }
    }

    private String price(Distance distance,String priceRate){
        BigDecimal rate = new BigDecimal(priceRate);
        String price = rate.multiply(new BigDecimal(distance.getDistance())).toString();
        return price;
    }
}
