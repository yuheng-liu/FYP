package com.example.viaporter.models;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocationUpdate {
    public LocationUpdate(LatLng newLocation) {
        this.location = newLocation;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng newLocation) {
        location = newLocation;
    }

    @SerializedName("location_update")
    @Expose
    private LatLng location;
}
