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
import com.example.viaporter.models.PatronTripRequest;

import java.util.List;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import io.reactivex.functions.Consumer;

public class TripRequestFragment extends Fragment {
    private static final String TAG = "viaPatron.TRFragment";

    private MainActivity mActivity;
    private NavController navController;
    private SocketManager socketManager;
    private DataManager dataManager;

    private RecyclerView mBidderView;
    private BroadcastAdapter mBroadcastAdapter;

    public TripRequestFragment() {} // Empty constructor

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
        setupSocket();
        setupSocketListeners();
    }

    private void setUpViews() {
        navController = Navigation.findNavController(mActivity, R.id.my_nav_host_fragment);

        mBidderView = mActivity.findViewById(R.id.broadcast_request_table);
        mBidderView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBroadcastAdapter = new BroadcastAdapter();
        mBidderView.setAdapter(mBroadcastAdapter);
    }

    private void setupSocket() {
        socketManager.connectSocket();
        socketManager.emitString("join", "viaPorter");
    }

    private void setupSocketListeners() {
        mActivity.addDisposable(socketManager.addOnPatronTripRequest(new Consumer<PatronTripRequest>() {
            @Override
            public void accept(final PatronTripRequest tripRequest) {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mBroadcastAdapter.addToDataSet(tripRequest);
                    }
                });

            }
        }));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        socketManager.disconnectSocket();
    }
}
