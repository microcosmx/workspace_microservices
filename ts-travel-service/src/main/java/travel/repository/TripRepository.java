package travel.repository;

import org.springframework.data.repository.CrudRepository;
import travel.domain.Trip;
import travel.domain.TripId;
import travel.domain.TripResponse;

import java.util.List;

/**
 * Created by Chenjie Xu on 2017/5/15.
 */
public interface TripRepository extends CrudRepository<Trip,TripId> {

    Trip findByTripId(TripId tripId);

    void deleteByTripId(TripId tripId);

    List<Trip> findByStartingStationIdAndTerminalStationId(String startingStationId,String terminalStationId);

    List<Trip> findByStartingStationIdAndStationsId(String startingStationId,String stationsId);

    List<Trip> findByStationsIdAndTerminalStationId(String stationsId,String terminalStationId);

    List<Trip> findAll();
}
