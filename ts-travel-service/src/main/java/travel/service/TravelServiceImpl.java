package travel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import travel.domain.*;
import travel.repository.TripRepository;

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
        }
        return null;
    }

    @Override
    public Trip retrieve(Information2 info){
        return null;
    }

    @Override
    public String update(Information info){
        return null;
    }

    @Override
    public String delete(Information2 info){
        return null;
    }

    @Override
    public TripResponse query(QueryInfo info){
        return null;
    }
}
