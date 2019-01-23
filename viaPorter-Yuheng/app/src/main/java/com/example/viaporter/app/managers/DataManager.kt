package com.example.viaporter.app.managers

import com.example.viaporter.core.models.UserSession

/**
 * Created by Lim Zhiming on 10/1/19.
 */
class DataManager {

    // todo: possible alternatives to realm in the works
    //        if (userSession == null || userSession.isValid()) {
    //            synchronized (this) {
    //                userSession = realm.where(UserSession.class).findFirst();
    //            }
    //        }
    val userSession: UserSession? = null

    companion object {

        private val TAG = "DataManager"
    }
}// init GeoApiContext
//        geoApiContext = new GeoApiContext.Builder()
//                .apiKey(APIKeys.GOOGLE_GEO_API_KEY)
//                .build();
//        // default value
//        currentTripStatus = TripStatus.NOT_STARTED;
//        riderInfo = new RiderInfo();
//        currentContext = UserContext.HOME;
