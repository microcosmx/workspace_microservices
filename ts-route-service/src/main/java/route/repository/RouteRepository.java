package route.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import route.domain.Route;
import java.util.ArrayList;
import java.util.UUID;

@Repository
public interface RouteRepository extends MongoRepository<Route, String> {

    @Query("{ 'id': ?0 }")
    Route findById(UUID id);

    void removeRouteById(UUID id);

    @Query("{ 'startStationId': ?0 , 'terminalStationId': ?1 }")
    ArrayList<Route> findByStartStationIdAndTerminalStationId(String startingId, String terminalId);

}
