package com.example.viapatron2.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import com.example.viapatron2.app.managers.DataManager;
import com.example.viapatron2.app.managers.SocketManager;
import io.reactivex.disposables.Disposable;

import java.util.List;

/**
 * Created by Lim Zhiming on 7/1/19.
 */

public class ViaPatronWorkerService extends Service {

    private static final String TAG = "viaP.ViaWorkerService";

    // Binder and managers
    private final IBinder mBinder = new LocalBinder();

    // todo: create classes for below managers
    private DataManager dataManager;
    private SocketManager socketManager;

    // disposables
    private List<Disposable> disposables;

    @Override
    public void onCreate() {
        super.onCreate();

        // This method is called everytime app is resumed from background
        Log.d(TAG, "onCreate, Service created");

        // init manager classes
        // todo: edit instances of managers and importData in DataManager
        dataManager = new DataManager();
        dataManager.importData();
        socketManager = new SocketManager(dataManager);

//        disposables = new ArrayList<>();
        // add listener
//        disposables.add(apiManager.addOnSessionCreated(new Consumer<String>() {
//            @Override
//            public void accept(String sessionId) {
//                socketManager.openConnection();
//            }
//        }));
    }

    // getters
    public DataManager getDataManager() {
        return dataManager;
    }

    public SocketManager getSocketManager() {
        return socketManager;
    }

    // Binder class
    public class LocalBinder extends Binder {
        public ViaPatronWorkerService getService() {
            return ViaPatronWorkerService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
