package query.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;
import java.util.UUID;

@Document(collection = "orders")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {

    @Id
    private UUID id;

    private Date boughtDate;

    private Date travelDate;

    private UUID accountId;

    private TrainNumber trainNumber;

    private int coachNumber;

    private int seatClass;

    private SeatNumber seatNumber;

    private String from;

    private String to;

    private int status;

    public Order(){
        boughtDate = new Date(System.currentTimeMillis());
        travelDate = new Date(System.currentTimeMillis());
        trainNumber = new TrainNumber();
        trainNumber.setType(TrainType.GAOTIE.getCode());
        trainNumber.setNumber(2001);
        coachNumber = 5;
        seatClass = SeatClass.FIRSTCLASS.getCode();
        seatNumber = new SeatNumber();
        seatNumber.setLineNum(5);
        seatNumber.setPosition('A');
        from = "上海虹桥";
        to = "南京南";
        status = OrderStatus.PAID.getCode();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    public Date getBoughtDate() {
        return boughtDate;
    }

    public void setBoughtDate(Date boughtDate) {
        this.boughtDate = boughtDate;
    }

    public Date getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(Date travelDate) {
        this.travelDate = travelDate;
    }

    public TrainNumber getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(TrainNumber trainNumber) {
        this.trainNumber = trainNumber;
    }

    public int getCoachNumber() {
        return coachNumber;
    }

    public void setCoachNumber(int coachNumber) {
        this.coachNumber = coachNumber;
    }

    public int getSeatClass() {
        return seatClass;
    }

    public void setSeatClass(int seatClass) {
        this.seatClass = seatClass;
    }

    public SeatNumber getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(SeatNumber seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
