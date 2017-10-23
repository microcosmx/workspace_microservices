package food.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Document(collection = "trainfoods")
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrainFood {

    public TrainFood(){

    }

    @Id
    private UUID id;

    @NotNull
    private UUID travelId;

    private List<Food> foodList;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getTravelId() {
        return travelId;
    }

    public void setTravelId(UUID travelId) {
        this.travelId = travelId;
    }

    public List<Food> getFoodList() {
        return foodList;
    }

    public void setFoodList(List<Food> foodList) {
        this.foodList = foodList;
    }

}
