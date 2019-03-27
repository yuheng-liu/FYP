package com.example.viapatron2.core.models;

public class MyPatron {

    private String patronUid;
    private TripStatus tripState;
    private PorterBidRequest porterBidRequest;

    public MyPatron() {

    }

    public MyPatron(String name, TripStatus tripState) {

        patronUid = name;
        this.tripState = tripState;
        porterBidRequest = new PorterBidRequest();
    }

    public void setPatronUid(String patronUid) {
        this.patronUid = patronUid;
    }

    public String getPatronUid() {
        return patronUid;
    }

    public void setTripState(TripStatus tripState) {
        this.tripState = tripState;
    }

    public TripStatus getTripState() {
        return tripState;
    }

    public PorterBidRequest getPorterBidRequest() {
        return porterBidRequest;
    }

    public void setPorterBidRequest(PorterBidRequest porterBidRequest) {
        this.porterBidRequest = porterBidRequest;
    }
}
