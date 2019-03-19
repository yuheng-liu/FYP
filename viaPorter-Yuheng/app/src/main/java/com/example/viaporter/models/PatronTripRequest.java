package com.example.viaporter.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PatronTripRequest {

    @SerializedName("patron_id")
    @Expose
    private String patronID;

    @SerializedName("train_station_name")
    @Expose
    private String trainStationName;

    @SerializedName("trip_start_location")
    @Expose
    private String tripStartLocation;

    @SerializedName("trip_end_location")
    @Expose String tripEndLocation;

    @SerializedName("number_of_luggage")
    @Expose
    private int numberOfLuggage;

    @SerializedName("total_luggage_weight")
    @Expose
    private int totalLuggageWeight;

    public String getPatronID() { return  patronID; };
    public String getTrainStationName() { return trainStationName; }
    public String getTripStartLocation() { return tripStartLocation; }
    public String getTripEndLocation() { return tripEndLocation; }
    public int getNumberOfLuggage() { return numberOfLuggage; }
    public int getTotalLuggageWeight() { return totalLuggageWeight; }

    public PatronTripRequest(String id, String name, String start, String end, int luggage, int weight) {
        patronID = id;
        trainStationName = name;
        tripStartLocation = start;
        tripEndLocation = end;
        numberOfLuggage = luggage;
        totalLuggageWeight = weight;
    }
}
