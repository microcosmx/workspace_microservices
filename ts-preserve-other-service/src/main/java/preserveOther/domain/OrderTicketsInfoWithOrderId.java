package preserveOther.domain;

import java.util.Date;

/**
 * Created by fdse-jichao on 2017/8/13.
 */
public class OrderTicketsInfoWithOrderId {
    private String contactsId;

    private String tripId;

    private int seatType;

    private Date date;//具体到哪一天

    private String from;

    private String to;

    private String orderId;

    public OrderTicketsInfoWithOrderId(){
        //Default Constructor
    }

    public OrderTicketsInfoWithOrderId(String contactsId, String tripId, int seatType, Date date, String from, String to, String orderId) {
        this.contactsId = contactsId;
        this.tripId = tripId;
        this.seatType = seatType;
        this.date = date;
        this.from = from;
        this.to = to;
        this.orderId = orderId;
    }

    public String getContactsId() {
        return contactsId;
    }

    public void setContactsId(String contactsId) {
        this.contactsId = contactsId;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public int getSeatType() {
        return seatType;
    }

    public void setSeatType(int seatType) {
        this.seatType = seatType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
