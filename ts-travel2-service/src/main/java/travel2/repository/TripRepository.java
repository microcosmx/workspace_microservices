package travel2.repository;

import org.springframework.data.repository.CrudRepository;
import travel2.domain.Trip;
import travel2.domain.TripId;

import java.util.List;

/**
 * Created by Chenjie Xu on 2017/6/7.
 */
public interface TripRepository extends CrudRepository<Trip,TripId> {

    Trip findByTripId(TripId tripId);

    void deleteByTripId(TripId tripId);

    List<Trip> findByStartingStationIdAndTerminalStationId(String startingStationId,String terminalStationId);

    List<Trip> findByStartingStationIdAndStationsId(String startingStationId,String stationsId);

    List<Trip> findByStationsIdAndTerminalStationId(String stationsId,String terminalStationId);

    List<Trip> findAll();
}
