package com.example.viapatron2.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.example.viapatron2.R;
import com.example.viapatron2.core.models.MyViewModel;

public class TripRequestConfirmFragment extends Fragment {

    private static final String TAG = "viaPatron.TRCFragment";

    private NavController navController;
    private MyViewModel model;

    private Button confirmButton;
    private TextView stationTitle;
    private TextView dateTitle;

    public TripRequestConfirmFragment() {
        // Empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");

        return inflater.inflate(R.layout.trip_request_confirm_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(getActivity(), R.id.my_nav_host_fragment);
        confirmButton = getActivity().findViewById(R.id.trip_request_confirm_button);
        stationTitle = getActivity().findViewById(R.id.trip_request_confirm_station_title);
        dateTitle = getActivity().findViewById(R.id.trip_request_confirm_date_title);

        if (confirmButton != null) {
            confirmButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "confirmButton onClick");

                    // todo: save data and navigate to bidding screen
                    navController.navigate(R.id.navigation_trip_bidding);
                }
            });
        }

        setUpViewModel();
    }

    private void setUpViewModel() {

        Log.d(TAG, "setUpViewModel");

        // Re-created activities receive the same MyViewModel instance created by the first activity.
        model = ViewModelProviders.of(getActivity()).get(MyViewModel.class);
        model.getRequestSession().observe(this, users -> {

            // update UI
            if (stationTitle != null) {
                String stationName = model.getRequestSession().getValue().getStation();

                if (stationName != null) {
                    stationTitle.setText(model.getRequestSession().getValue().getStation());
                }
            }

            if (dateTitle != null) {
                String date = model.getRequestSession().getValue().getDate();
                dateTitle.setText(date);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
