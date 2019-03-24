package com.example.viaporter.models;

import java.util.ArrayList;
import java.util.List;

public class PorterUserDetails {
    private String porterUid;
    private String name;
    private Double rating;
    private List<PatronTripRequest> currentBids;

    // for testing
    public PorterUserDetails() {
        porterUid = "liuyuheng";
        name = "Yuheng";
        rating = 4.5;
        currentBids = new ArrayList<PatronTripRequest>();
    }

    public PorterUserDetails(String porterUid, String name, Double rating) {
        this.porterUid = porterUid;
        this.name = name;
        this.rating = rating;
        currentBids = new ArrayList<PatronTripRequest>();
    }

    public String getId() { return porterUid; }
    public void setId(String porterUid) { this.porterUid = porterUid; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }
    public List<PatronTripRequest> getCurrentBids() { return currentBids; }
    public void setCurrentBids(List<PatronTripRequest> currentBids) { this.currentBids = currentBids; }
}
