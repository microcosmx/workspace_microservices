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

    List<Trip> findByStartingStationAndTerminalStation(String startingStation,String terminalStation);

    List<Trip> findByStartingStationAndStations(String startingStation,String stations);

    List<Trip> findByStationsAndTerminalStation(String stations,String terminalStation);

    List<Trip> findAll();
}
