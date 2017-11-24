package admintravel.service;

import admintravel.domain.request.AddAndModifyTravelRequest;
import admintravel.domain.request.DeleteTravelRequest;
import admintravel.domain.response.AdminFindAllResult;

public interface AdminTravelService {
    AdminFindAllResult getAllTravels(String id);
    String addTravel(AddAndModifyTravelRequest request);
    String updateTravel(AddAndModifyTravelRequest request);
    String deleteTravel(DeleteTravelRequest request);
}
