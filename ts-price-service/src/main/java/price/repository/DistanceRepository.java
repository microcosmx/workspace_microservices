package price.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import price.domain.Distance;

import java.util.List;

/**
 * Created by Chenjie Xu on 2017/6/12.
 */
@Repository
public interface DistanceRepository extends CrudRepository<Distance, String> {
    Distance findByPlaceAAndPlaceB(String placeA,String placeB);
    boolean deleteByPlaceAAndPlaceB(String placeA,String placeB);
    List<Distance> findAll();
}

