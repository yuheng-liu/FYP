package com.example.viapatron2.core.models;

public class FirebaseLocationUpdate {

    private Double latitude;
    private Double longitude;

    // empty constructor
    public FirebaseLocationUpdate() {

    }

    public FirebaseLocationUpdate(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Double getLat() { return latitude; }
    public Double getLong() { return longitude; }
    public void setLat(Double latitude) { this.latitude = latitude; }
    public void setLong(Double longitude) { this.longitude = longitude; }
}
