package travelplan.service;

import travelplan.domain.TransferTravelSearchInfo;
import travelplan.domain.TransferTravelSearchResult;

public interface TravelPlanService {

    TransferTravelSearchResult getTransferSearch(TransferTravelSearchInfo info);

}
