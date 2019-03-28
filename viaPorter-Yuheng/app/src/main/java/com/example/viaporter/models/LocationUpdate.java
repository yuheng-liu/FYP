package com.example.viaporter.models;

public class LocationUpdate {
    private Double latitude;
    private Double longitude;

    public LocationUpdate(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public LocationUpdate() {}

    public Double getLat() {
        return latitude;
    }
    public Double getLong() { return longitude; }
    public void setLat(Double latitude) {
        this.latitude = latitude;
    }
    public void setLong(Double longitude) { this.longitude = longitude; }
}
