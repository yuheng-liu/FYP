package com.example.viapatron2.app.managers;

import com.example.viapatron2.core.models.UserSession;

/**
 * Created by Lim Zhiming on 10/1/19.
 */
public class DataManager {

    private static final String TAG = "DataManager";

    private UserSession userSession;

    public DataManager() {
        // init GeoApiContext
//        geoApiContext = new GeoApiContext.Builder()
//                .apiKey(APIKeys.GOOGLE_GEO_API_KEY)
//                .build();
//        // default value
//        currentTripStatus = TripStatus.NOT_STARTED;
//        riderInfo = new RiderInfo();
//        currentContext = UserContext.HOME;
    }

    public UserSession getUserSession() {
        // todo: possible alternatives to realm in the works
//        if (userSession == null || userSession.isValid()) {
//            synchronized (this) {
//                userSession = realm.where(UserSession.class).findFirst();
//            }
//        }
        return userSession;
    }
}
