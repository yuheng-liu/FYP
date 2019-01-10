package com.example.viapatron2.activity;


import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.example.viapatron2.app.managers.APIManager;
import com.example.viapatron2.app.managers.DataManager;
import io.reactivex.disposables.Disposable;

import com.example.viapatron2.service.viaPatronWorkerService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lim Zhiming on 7/1/19.
 */

public class AppActivity extends AppCompatActivity {

    private List<Disposable> disposables;
    private viaPatronWorkerService mService;
    private boolean mServiceBounded, mServiceConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // init data
        disposables = new ArrayList<>();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mServiceBounded = false;
        mServiceConnected = false;
    }

    public DataManager getDataManager() {
        return mService.getDataManager();
    }

    public APIManager getApiManager() {
        return mService.getApiManager();
    }

//    public SocketManager getSocketManager() {
//        return mService.getSocketManager();
//    }
}
