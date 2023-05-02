package com.example.courseworkfinal;

import java.io.Serializable;

public class Detail implements Serializable { // https://stackoverflow.com/questions/21964281/how-to-pass-the-item-id-to-another-activity

    private String trip;
    private String transport;
    private String currency;
    private String destination;
    private String date;
    private String risk;
    private String description;
    private int details_id;

    public Detail(String trip, String transport, String currency, String destination,
                  String date, String risk, String description)
    {
        this.trip = trip;
        this.transport = transport;
        this.currency = currency;
        this.destination = destination;
        this.date = date;
        this.risk = risk;
        this.description = description;
    }

    public Detail(int details_id, String trip, String transport, String currency, String destination,
                  String date, String risk, String description)
    {
        this.details_id = details_id;
        this.trip = trip;
        this.transport = transport;
        this.currency = currency;
        this.destination = destination;
        this.date = date;
        this.risk = risk;
        this.description = description;
    }

    public String getTrip() { return trip; }
    public void setTrip(String trip) {  this.trip = trip; }

    public String getTransport() { return transport; }
    public void setTransport(String transport) { this.transport = transport; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getRisk() { return risk; }
    public void setRisk(String risk) { this.risk = risk; }

    public String getDescription() { return description;}
    public void setDescription(String description) { this.description = description; }

    public int getDetails_id() {  return details_id; }
    public void setDetails_id(int details_id) { this.details_id = details_id; }
}
