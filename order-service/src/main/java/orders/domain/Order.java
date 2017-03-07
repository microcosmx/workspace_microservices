package orders.domain;


import java.util.Date;

public class Order {

    private int id;
    private String orderNumber;
    private Date boughtDate;
    private User user;
    private TrainNumber trainNumber;
    private int coachNumber;
    private int seatClass;
    private SeatNumber seatNumber;
    private String from;
    private String to;
    private int status;


}
