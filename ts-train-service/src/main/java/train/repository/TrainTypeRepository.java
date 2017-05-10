package train.repository;

import org.springframework.data.repository.CrudRepository;
import train.domain.TrainType;

/**
 * Created by Chenjie Xu on 2017/5/8.
 */
public interface TrainTypeRepository extends CrudRepository<TrainType,String>{

    TrainType findById(String id);
    //void save(TrainType trainType);
    //void deleteById(String id);
    //void update();
}
