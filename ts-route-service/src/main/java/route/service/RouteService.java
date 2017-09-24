package route.service;

import route.domain.*;

public interface RouteService {

    GetRouteByStartAndTerminalResult getRouteByStartAndTerminal(GetRouteByStartAndTerminalInfo info);

    GetRouteByIdResult getRouteById(GetRouteByIdInfo info);

    DeleteRouteResult deleteRoute(DeleteRouteInfo info);

    CreateAndModifyRouteResult createAndModify(CreateAndModifyRouteInfo info);

}
