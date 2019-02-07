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

import com.example.viaporter.BroadcastAdapter;
import com.example.viaporter.R;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class TripRequestFragment extends Fragment {
    private static final String TAG = "viaPatron.TRFragment";

    private NavController navController;
    private BroadcastAdapter mBroadcastAdapter;

    public TripRequestFragment() {
        // Empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");

        return inflater.inflate(R.layout.trip_request_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpViews();
        createAdapter();
    }

    private void setUpViews() {
        navController = Navigation.findNavController(getActivity(), R.id.my_nav_host_fragment);
    }

    private void createAdapter() {

        Log.d(TAG, "createAdapter");

        RecyclerView mBidderView = getActivity().findViewById(R.id.broadcast_request_table);
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mBidderView.setLayoutManager(linearLayoutManager);

        String tempString = "Max";
        mBroadcastAdapter = new BroadcastAdapter(tempString);
        mBidderView.setAdapter(mBroadcastAdapter);
    }
}
