package foodsearch.service;

import foodsearch.domain.GetAllFoodOfTripInfo;
import foodsearch.domain.GetAllFoodOfTripResult;

public interface FoodService {

    GetAllFoodOfTripResult getAllFood(String date, String startStation, String endStation, String tripId);
}
