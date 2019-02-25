package com.example.viaporter.managers;

import com.example.viaporter.constants.AppConstants;
import com.example.viaporter.models.PatronTripRequest;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import com.jakewharton.rxrelay2.PublishRelay;

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
    /*                                      */

    private void createPublishRelayObservers() {
        patronTripRequestRelay = PublishRelay.create();
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

    // listening methods
    public Disposable addOnPatronTripRequest(Consumer<PatronTripRequest> onSuccess){
        return patronTripRequestRelay.subscribe(onSuccess);
    }

    // socket listeners
    private void prepareListeners() {
        socket.on("patron_trip_request", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                JSONObject data = (JSONObject) args[0];
                PatronTripRequest newRequest = gson.fromJson(data.toString(), PatronTripRequest.class);
                patronTripRequestRelay.accept(newRequest);
            }
        });
    }
}
