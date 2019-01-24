package com.example.viaporter.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.example.viaporter.app.managers.APIManager
import com.example.viaporter.app.managers.DataManager
import io.reactivex.disposables.Disposable

/**
 * Created by Lim Zhiming on 7/1/19.
 */

class viaPatronWorkerService : Service() {

    // Binder and managers
    private val mBinder = LocalBinder()

    // todo: create classes for below managers
    // getters
    val dataManager: DataManager? = null
    val apiManager: APIManager? = null
    //private SocketManager socketManager;

    // disposables
    private val disposables: List<Disposable>? = null
    //    public SocketManager getSocketManager() {
    //        return socketManager;
    //    }

    // Binder class
    inner class LocalBinder : Binder() {
        val service: viaPatronWorkerService
            get() = this@viaPatronWorkerService
    }

    override fun onBind(intent: Intent): IBinder? {
        return mBinder
    }

    companion object {

        private val TAG = "AutoWorkerService"
    }
}
