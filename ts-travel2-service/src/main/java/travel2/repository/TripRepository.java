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

    List<Trip> findByStartingStationAndTerminalStation(String startingStation, String terminalStation);

    List<Trip> findByStartingStationAndStations(String startingStation,String stations);

    List<Trip> findByStationsAndTerminalStation(String stations,String terminalStation);

    List<Trip> findAll();
}
