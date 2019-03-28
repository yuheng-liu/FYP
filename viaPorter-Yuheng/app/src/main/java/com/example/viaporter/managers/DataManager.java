package com.example.viaporter.managers;

import com.example.viaporter.models.PatronTripRequest;
import com.example.viaporter.models.PorterUserDetails;
import com.example.viaporter.models.TripState;
import com.google.android.gms.maps.model.LatLng;

public class DataManager {
    private static final String TAG = "DataManager";

    private PorterUserDetails porterUserDetails;
    private PatronTripRequest selectedBidRequest;
    private TripState tripState;

    private LatLng currentLocation;

    /*                                      *
     * Use of Bill Pugh Singleton structure *
     *                                      */
    // Private constructor //
    private DataManager() {
        porterUserDetails = new PorterUserDetails();
        tripState = TripState.NOT_STARTED;
    }

    // Static inner class are not loaded until they are referenced
    private static class dataManagerholder {
        private static DataManager manager = new DataManager();
    }
    // Global excess point
    public static DataManager getSharedInstance() {
        return dataManagerholder.manager;
    }
    /* ************************************ */

    // getters
    public LatLng getCurrentLocation() { return currentLocation; }
    public TripState getTripState() {
        return tripState;
    }
    public PorterUserDetails getPorterUserDetails() { return  porterUserDetails; }
    public PatronTripRequest getSelectedBidRequest() { return selectedBidRequest; }

    // setters
    public void setCurrentLocation(LatLng currentLocation) { this.currentLocation = currentLocation; }
    public void setTripState(TripState tripState) { this.tripState = tripState; }
    public void setSelectedBidRequest(PatronTripRequest selectedBidRequest) { this.selectedBidRequest = selectedBidRequest; }
}
