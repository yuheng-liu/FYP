package com.example.viaporter.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.viaporter.MainActivity;
import com.example.viaporter.adapters.BroadcastAdapter;
import com.example.viaporter.R;
import com.example.viaporter.managers.DataManager;
import com.example.viaporter.managers.SocketManager;
import com.github.nkzawa.emitter.Emitter;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class TripRequestFragment extends Fragment {
    private static final String TAG = "viaPatron.TRFragment";

    private MainActivity mActivity;
    private NavController navController;
    private BroadcastAdapter mBroadcastAdapter;
    private SocketManager socketManager;
    private DataManager dataManager;

    private RecyclerView mBidderView;

    public TripRequestFragment() {
        // Empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");

        socketManager = SocketManager.getSharedInstance();
        dataManager = DataManager.getSharedInstance();

        return inflater.inflate(R.layout.trip_request_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mActivity = (MainActivity) requireActivity();
        setUpViews();
        createAdapter("tempString");
        setupSocket();
        setupSocketListeners();
    }

    private void setUpViews() {
        navController = Navigation.findNavController(mActivity, R.id.my_nav_host_fragment);

        mBidderView = mActivity.findViewById(R.id.broadcast_request_table);
        mBidderView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void setupSocket() {
        socketManager.connectSocket();
        socketManager.emitString("join", "viaPorter");
    }

    private void setupSocketListeners() {
        socketManager.getSocket().on("newMessage", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String message = args[0].toString();
                        createAdapter(message);
                    }
                });
            }
        });
    }

    private void createAdapter(String str) {
        Log.d(TAG, "createAdapter");

        mBroadcastAdapter = new BroadcastAdapter(str);
        mBidderView.setAdapter(mBroadcastAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        socketManager.disconnectSocket();
    }
}
