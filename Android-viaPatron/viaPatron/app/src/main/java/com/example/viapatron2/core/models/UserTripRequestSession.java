package com.example.viapatron2.core.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lim Zhiming on 10/1/19.
 */

// Originally extends RealmObject
public class UserTripRequestSession {

    private String date;

    @SerializedName("patron_id")
    @Expose
    private String patronID;

    @SerializedName("train_station_name")
    @Expose
    private String station;

    @SerializedName("trip_start_location")
    @Expose
    private String tripStartLocation;

    @SerializedName("trip_end_location")
    @Expose
    private String tripEndLocation;

    @SerializedName("number_of_luggage")
    @Expose
    private int numberOfLuggage = 0;

    @SerializedName("total_luggage_weight")
    @Expose
    private int totalLuggageWeight = 0;

    public String getPatronID() {
        return patronID;
    }

    public void setPatronID(String patronID) {
        this.patronID = patronID;
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

    public String getTripStartLocation() {
        return tripStartLocation;
    }

    public void setTripStartLocation(String startLocation) {
        this.tripStartLocation = startLocation;
    }

    public String getTripEndLocation() {
        return tripEndLocation;
    }

    public void setTripEndLocation(String tripEndLocation) {
        this.tripEndLocation = tripEndLocation;
    }

    public int getNumberOfLuggage() {
        return numberOfLuggage;
    }

    public void setNumberOfLuggage(int numberOfLuggage) {
        this.numberOfLuggage = numberOfLuggage;
    }

    public int getTotalLuggageWeight() {
        return totalLuggageWeight;
    }

    public void setTotalLuggageWeight(int totalLuggageWeight) {
        this.totalLuggageWeight = totalLuggageWeight;
    }

    public Map<String, Object> toMap() {

        HashMap<String, Object> result = new HashMap<>();

        result.put("date", date);
        result.put("patronID", patronID);
        result.put("tripStartLocation", tripStartLocation);
        result.put("tripEndLocation", tripEndLocation);
        result.put("numberOfLuggage", numberOfLuggage);
        result.put("totalLuggageWeight", totalLuggageWeight);

        return result;
    }
}
