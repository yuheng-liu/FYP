package com.example.viapatron2.core.models;



/**
 * Created by Lim Zhiming on 10/1/19.
 * Adapting from Auto
 */

// Originally extends RealmObject (in Auto)
public class UserSession {
    private String sessionId;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
