package travel.repository;

import org.springframework.data.repository.CrudRepository;
import travel.domain.Trip;

/**
 * Created by Chenjie Xu on 2017/5/15.
 */
public interface TripRepository extends CrudRepository<Trip,String> {
    Trip findByTripId(String tripId);
    void deleteByTripId(String tripId);
}
