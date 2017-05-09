package register.repository;

import org.springframework.data.repository.CrudRepository;
import register.domain.TrainType;

import java.util.List;

/**
 * Created by Chenjie Xu on 2017/5/8.
 */
public interface TrainTypeRepository extends CrudRepository{

    TrainType findById(String id);
    void save(TrainType trainType);
    void deleteById(String id);
    //void update();
}
