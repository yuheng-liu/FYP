package com.example.viapatron2.core.models;

import io.realm.RealmObject;

/**
 * Created by Lim Zhiming on 30/1/19.
 * Adapted from Auto.
 */
public class UserSession extends RealmObject {
    private String sessionId;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
