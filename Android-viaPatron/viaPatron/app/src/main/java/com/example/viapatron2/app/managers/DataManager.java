package com.example.viapatron2.app.managers;

import com.example.viapatron2.core.models.TripStatus;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Lim Zhiming on 10/1/19.
 */
public class DataManager {

    private static final String TAG = "DataManager";

    private TripStatus tripStatus;
    private LatLng currentLocation;

    public DataManager() {
        tripStatus = TripStatus.NOT_STARTED;
    }

    public void setCurrentLocation(LatLng location) {
        currentLocation = location;
    }

    public LatLng getCurrentLocation() {
        return currentLocation;
    }
}
