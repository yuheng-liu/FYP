package com.example.viaporter.managers;

import com.example.viaporter.models.PatronTripRequest;

import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static final String TAG = "DataManager";

    private List<PatronTripRequest> patronTripRequestList;

    /*                                      *
     * Use of Bill Pugh Singleton structure *
     *                                      */
    // Private constructor //
    private DataManager() {
        resetData();
    }
    // Static inner class are not loaded until they are referenced
    private static class dataManagerholder {
        private static DataManager manager = new DataManager();
    }
    // Global excess point
    public static DataManager getSharedInstance() {
        return dataManagerholder.manager;
    }
    /*                                      */

    public void resetData() {
        patronTripRequestList = new ArrayList<PatronTripRequest>();
    }

    // getter & setters
    public List<PatronTripRequest> getPatronTripRequestList() {
        return patronTripRequestList;
    }

    public void addPatronTripRequest(PatronTripRequest newRequest) {
        patronTripRequestList.add(newRequest);
    }
}
