package com.example.viapatron2.core.models;


import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Lim Zhiming on 25/2/19.
 */
public class Trip {


    @SerializedName("patron_location")
    @Expose
    private LatLng patronLocation;

    @SerializedName("pickup_location")
    @Expose
    private String pickupLocation;

    @SerializedName("dropoff_location")
    @Expose
    private String dropoffLocation;

    public LatLng getPatronLocation() {
        return patronLocation;
    }

    public void setPatronLocation(LatLng patronLocation) {
        this.patronLocation = patronLocation;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public String getDropoffLocation() {
        return dropoffLocation;
    }

    public void setDropoffLocation(String dropoffLocation) {
        this.dropoffLocation = dropoffLocation;
    }
}
