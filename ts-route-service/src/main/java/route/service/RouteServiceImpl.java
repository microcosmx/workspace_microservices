package route.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import route.domain.*;
import route.repository.RouteRepository;
import java.util.ArrayList;
import java.util.UUID;

@Service
public class RouteServiceImpl implements RouteService {

    @Autowired
    private RouteRepository routeRepository;

    @Override
    public CreateAndModifyRouteResult createAndModify(CreateAndModifyRouteInfo info){
        Route route = info.getRoute();
        if(route.getId() == null || route.getId().toString().length() < 10){
            route.setId(UUID.randomUUID());
        }
        routeRepository.save(route);
        CreateAndModifyRouteResult result = new CreateAndModifyRouteResult(
                true,"Success",route
        );
        return result;
    }

    @Override
    public DeleteRouteResult deleteRoute(DeleteRouteInfo info){
        routeRepository.removeRouteById(UUID.fromString(info.getRouteId()));
        DeleteRouteResult result = new DeleteRouteResult(true,"Success");
        return result;
    }

    @Override
    public GetRouteByIdResult getRouteById(GetRouteByIdInfo info){
        Route route = routeRepository.findById(UUID.fromString(info.getRouteId()));
        GetRouteByIdResult result;
        if(route == null){
            result = new GetRouteByIdResult(false,"Route Not Exist",null);
        }else{
            result = new GetRouteByIdResult(true,"Success",route);
        }
        return result;
    }

    @Override
    public GetRouteByStartAndTerminalResult getRouteByStartAndTerminal(GetRouteByStartAndTerminalInfo info){
        ArrayList<Route> routes = routeRepository.findByStartStationIdAndTerminalStationId(info.getStartId(),info.getTerminalId());
        if(routes == null){
            routes = new ArrayList<>();
        }
        GetRouteByStartAndTerminalResult result = new GetRouteByStartAndTerminalResult(
                true, "Success", routes
        );
        return result;
    }

}
