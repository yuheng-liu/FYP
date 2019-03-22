package com.example.viapatron2.core.models;


import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Lim Zhiming on 10/3/19.
 * Followed Yuheng's version.
 */
public class PorterTripAccept {

    @SerializedName("accepted_porter_name")
    @Expose
    private String porterName;

    @SerializedName("accepted_porter_id")
    @Expose
    private String porterId;

    @SerializedName("porter_location")
    @Expose
    private LatLng porterLocation;

    @SerializedName("porter_rating")
    @Expose
    private Double porterRating;

    public String getPorterName() { return porterName; }
    public void setPorterName(String porterName) { this.porterName = porterName; }
    public String getPorterId() { return porterId; }
    public void setPorterId(String porterId) { this.porterId = porterId; }
    public LatLng getPorterLocation() { return porterLocation; }
    public void setPorterLocation(LatLng porterLocation) { this.porterLocation = porterLocation; }
    public Double getPorterRating() { return porterRating; }
    public void setPorterRating(Double porterRating) { this.porterRating = porterRating;}
}
