package com.example.viapatron2.core.models;


import androidx.navigation.NavDestination;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Lim Zhiming on 10/1/19.
 * Adapting from Auto
 */

// Originally extends RealmObject (in Auto)
public class UserTripRequestSession {

    @SerializedName("start")
    @Expose
    private LatLng start;

    @SerializedName("end")
    @Expose
    private LatLng end;

    @SerializedName("station")
    @Expose
    private String station;

    @SerializedName("station_id")
    @Expose
    private long stationId;

    private String date;
    private String startLocation;
    private String endLocation;
    private String noOfLuggages;
    private NavDestination currentDest;

    public LatLng getStart() {
        return start;
    }

    public void setStart(LatLng start) {
        this.start = start;
    }

    public LatLng getEnd() {
        return end;
    }

    public void setEnd(LatLng end) {
        this.end = end;
    }

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
