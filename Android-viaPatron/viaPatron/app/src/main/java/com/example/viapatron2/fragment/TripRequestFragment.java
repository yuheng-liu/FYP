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
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import com.example.viapatron2.R;
import com.example.viapatron2.core.models.MyViewModel;

public class TripRequestFragment extends Fragment {

    private static final String TAG = "viaPatron.TRFragment";

    private NavController navController;
    private Button nextButton;
    private MyViewModel model;
    private TextView stationTitle;

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

        navController = Navigation.findNavController(getActivity(), R.id.my_nav_host_fragment);
        nextButton = getActivity().findViewById(R.id.trip_request_next_button);
        stationTitle = getActivity().findViewById(R.id.trip_request_station_title);

        if (nextButton != null) {
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "nextButton onClick");

                    // debug
                    NavDestination currentDest = navController.getCurrentDestination();
                    if (currentDest != null) {
                        Log.d(TAG, "currentDest.getId = " + currentDest.getId());
                        Log.d(TAG, "currentDest.getLabel = " + currentDest.getLabel());
                    }

                    // Todo: save data for next fragment
                    navController.navigate(R.id.navigation_trip_request_confirm);
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
                stationTitle.setText(model.getRequestSession().getValue().getStation());
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
