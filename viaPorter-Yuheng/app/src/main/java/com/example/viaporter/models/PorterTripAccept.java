package com.example.viaporter.models;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PorterTripAccept {

    @SerializedName("accepted_porter_id")
    @Expose
    private String porterID;

    @SerializedName("accepted_porter_name")
    @Expose
    private String porterName;

    @SerializedName("porter_location")
    @Expose
    private LatLng porterLocation;

    @SerializedName("porter_rating")
    @Expose
    private Double porterRating;

    public String getPorterID() { return porterID; };
    public void  setPorterID(String porterID) { this.porterID = porterID; }
    public String getPorterName() { return porterName; }
    public void setPorterName(String porterName) { this.porterName = porterName; }
    public LatLng getPorterLocation() { return porterLocation; }
    public void setPorterLocation(LatLng porterLocation) { this.porterLocation = porterLocation; }
    public Double getPorterRating() { return porterRating; }
    public void setPorterRating(Double porterRating) { this.porterRating = porterRating;}

    public PorterTripAccept(String id, String porterName, LatLng porterLocation, Double porterRating) {
        this.porterID = id;
        this.porterName = porterName;
        this.porterLocation = porterLocation;
        this.porterRating = porterRating;
    }
}
