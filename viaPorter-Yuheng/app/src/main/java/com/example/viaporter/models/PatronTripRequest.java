package com.example.viaporter.models;

public class PatronTripRequest {

    private String date;
    private String patronUid;
    private String tripStartLocation;
    private String tripEndLocation;
    private int numberOfLuggage;
    private int totalLuggageWeight;

    public String getPatronUid() { return patronUid; }
    public String getTripStartLocation() { return tripStartLocation; }
    public String getTripEndLocation() { return tripEndLocation; }
    public int getNumberOfLuggage() { return numberOfLuggage; }
    public int getTotalLuggageWeight() { return totalLuggageWeight; }
    public String getDate() { return date; }

    public PatronTripRequest() {}

    public PatronTripRequest(String newDate, String id, String start, String end, int luggage, int weight) {
        date = newDate;
        patronUid = id;
        tripStartLocation = start;
        tripEndLocation = end;
        numberOfLuggage = luggage;
        totalLuggageWeight = weight;
    }
}
