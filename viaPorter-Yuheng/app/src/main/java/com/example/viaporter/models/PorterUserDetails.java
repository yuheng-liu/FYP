package com.example.viaporter.models;

public class PorterUserDetails {
    private String id;
    private String name;
    private Double rating;

    // for testing
    public PorterUserDetails() {
        this.id = "liuyuheng";
        this.name = "Yuheng";
        this.rating = 4.5;
    }

    public PorterUserDetails(String id, String name, Double rating) {
        this.id = id;
        this.name = name;
        this.rating = rating;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }
}
