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
    
    public static final String LOCAL_HOST_URL = "http://172.17.203.32:3000"; // localhost

    public static final int PERMISSION_FINE_LOCATION = 111;

    /* Help URLs */
//    public static final String TC_URL = "http://autoapp.io/terms-riders/";
//    public static final String FAQ_URL = "http://autoapp.io/faq-riders/";

    /* Login parameter */
    public static final String CLIENT_PLATFORM = "android";

    /* Rider APIs */
    public static final String RIDER_CHECK_PHONE_NUMBER = "/api/rider/check-phone-number";
    public static final String RIDER_SIGN_UP = "/api/rider/signup-firebase";
    public static final String RIDER_RESET_PASSWORD = "/api/rider/reset-password-firebase";
    public static final String RIDER_SEND_VERIFICATION = "/api/rider/send-verification";
    public static final String RIDER_LOGIN = "/api/rider/login";
    public static final String RIDER_LOGIN_PHONE = "/api/rider/login-phone";
    public static final String RIDER_LOGIN_FACEBOOK = "/api/rider/login-facebook";
    public static final String RIDER_LOGIN_GOOGLE = "/api/rider/login-google";
    public static final String RIDER_FORGET_PASSWORD = "/api/rider/forget-password";
    public static final String RIDER_VALIDATE_SESSION = "/api/rider/validate-session";
    public static final String RIDER_LOGOUT = "/api/rider/logout";

    /* Rider socket */
    public static final String RIDER_APP_SOCKET = "/";

}

