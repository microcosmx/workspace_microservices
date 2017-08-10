package preserveOther.domain;

import java.util.Date;

public class OrderTicketsInfo {

    private String contactsId;

    private String isCreateContacts;

    private String contactsName;

    private int contactsDocumentType;

    private String contactsDocumentNumber;

    private String contactsPhoneNumber;

    private String tripId;

    private int seatType;

    private Date date;//具体到哪一天

    private String from;

    private String to;



    public OrderTicketsInfo(){
        //Default Constructor
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

    public String getContactsName() {
        return contactsName;
    }

    public void setContactsName(String contactsName) {
        this.contactsName = contactsName;
    }

    public int getContactsDocumentType() {
        return contactsDocumentType;
    }

    public void setContactsDocumentType(int contactsDocumentType) {
        this.contactsDocumentType = contactsDocumentType;
    }

    public String getContactsDocumentNumber() {
        return contactsDocumentNumber;
    }

    public void setContactsDocumentNumber(String contactsDocumentNumber) {
        this.contactsDocumentNumber = contactsDocumentNumber;
    }

    public String getContactsPhoneNumber() {
        return contactsPhoneNumber;
    }

    public void setContactsPhoneNumber(String contactsPhoneNumber) {
        this.contactsPhoneNumber = contactsPhoneNumber;
    }

    public String getIsCreateContacts() {
        return isCreateContacts;
    }

    public void setIsCreateContacts(String isCreateContacts) {
        this.isCreateContacts = isCreateContacts;
    }
}
