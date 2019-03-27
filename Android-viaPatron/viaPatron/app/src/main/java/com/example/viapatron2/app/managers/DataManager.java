package com.example.viapatron2.app.managers;

import com.example.viapatron2.core.models.PorterBidRequest;
import com.example.viapatron2.core.models.TripStatus;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Lim Zhiming on 10/1/19.
 */
public class DataManager {

    private static final String TAG = "DataManager";

    private TripStatus tripStatus;
    private LatLng currentLocation;
    private String myTripRequestKey;
    private PorterBidRequest acceptedBidRequest;

    public DataManager() {
        tripStatus = TripStatus.NOT_STARTED;
    }

    public void updateTripStatus(TripStatus updatedTripStatus) {
        this.tripStatus = updatedTripStatus;
    }

    public TripStatus getTripStatus() {
        return tripStatus;
    }

    public void setCurrentLocation(LatLng location) {
        currentLocation = location;
    }

    public LatLng getCurrentLocation() {
        return currentLocation;
    }

    public void setMyTripRequestKey(String key) {
        myTripRequestKey = key;
    }

    public String getMyTripRequestKey() {
        return myTripRequestKey;
    }

    public PorterBidRequest getAcceptedBidRequest() { return acceptedBidRequest; }

    public void setAcceptedBidRequest(PorterBidRequest acceptedBidRequest) { this.acceptedBidRequest = acceptedBidRequest; }
}
