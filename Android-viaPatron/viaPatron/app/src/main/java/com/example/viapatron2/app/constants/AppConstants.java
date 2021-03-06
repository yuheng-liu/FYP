package com.example.viapatron2.app.constants;

/**
 * Created by Lim Zhiming on 30/1/19.
 * Configs for debug.
 */

public final class AppConstants {
    /* Base URL */
//    public static final String LOCAL_HOST_URL = "https://server.autoapp.io/"; // aws prod
    public static final String LOCAL_HOST_URL = "http://172.17.63.125:5000"; // localhost

    public static final int PERMISSION_FINE_LOCATION = 111;
    public static final int PERMISSION_MEDIA_ACCESS = 112;

    /* Map parameter */
    public static final int MAP_CAMERA_ZOOM = 18;
    public static final int MAP_LOCATION_REQUEST_INTERVAL = 1000;
    public static final int MAP_DISTANCE_BETWEEN_PROXIMITY = 30;
    public static final int MAP_PLACE_SEARCH_RADIUS = 100 * 1000; // search place 100 km around target

    /* APP API KEY */
    public static final String API_KEY_STRING = "AIzaSyBOhZ4BnlhkaPJdf8v6SvJRbdRnA8Vwofo";

    /* Login parameter */
    public static final String CLIENT_PLATFORM = "android";

    /* Rider socket */
    public static final String RIDER_APP_SOCKET = "/";
}

