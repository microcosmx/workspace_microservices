package register.repository;

import org.springframework.data.repository.CrudRepository;
import register.domain.Station;

/**
 * Created by Chenjie Xu on 2017/5/9.
 */
public interface StationRepository extends CrudRepository {
    Station findByName(String name);
    void save(Station station);
    void deleteByName(String name);
    //void update();
}
