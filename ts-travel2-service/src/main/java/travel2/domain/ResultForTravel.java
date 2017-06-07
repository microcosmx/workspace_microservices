package travel2.domain;

/**
 * Created by Chenjie Xu on 2017/6/6.
 */
public class ResultForTravel {
    private boolean status;
    private double percent;
    private TrainType trainType;

    public ResultForTravel(){}

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent){
        this.percent = percent;
    }

    public TrainType getTrainType() {
        return trainType;
    }

    public void setTrainType(TrainType trainType) {
        this.trainType = trainType;
    }
}
