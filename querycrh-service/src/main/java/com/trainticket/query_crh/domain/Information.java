package com.trainticket.query_crh.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by Chenjie Xu on 2017/3/1.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Information {

    @Valid
    @NotNull
    private String starting;

    @Valid
    @NotNull
    private String destination;

    @Valid
    @NotNull
    private Date date;

    public String getStarting() {
        return starting;
    }

    public void setStarting(String starting) {
        this.starting = starting;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
