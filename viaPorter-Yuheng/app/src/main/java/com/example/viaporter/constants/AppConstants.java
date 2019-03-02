package com.example.viaporter.constants;

public class AppConstants {

    // Used for locahost testing of server, changes everytime in new network
    public static final String LOCAL_HOST_URL = "http://172.17.60.198:3000"; // localhost
    public static final int PERMISSION_FINE_LOCATION = 111;
    public static final int PERMISSION_MEDIA_ACCESS = 112;

    // Map paramters
    public static final int MAP_CAMERA_ZOOM = 18;
    public static final int MAP_LOCATION_REQUEST_INTERVAL = 4 * 1000;

    // Arbitrary places for testing
    public static final double PLACE_NUS_UTOWN_LAT = 1.3054857921840286;
    public static final double PLACE_NUS_UTOWN_LNG = 103.77305947244167;

    public static final double PLACE_NUS_FOS_LAT = 1.296701140936438;
    public static final double PLACE_NUS_FOS_LNG = 103.78054015338421;
}
