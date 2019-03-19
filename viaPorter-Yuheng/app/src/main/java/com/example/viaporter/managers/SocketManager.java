package com.example.viaporter.managers;

import android.util.Log;

import com.example.viaporter.constants.AppConstants;
import com.example.viaporter.models.LocationUpdate;
import com.example.viaporter.models.PatronTripRequest;

import com.example.viaporter.models.PatronTripSuccess;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import com.jakewharton.rxrelay2.PublishRelay;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class SocketManager {
    private static final String TAG = "SocketManager";

    private Socket socket;
    private Gson gson;
    private DataManager dataManager;

    // Listen events
    private PublishRelay<PatronTripRequest> patronTripRequestRelay;
    private PublishRelay<PatronTripSuccess> patronTripSuccessRelay;
    private PublishRelay<LocationUpdate> patronLocationUpdateRelay;

    /*                                      *
     * Use of Bill Pugh Singleton structure *
     *                                      */
    // Private constructor //
    private SocketManager() {
        gson = new Gson();
        dataManager = DataManager.getSharedInstance();
        createPublishRelayObservers();
    }
    // Static inner class are not loaded until they are referenced
    private static class socketManagerholder {
        private static SocketManager manager = new SocketManager();
    }
    // Global excess point
    public static SocketManager getSharedInstance() {
        return socketManagerholder.manager;
    }
    /* ************************************ */

    private void createPublishRelayObservers() {
        patronTripRequestRelay = PublishRelay.create();
        patronTripSuccessRelay = PublishRelay.create();
        patronLocationUpdateRelay = PublishRelay.create();
    }

    public void connectSocket() {
        try {
            socket = IO.socket(AppConstants.LOCAL_HOST_URL);
            socket.connect();
            prepareListeners();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void disconnectSocket() {
        socket.disconnect();
    }

    public void reconnectSocket() {
        // disconnect old socket
        if (socket != null) {
            socket.disconnect();
            socket.connect();
        }
    }

    public void emitString(String event, String msg) {
        socket.emit(event, msg);
    }

    public void emitJSON(String event, JSONObject msg) { socket.emit(event, msg); }

    public void emitStateChanged(String state, JSONObject msg) {
        try {
            JSONObject data = new JSONObject()
                .put("STATE", state)
                .put("MSG", msg);
            socket.emit("porter_state_change", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // listening methods
    public Disposable addOnPatronTripRequest(Consumer<PatronTripRequest> onSuccess){
        return patronTripRequestRelay.subscribe(onSuccess);
    }
    public Disposable addOnPatronTripSuccess(Consumer<PatronTripSuccess> onSuccess){
        return patronTripSuccessRelay.subscribe(onSuccess);
    }
    public Disposable addOnPatronLocationUpdate(Consumer<LocationUpdate> onSuccess){
        return patronLocationUpdateRelay.subscribe(onSuccess);
    }

    // socket listeners
    private void prepareListeners() {
        socket.on("patron_location_update", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                LocationUpdate newLocation = gson.fromJson(data.toString(), LocationUpdate.class);
                Log.d("testing", "new location update received");
                patronLocationUpdateRelay.accept(newLocation);
            }
        });

        socket.on("patron_state_change", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                String strMsg;
                JSONObject msg;
                JSONObject data = (JSONObject) args[0];
                try {
                    switch (data.getString("STATE")) {
                        case "patron_trip_request":
                            msg = data.getJSONObject("MSG");
                            PatronTripRequest newRequest = gson.fromJson(msg.toString(), PatronTripRequest.class);
                            patronTripRequestRelay.accept(newRequest);
                            break;

                        case "patron_bid_success":
                            msg = data.getJSONObject("MSG");
                            PatronTripSuccess newTrip = gson.fromJson(msg.toString(), PatronTripSuccess.class);
                            dataManager.setCurrentTrip(newTrip);
                            patronTripSuccessRelay.accept(newTrip);
                            break;

                        case "patron_stop_bidding":
                            strMsg = data.getString("MSG");

                        default:
                            Log.d(TAG,"no state change detected");
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}
