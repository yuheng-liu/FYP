package com.example.viaporter.models;

public class PorterUserDetails {
    private String porterUid;
    private String name;
    private Double rating;
    private TripState tripState;

    public PorterUserDetails() { }

    public PorterUserDetails(String porterUid, String name, Double rating, TripState tripState) {
        this.porterUid = porterUid;
        this.name = name;
        this.rating = rating;
        this.tripState = tripState;
    }

    public String getPorterUid() { return porterUid; }
    public void setPorterUid(String porterUid) { this.porterUid = porterUid; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }
    public TripState getTripState() { return tripState; }
    public void setTripState(TripState tripState) { this.tripState = tripState; }
}
