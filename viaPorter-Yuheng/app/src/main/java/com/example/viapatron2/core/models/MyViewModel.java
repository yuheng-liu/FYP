package com.example.viapatron2.core.models;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

public class MyViewModel extends ViewModel {

    private MutableLiveData<List<String>> userInfo;

    public LiveData<List<String>> getUserInfo() {

        if (userInfo == null) {
            userInfo = new MutableLiveData<List<String>>();
        }

        return userInfo;
    }

    private void loadUserInfo() {
        // Todo: Do an asynchronous operation to fetch users.
    }
}
