package com.example.viapatron2.app.managers;

import android.util.Log;
import com.example.viapatron2.app.constants.AppConstants;
import com.example.viapatron2.core.models.*;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import com.jakewharton.rxrelay2.PublishRelay;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
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

    // Listen events
    private PublishRelay<PorterBidRequest> porterBidRequestPublishRelay;
    private PublishRelay<PorterTripAccept> porterTripAcceptPublishRelay;
    private PublishRelay<LocationUpdate> porterLocationUpdatePublishRelay;

    // constructor
    public SocketManager(DataManager dataManager) {
        this.mDataManager = dataManager;
        gson = new Gson();
        createPublishRelayObservers();
    }

    public SocketManager() {
        gson = new Gson();
        socket = getSocket();
        createPublishRelayObservers();
    }

    public Socket getSocket() {
        if (socket == null) {
            synchronized (this) {
                openConnection();
            }
        }
        return socket;
    }

    private void createPublishRelayObservers() {
        porterBidRequestPublishRelay = PublishRelay.create();
        porterTripAcceptPublishRelay = PublishRelay.create();
        porterLocationUpdatePublishRelay = PublishRelay.create();
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
            prepareListeners();
            socket.emit("join", "viaPatron");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {

        if (socket != null) {
            socket.disconnect();
            socket.close();
        }
    }

    // public listening methods
    public Disposable addOnPorterBidRequest(Consumer<PorterBidRequest> onSuccess){
        return porterBidRequestPublishRelay.subscribe(onSuccess);
    }

    public Disposable addOnPorterTripAccept(Consumer<PorterTripAccept> onSuccess){
        return porterTripAcceptPublishRelay.subscribe(onSuccess);
    }

    public Disposable addOnPorterLocationUpdate(Consumer<LocationUpdate> onSuccess){
        return porterLocationUpdatePublishRelay.subscribe(onSuccess);
    }

    private void prepareListeners() {

        socket.on("porter_state_changed", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];

                Log.d(TAG, "bid request received");
                Log.d(TAG, "data = " + data.toString());

                try {
                    JSONObject msg = new JSONObject();

                    switch (data.getString("STATE")) {
                        case "porter_bid_request":
                            Log.d(TAG, "porter bidding");
                            msg = data.getJSONObject("MSG");

                            PorterBidRequest newRequest = gson.fromJson(msg.toString(), PorterBidRequest.class);
                            porterBidRequestPublishRelay.accept(newRequest);

                            return;

                        case "porter_accept_trip":
                            Log.d(TAG, "porter accepted trip");
                            msg = data.getJSONObject("MSG");

                            PorterTripAccept porterInfo = gson.fromJson(msg.toString(), PorterTripAccept.class);
                            porterTripAcceptPublishRelay.accept(porterInfo);

                            return;

                        case "porter_cancel_trip":
                            Log.d(TAG, "porter is cancelling trip");

                            return;

                        default:
                            Log.d(TAG, "no state detected");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        // bid request created
        socket.on("porter_bid_request", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                Log.d(TAG, "bid request received");
                Log.d(TAG, "data = " + data.toString());

                PorterBidRequest newRequest = gson.fromJson(data.toString(), PorterBidRequest.class);
                porterBidRequestPublishRelay.accept(newRequest);
//                Log.d(TAG, newRequest.getPorterName());
//                Log.d(TAG, newRequest.getBidAmount());
            }
        });

        socket.on("porter_accept_trip", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                try {
                    JSONObject data = (JSONObject) args[0];
                    Log.d(TAG, "Porter information received");
                    Log.d(TAG, "data = " + data.toString());

                    PorterTripAccept porterInfo = gson.fromJson(data.toString(), PorterTripAccept.class);
                    porterTripAcceptPublishRelay.accept(porterInfo);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        socket.on("porter_location_update", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                try {
                    JSONObject data = (JSONObject) args[0];
                    Log.d(TAG, "Porter updated location received");
                    Log.d(TAG, "data = " + data.toString());

                    LocationUpdate porterUpdatedLocation = gson.fromJson(data.toString(), LocationUpdate.class);
                    porterLocationUpdatePublishRelay.accept(porterUpdatedLocation);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void sendRideRequest(UserTripRequestSession tripRequestInfo) {
        try {
            JSONObject msg = new JSONObject(gson.toJson(tripRequestInfo));
            JSONObject data = new JSONObject();
            data.put("STATE", "patron_trip_request");
            data.put("MSG", msg);

//            getSocket().emit("trip_request", data);
            getSocket().emit("patron_state_changed", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void acceptBidder(Trip tripSessionInfo) {
        try {
            JSONObject msg = new JSONObject(gson.toJson(tripSessionInfo));
            JSONObject data = new JSONObject();
            data.put("STATE", "patron_bid_success");
            data.put("MSG", msg);

            getSocket().emit("patron_state_changed", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public boolean stopBidding(String patronBidRequestId) {
        try {
            JSONObject msg = new JSONObject(gson.toJson(patronBidRequestId));
            JSONObject data = new JSONObject();
            data.put("STATE", "patron_stop_bidding");
            data.put("MSG", msg);

            getSocket().emit("patron_state_changed", data);
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void sendLocation(LocationUpdate myUpdatedLocation) {
        try {
            JSONObject data = new JSONObject(gson.toJson(myUpdatedLocation));
            getSocket().emit("location_update_patron", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
