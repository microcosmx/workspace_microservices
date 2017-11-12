package plan.service;

import plan.domain.GetRoutePlanInfo;
import plan.domain.RoutePlanResults;
import plan.domain.TripResponse;
import java.util.ArrayList;

public interface RoutePlanService {

    ArrayList<TripResponse> searchCheapestResult(GetRoutePlanInfo info);

    ArrayList<TripResponse> searchQuickestResult(GetRoutePlanInfo info);

    RoutePlanResults searchMinStopStations(GetRoutePlanInfo info);

}
