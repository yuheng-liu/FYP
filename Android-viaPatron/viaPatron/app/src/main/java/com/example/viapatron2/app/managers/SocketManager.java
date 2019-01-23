package com.example.viapatron2.app.managers;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;

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
        // disconnect old socket
        if (socket != null) {
            socket.close();
        }
        // create new socket connection
//        String sessionId = mDataManager.getUserSession().getSessionId();

        IO.Options options = new IO.Options();

//        options.query = "session_id=" + sessionId;
//        Manager manager = new Manager(URI.create(APIEndpoints.API_BASE_URL), options);
//        socket = manager.socket(APIEndpoints.RIDER_APP_SOCKET);

        socket.connect();
        prepareListeners();
    }

    private void prepareListeners() {
        // session_connected
//        socket.on("session_connected", new Emitter.Listener() {
//            @Override
//            public void call(Object... args) {
//                // read ride request data
//                JSONObject data = (JSONObject) args[0];
//                UserContext userContext = null;
//                try {
//                    userContext = UserContext.valueOf(data.getString("context"));
//                    switch (userContext) {
//                        case ON_TRIP:
//                            Trip trip = gson.fromJson(data.getString("context_data"), Trip.class);
//                            mDataManager.setCurrentTrip(trip);
//                            break;
//                        case ON_BID:
//                            RideRequest rideRequest = gson.fromJson(data.getString("context_data"), RideRequest.class);
//                            mDataManager.setCurrentRideRequest(rideRequest);
//                            break;
//                        default:
//                            break;
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                // run listener
//                if (userContext != null) {
//                    mDataManager.setSessionContext(userContext);
//                    sessionConnectedObservable.accept(userContext);
//                }
//            }
//        });

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
}
