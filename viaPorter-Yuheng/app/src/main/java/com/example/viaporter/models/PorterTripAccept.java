package com.example.viaporter.models;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PorterTripAccept {

    @SerializedName("accepted_porter_name")
    @Expose
    private String porterName;

    @SerializedName("porter_location")
    @Expose
    private LatLng porterLocation;

    @SerializedName("porter_rating")
    @Expose
    private String porterRating;

    public String getPorterName() { return porterName; }
    public void setPorterName(String porterName) { this.porterName = porterName; }
    public LatLng getPorterLocation() { return porterLocation; }
    public void setPorterLocation(LatLng porterLocation) { this.porterLocation = porterLocation; }
    public String getPorterRating() { return porterRating; }
    public void setPorterRating(String porterRating) { this.porterRating = porterRating;}

    public PorterTripAccept(String porterName, LatLng porterLocation, String porterRating) {
        this.porterName = porterName;
        this.porterLocation = porterLocation;
        this.porterRating = porterRating;
    }
}
