package com.example.viapatron2.app.constants;

/**
 * Created by Lim Zhiming on 30/1/19.
 * Adapted from Auto.
 * Configs for debug.
 */

public final class AppConstants {
    /* Base URL */
//    public static final String LOCAL_HOST_URL = "https://server.autoapp.io/"; // aws prod
//    public static final String LOCAL_HOST_URL = "http://54.169.55.80/"; // aws dev
//    public static final String LOCAL_HOST_URL = "http://139.59.244.233:5000"; // digital ocean
//    public static final String LOCAL_HOST_URL = "http://10.0.2.2:5000"; // localhost
    
    public static final String LOCAL_HOST_URL = "http://172.17.63.200:3000"; // localhost

    public static final int PERMISSION_FINE_LOCATION = 111;

    /* Map parameter */
    public static final int MAP_CAMERA_ZOOM = 17;
    public static final int MAP_LOCATION_REQUEST_INTERVAL = 4 * 1000;
    public static final int MAP_PLACE_SEARCH_RADIUS = 100 * 1000; // search place 100 km around target

    /* Login parameter */
    public static final String CLIENT_PLATFORM = "android";

    /* Rider socket */
    public static final String RIDER_APP_SOCKET = "/";

}

