package com.example.viapatron2.app.managers;

import com.example.viapatron2.core.models.UserSession;
import com.example.viapatron2.core.models.UserTripRequestSession;
import io.realm.Realm;

/**
 * Created by Lim Zhiming on 10/1/19.
 */
public class DataManager {

    private static final String TAG = "DataManager";

    private Realm realm;

    private UserTripRequestSession userTripRequestSession;
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

    public void createSession(String sessionId) {
        realm.beginTransaction();
        if (userSession == null) {
            userSession = realm.createObject(UserSession.class);
        }
        userSession.setSessionId(sessionId);
        realm.commitTransaction();
    }

    public UserSession getUserSession() {
        if (userSession == null || userSession.isValid()) {
            synchronized (this) {
                userSession = realm.where(UserSession.class).findFirst();
            }
        }
        return userSession;
    }

    public void removeCurrentSession() {
        if (getUserSession() != null) {
            realm.beginTransaction();
            userSession.deleteFromRealm();
            realm.commitTransaction();
            userSession = null;
        }
    }
}
