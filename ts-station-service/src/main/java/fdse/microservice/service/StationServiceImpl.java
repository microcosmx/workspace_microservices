package fdse.microservice.service;

import fdse.microservice.domain.Information;
import fdse.microservice.domain.Station;
import fdse.microservice.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StationServiceImpl implements StationService {

    @Autowired
    private StationRepository repository;

    public boolean create(Information info){
        boolean result = false;
        if(repository.findByName(info.getName()) == null){
            Station station = new Station(info.getName());
            repository.save(station);
            result = true;
        }

        return result;
    }

    public boolean exist(Information info){
        boolean result = false;
        if(repository.findByName(info.getName()) != null){
            result = true;
        }
        return result;
    }
    /*
    public boolean update(Information info){
        boolean result = false;
        if(repository.findByName(info.getName()) == null){
            Station station = new Station(info.getName());
            repository.save(station);
            result = true;
        }else{
            Station station = new Station(info.getName());
            repository.
        }
        return result;
    }*/

    public boolean delete(Information info){
        boolean result = false;
        if(repository.findByName(info.getName()) != null){
            Station station = new Station(info.getName());
            repository.delete(station);
            result = true;
        }
        return result;
    }

}
