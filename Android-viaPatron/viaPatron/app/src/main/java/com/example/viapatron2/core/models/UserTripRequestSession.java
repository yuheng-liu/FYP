package com.example.viapatron2.core.models;


import androidx.navigation.NavDestination;

/**
 * Created by Lim Zhiming on 10/1/19.
 * Adapting from Auto
 */

// Originally extends RealmObject (in Auto)
public class UserTripRequestSession {
    private String station;
    private long stationId;
    private String date;
    private String startLocation;
    private String endLocation;
    private String noOfLuggages;
    private NavDestination currentDest;


    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public long getStationId() {
        return stationId;
    }

    public void setStationId(long stationId) {
        this.stationId = stationId;
    }

    public NavDestination getCurrentDest() {
        return currentDest;
    }

    public void setCurrentDest(NavDestination currentDest) {
        this.currentDest = currentDest;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public String getNoOfLuggages() {
        return noOfLuggages;
    }

    public void setNoOfLuggages(String noOfLuggages) {
        this.noOfLuggages = noOfLuggages;
    }
}
