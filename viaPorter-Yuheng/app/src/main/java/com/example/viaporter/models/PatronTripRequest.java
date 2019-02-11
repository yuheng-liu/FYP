package com.example.viaporter.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PatronTripRequest {

    @SerializedName("train_station_name")
    @Expose
    private String TrainStationName;

    @SerializedName("trip_start_location")
    @Expose
    private String tripStartLocation;

    @SerializedName("trip_end_location")
    @Expose String tripEndLocation;

    @SerializedName("number_of_luggage")
    @Expose
    private int numberOfLuggage;
}
