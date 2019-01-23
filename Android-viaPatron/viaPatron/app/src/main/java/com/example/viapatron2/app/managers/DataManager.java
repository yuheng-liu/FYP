package com.example.viapatron2.app.managers;

import com.example.viapatron2.BidderInfo;
import com.example.viapatron2.core.models.UserTripRequestSession;

/**
 * Created by Lim Zhiming on 10/1/19.
 */
public class DataManager {

    private static final String TAG = "DataManager";

    private UserTripRequestSession userTripRequestSession;
    private BidderInfo bidderInfo;

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

    public void importData() {
//        realm = Realm.getDefaultInstance();
//        userSession = realm.where(UserSession.class).findFirst();
    }

    public UserTripRequestSession getUserTripRequestSession() {
        // todo: possible alternatives to realm in the works
//        if (userTripRequestSession == null || userTripRequestSession.isValid()) {
//            synchronized (this) {
//                userTripRequestSession = realm.where(UserTripRequestSession.class).findFirst();
//            }
//        }
        return userTripRequestSession;
    }
}
