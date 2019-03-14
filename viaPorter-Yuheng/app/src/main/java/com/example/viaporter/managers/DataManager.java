package com.example.viaporter.managers;

import com.example.viaporter.models.PatronTripRequest;
import com.example.viaporter.models.PatronTripSuccess;
import com.example.viaporter.models.TripStatus;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static final String TAG = "DataManager";

    private List<PatronTripRequest> BroadcastRequestList;
    private List<PatronTripRequest> CurrentBidList;
    private PatronTripSuccess CurrentTrip;
    private TripStatus tripStatus;

    private LatLng currentLocation;
    /*                                      *
     * Use of Bill Pugh Singleton structure *
     *                                      */
    // Private constructor //
    private DataManager() {
        BroadcastRequestList = new ArrayList<>();
        CurrentBidList = new ArrayList<>();
        tripStatus = TripStatus.IDLE;
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
    public List<PatronTripRequest> getBroadcastRequestList() {
        return BroadcastRequestList;
    }
    public List<PatronTripRequest> getCurrentBidList() {
        return CurrentBidList;
    }
    public PatronTripSuccess getCurrentTrip() { return CurrentTrip; }
    public LatLng getCurrentLocation() { return currentLocation; }
    public TripStatus getTripStatus() {
        return tripStatus;
    }

    // setters
    public void addToBroadcastList(PatronTripRequest newRequest) { BroadcastRequestList.add(newRequest); }
    public void setCurrentTrip(PatronTripSuccess newTrip){
        CurrentTrip = newTrip;
    }
    public void setCurrentLocation(LatLng currentLocation) { this.currentLocation = currentLocation; }
    public void setTripStatus(TripStatus tripStatus) { this.tripStatus = tripStatus; }

    // For showing review page after navigating to home
    private boolean showReviewInHome = false;
    public boolean isShowReviewInHome() { return showReviewInHome; }
    public void setShowReviewInHome(boolean showReviewInHome) { this.showReviewInHome = showReviewInHome; }
}
