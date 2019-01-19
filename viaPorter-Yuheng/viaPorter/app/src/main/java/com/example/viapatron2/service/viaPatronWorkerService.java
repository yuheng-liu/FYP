package com.example.viapatron2.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import com.example.viapatron2.app.managers.APIManager;
import com.example.viapatron2.app.managers.DataManager;
import io.reactivex.disposables.Disposable;

import java.util.List;

/**
 * Created by Lim Zhiming on 7/1/19.
 */

public class viaPatronWorkerService extends Service {

    private static final String TAG = "AutoWorkerService";

    // Binder and managers
    private final IBinder mBinder = new LocalBinder();

    // todo: create classes for below managers
    private DataManager dataManager;
    private APIManager apiManager;
    //private SocketManager socketManager;

    // disposables
    private List<Disposable> disposables;

    // getters
    public DataManager getDataManager() {
        return dataManager;
    }
    public APIManager getApiManager() {
        return apiManager;
    }
//    public SocketManager getSocketManager() {
//        return socketManager;
//    }

    // Binder class
    public class LocalBinder extends Binder {
        public viaPatronWorkerService getService() {
            return viaPatronWorkerService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
