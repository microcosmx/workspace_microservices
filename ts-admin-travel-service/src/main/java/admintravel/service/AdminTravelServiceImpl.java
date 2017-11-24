package admintravel.service;

import admintravel.domain.bean.AdminTrip;
import admintravel.domain.bean.GetTrainTypeRequest;
import admintravel.domain.bean.TrainType;
import admintravel.domain.request.AddAndModifyTravelRequest;
import admintravel.domain.request.DeleteTravelRequest;
import admintravel.domain.response.AdminFindAllResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Service
public class AdminTravelServiceImpl implements AdminTravelService {
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public AdminFindAllResult getAllTravels(String id) {
        AdminFindAllResult result = new AdminFindAllResult();
        ArrayList<AdminTrip> trips = new ArrayList<AdminTrip>();
        if(checkId(id)){
            System.out.println("[Admin Travel Service][Get All Travels]");
            result = restTemplate.getForObject(
                    "http://ts-travel-service:12346/travel/adminQueryAll",
                    AdminFindAllResult.class);
            if(result.isStatus()){
                System.out.println("[Admin Travel Service][Get Travel From ts-travel-service successfully!]");
                trips.addAll(result.getTrips());
            }
            else
                System.out.println("[Admin Travel Service][Get Travel From ts-travel-service fail!]");

            result = restTemplate.getForObject(
                    "http://ts-travel2-service:16346/travel2/adminQueryAll",
                    AdminFindAllResult.class);
            if(result.isStatus()){
                System.out.println("[Admin Travel Service][Get Travel From ts-travel2-service successfully!]");
                trips.addAll(result.getTrips());
            }
            else
                System.out.println("[Admin Travel Service][Get Travel From ts-travel2-service fail!]");
            result.setTrips(trips);
        }
        else{
            result.setStatus(false);
            result.setMessage("Admin find all travel result fail");
            result.setTrips(null);
        }
        return result;
    }

    @Override
    public String addTravel(AddAndModifyTravelRequest request) {
        String trainNumber = getTrainNumberByTrainTypeId(request.getTrainTypeId());
        return null;
    }

    @Override
    public String updateTravel(AddAndModifyTravelRequest request) {
        return null;
    }

    @Override
    public String deleteTravel(DeleteTravelRequest request) {
        return null;
    }

    private boolean checkId(String id){
        if("1d1a11c1-11cb-1cf1-b1bb-b11111d1da1f".equals(id)){
            return true;
        }
        else{
            return false;
        }
    }

    private String getTrainNumberByTrainTypeId(String trainTypeId){
        GetTrainTypeRequest request = new GetTrainTypeRequest();
        request.setId(trainTypeId);
        TrainType trainType = restTemplate.postForObject(
                "http://ts-train-service:14567/train/retrieve", request,TrainType.class);
        return trainType.getId();
    }
}
