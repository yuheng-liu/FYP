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

    //@SerializedName("start")
    //@Expose
    private LatLng start;

    //@SerializedName("end")
    //@Expose
    private LatLng end;

    //@SerializedName("station_id")
    //@Expose
    private long stationId;
    private String date;

    @SerializedName("train_station_name")
    @Expose
    private String station;

    @SerializedName("trip_start_location")
    @Expose
    private String fromLocation;

    @SerializedName("trip_end_location")
    @Expose
    private String toLocation;

    @SerializedName("number_of_luggage")
    @Expose
    private int noOfLuggage = 0;

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
        if (station != null) {
            return station;
        } else {
            return "";
        }
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
        if (date != null) {
            return date;
        } else {
            return "";
        }
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String startLocation) {
        this.fromLocation = startLocation;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public int getNoOfLuggage() {
        return noOfLuggage;
    }

    public void setNoOfLuggage(int noOfLuggage) {
        this.noOfLuggage = noOfLuggage;
    }
}
