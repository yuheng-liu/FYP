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
import com.example.viapatron2.activity.MainActivity;
import com.example.viapatron2.core.models.MyViewModel;
import com.example.viapatron2.core.models.UserTripRequestSession;

public class TripRequestConfirmFragment extends Fragment {

    private static final String TAG = "viaPatron.TRCFragment";

    private NavController navController;
    private MyViewModel model;
    private MainActivity mActivity;

    private Button confirmButton;
    private TextView stationTitle;
    private TextView dateTitle;
    private TextView smallStationTitleLeft;
    private TextView smallStationTitleRight;
    private TextView toTitle;
    private TextView fromTitle;
    private TextView luggageTitle;
    private TextView luggageWeightTitle;
    private TextView priceTitle;

    private UserTripRequestSession userTripRequestSession;

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

        mActivity = (MainActivity) requireActivity();

        navController = Navigation.findNavController(mActivity, R.id.my_nav_host_fragment);
        confirmButton = mActivity.findViewById(R.id.trip_request_confirm_button);
        stationTitle = mActivity.findViewById(R.id.trip_request_confirm_station_title);
        dateTitle = mActivity.findViewById(R.id.trip_request_confirm_date_title);
        smallStationTitleLeft = mActivity.findViewById(R.id.tv_trip_confirm_station_left_side);
        smallStationTitleRight = mActivity.findViewById(R.id.tv_trip_confirm_station_right_side);
        toTitle = mActivity.findViewById(R.id.tv_trip_confirm_to);
        fromTitle = mActivity.findViewById(R.id.tv_trip_confirm_from);
        luggageTitle = mActivity.findViewById(R.id.tv_trip_confirm_luggage);
        luggageWeightTitle = mActivity.findViewById(R.id.tv_trip_confirm_luggage_weight);
        priceTitle = mActivity.findViewById(R.id.trip_confirm_price);

        if (confirmButton != null) {
            confirmButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        mActivity.getmSocketManager().sendRideRequest(userTripRequestSession);
                        navController.navigate(R.id.navigation_trip_bidding);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        setUpViewModel();
    }

    private void setUpViewModel() {

        Log.d(TAG, "setUpViewModel");

        // Re-created activities receive the same MyViewModel instance created by the first activity.
        model = ViewModelProviders.of(mActivity).get(MyViewModel.class);
        userTripRequestSession = model.getUserTripRequestSession();

        String modelStationName = userTripRequestSession.getStation();
        String modelDate = userTripRequestSession.getDate();
        String modelToLocation = userTripRequestSession.getToLocation();
        String modelFromLocation = userTripRequestSession.getFromLocation();
        int modelLuggageNo = userTripRequestSession.getNoOfLuggage();
        int modelLuggageWeight = userTripRequestSession.getTotalLuggageWeight();

        model.getRequestSession().observe(this, users -> {

            // update UI
            if (stationTitle != null) {
                stationTitle.setText(modelStationName);
            }

            if (smallStationTitleLeft != null) {
                smallStationTitleLeft.setText(modelStationName);
            }

            if (smallStationTitleRight != null) {
                smallStationTitleRight.setText(modelStationName);
            }

            if (dateTitle != null) {
                dateTitle.setText(modelDate);
            }

            if (toTitle != null) {
                toTitle.setText(modelToLocation);
            }

            if (fromTitle != null) {
                fromTitle.setText(modelFromLocation);
            }

            if (luggageTitle != null) {
                String luggageNum = String.valueOf(modelLuggageNo) + " " + getResources().getString(R.string.trip_request_confirm_luggage_text);
                luggageTitle.setText(luggageNum);
            }

            if (luggageWeightTitle != null) {
                String luggageWeightText = String.valueOf(modelLuggageWeight) + " " + getResources().getString(R.string.trip_request_confirm_luggage_KG_text);
                luggageWeightTitle.setText(String.valueOf(luggageWeightText));
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
