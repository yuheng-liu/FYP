package com.example.viapatron2.core.models;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocationUpdate {

    @SerializedName("location_update")
    @Expose
    private LatLng updatedLocation;

    public LatLng getUpdatedLocation() {
        return updatedLocation;
    }

    public void setUpdatedLocation(LatLng updatedLocation) {
        this.updatedLocation = updatedLocation;
    }
}
