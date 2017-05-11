package train.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import train.domain.TrainType;
import train.repository.TrainTypeRepository;

@Service
public class TrainServiceImpl implements TrainService {
    @Autowired
    private TrainTypeRepository repository;

    //private static final Logger log = LoggerFactory.getLogger(Application.class);

    public boolean create(String id, int economyClass, int confortClass){
        boolean result = false;
        if(repository.findById(id) == null){
            TrainType type = new TrainType(id,economyClass,confortClass);
            repository.save(type);
            result = true;
        }
        return result;
    }

    public TrainType retrieve(String id){
       if(repository.findById(id) == null){
           //log.info("ts-train-service:retireve "+id+ " and there is no TrainType with the id:" +id);
           return null;
       }else{
           return repository.findById(id);
       }
    }

    public boolean update(String id, int economyClass, int confortClass){
        boolean result = false;
        if(repository.findById(id) != null){
            TrainType type = new TrainType(id,economyClass,confortClass);
            repository.save(type);
            result = true;
        }else{
            TrainType type = new TrainType(id,economyClass,confortClass);
            repository.save(type);
            //log.info("ts-train-service:update "+id+ " and there doesn't exist TrainType with the id:" +id);
            //log.info("ts-train-service:update "+id+ " create now!");
            result = true;
        }
        return result;
    }

    public boolean delete(String id){
        boolean result = false;
        if(repository.findById(id) == null){
            //log.info("ts-train-service:delete " + id +" and there doesn't exist TrainType with the id:" +id);
        }else{
            repository.deleteById(id);
            result = true;
        }
        return result;
    }

}
