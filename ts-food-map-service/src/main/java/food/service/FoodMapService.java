package food.service;

import food.domain.FoodStore;
import food.domain.TrainFood;

import java.util.List;

public interface FoodMapService {

    FoodStore createFoodStore(FoodStore fs);

    TrainFood createTrainFood(TrainFood tf);

    List<FoodStore> listFoodStores();

    List<TrainFood> listTrainFood();


}
