package com.example.viapatron2.core.models;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class MyViewModel extends ViewModel {

    private MutableLiveData<UserTripRequestSession> requestSession;

    public LiveData<UserTripRequestSession> getRequestSession() {

        if (requestSession == null) {
            requestSession = new MutableLiveData<UserTripRequestSession>();
        }
        return requestSession;
    }

    public void setRequestSession(UserTripRequestSession updatedRequestSession) {

        requestSession.setValue(updatedRequestSession);
    }
}
