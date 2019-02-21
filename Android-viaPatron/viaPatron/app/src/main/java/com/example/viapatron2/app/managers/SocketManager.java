package com.example.viapatron2.app.managers;

import android.util.Log;
import com.example.viapatron2.app.constants.AppConstants;
import com.example.viapatron2.core.models.PorterBidRequest;
import com.example.viapatron2.core.models.UserTripRequestSession;
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

    private void prepareListeners() {
        // bid request created
        socket.on("porter_bid_request", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                PorterBidRequest newRequest = gson.fromJson(data.toString(), PorterBidRequest.class);
                porterBidRequestPublishRelay.accept(newRequest);
                Log.d(TAG, "bid request received");
                Log.d(TAG, newRequest.getPorterName());
                Log.d(TAG, newRequest.getBidAmount());
            }
        });


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
