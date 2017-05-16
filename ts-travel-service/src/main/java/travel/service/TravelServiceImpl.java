package travel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import travel.domain.*;
import travel.repository.TripRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by Chenjie Xu on 2017/5/9.
 */
@Service
public class TravelServiceImpl implements TravelService{
    @Autowired
    TripRepository repository;

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
    public TripResponse query(QueryInfo info){
        TripResponse response = new TripResponse();
        String startingPlace = info.getStartingPlace();
        String endPlace = info.getEndPlace();
        Date departureTime = info.getDepartureTime();
        List<Trip> list = repository.findByStartingStationAndTerminalStation(startingPlace,endPlace);


        return null;
    }
}
