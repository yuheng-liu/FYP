package com.example.viapatron2.app.managers;

import android.util.Log;
import com.example.viapatron2.app.constants.APIEndpoints;
import com.example.viapatron2.core.models.UserContext;
import com.example.viapatron2.core.models.UserTripRequestSession;
import com.github.nkzawa.emitter.Emitter;
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
    private Socket socket;
    private Gson gson;

    private DataManager mDataManager;

    // constructor
    public SocketManager(DataManager dataManager) {
        this.mDataManager = dataManager;
        gson = new Gson();
    }

    private Socket getSocket() {
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
            socket = IO.socket(APIEndpoints.API_BASE_URL);
            socket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        prepareListeners();
    }

    private void prepareListeners() {
        // session_connected
        socket.on("session_connected", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                // read ride request data
                JSONObject data = (JSONObject) args[0];
                UserContext userContext = null;
                try {
                    userContext = UserContext.valueOf(data.getString("context"));
                    switch (userContext) {
//                        case ON_TRIP:
//                            Trip trip = gson.fromJson(data.getString("context_data"), Trip.class);
//                            mDataManager.setCurrentTrip(trip);
//                            break;
//                        case ON_BID:
//                            RideRequest rideRequest = gson.fromJson(data.getString("context_data"), RideRequest.class);
//                            mDataManager.setCurrentRideRequest(rideRequest);
//                            break;
                        default:
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // run listener
//                if (userContext != null) {
//                    mDataManager.setSessionContext(userContext);
//                    sessionConnectedObservable.accept(userContext);
//                }
            }
        });

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

            // test
            getSocket().emit("join", "test send trip request");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
