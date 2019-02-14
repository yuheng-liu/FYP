package com.example.viapatron2.app.managers;

import android.util.Log;
import com.example.viapatron2.app.constants.AppConstants;
import com.example.viapatron2.core.models.UserTripRequestSession;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

/**
 * Created by Lim Zhiming on 23/1/19.
 */

public class SocketManager {

    private static final String TAG = "viaPatron.SocketManager";

    // private properties
    private static Socket socket;
    private Gson gson;

    private DataManager mDataManager;

    // constructor
    public SocketManager(DataManager dataManager) {
        this.mDataManager = dataManager;
        gson = new Gson();
    }

    public SocketManager() {
        gson = new Gson();
        socket = getSocket();
    }

    public Socket getSocket() {
        if (socket == null) {
            synchronized (this) {
                openConnection();
            }
        }
        return socket;
    }

    // public methods
    public void openConnection() {

        Log.d(TAG, "openConnection");

        // disconnect old socket
        if (socket != null) {
            socket.close();
        }

        try {
            socket = IO.socket(AppConstants.LOCAL_HOST_URL);
            socket.connect();
            socket.emit("join", "viaPatron");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

//        prepareListeners();
    }

    public void closeConnection() {

        if (socket != null) {
            socket.disconnect();
            socket.close();
        }
    }

    private void prepareListeners() {

        // ride request created
//        socket.on("ride_request_created", new Emitter.Listener() {
//            @Override
//            public void call(Object... args) {
//                // read ride request data
//                JSONObject data = (JSONObject) args[0];
//                Log.d(TAG, data.toString());
//                RideRequest rideRequest = gson.fromJson(data.toString(), RideRequest.class);
//                // set data
//                mDataManager.setCurrentRideRequest(rideRequest);
//                // run listener
//                rideRequestCreatedObservable.accept(rideRequest);
//            }
//        });
    }

    public void sendRideRequest(UserTripRequestSession tripRequestInfo) {
        try {
            JSONObject data = new JSONObject(gson.toJson(tripRequestInfo));
            getSocket().emit("trip_request", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
