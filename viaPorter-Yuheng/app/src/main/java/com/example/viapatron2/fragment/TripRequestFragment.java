package com.example.viapatron2.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import com.example.viapatron2.R;
import com.example.viapatron2.core.models.MyViewModel;
import com.example.viapatron2.core.models.UserTripRequestSession;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TripRequestFragment extends Fragment {

    private static final String TAG = "viaPatron.TRFragment";

    private NavController navController;
    private MyViewModel model;

    private Button nextButton;
    private TextView stationTitle;
    private TextView dateTitle;
    private EditText fromField;
    private EditText toField;
    private EditText luggageField;

    private String dateString;

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
        setUpViewModel();
    }

    private void setUpViews() {

        navController = Navigation.findNavController(getActivity(), R.id.my_nav_host_fragment);
        nextButton = getActivity().findViewById(R.id.trip_request_next_button);
        stationTitle = getActivity().findViewById(R.id.trip_request_station_title);
        dateTitle = getActivity().findViewById(R.id.trip_request_date_title);
        fromField = getActivity().findViewById(R.id.from_request_field);
        toField = getActivity().findViewById(R.id.to_request_field);
        luggageField = getActivity().findViewById(R.id.luggages_request_field);

        String pattern = "dd MMM";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        dateString = simpleDateFormat.format(new Date());

        if (dateTitle != null) {
            dateTitle.setText(dateString);
        }

        if (nextButton != null) {
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // debug
                    NavDestination currentDest = navController.getCurrentDestination();
                    if (currentDest != null) {
                        Log.d(TAG, "currentDest.getId = " + currentDest.getId());
                        Log.d(TAG, "currentDest.getLabel = " + currentDest.getLabel());
                    }

                    // Todo: save data for next fragment
                    UserTripRequestSession userTripRequestSession = model.getUserRequestSession();
                    userTripRequestSession.setDate(dateString);
                    model.setRequestSession(userTripRequestSession);

                    navController.navigate(R.id.navigation_trip_request_confirm);
                }
            });
        }

        if (fromField != null) {
            fromField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_NEXT) {

                        //todo: save text input to viewmodel
                        Log.d(TAG, fromField.getText().toString());
                        return true;
                    }

                    return false;
                }
            });
        }

        if (toField != null) {
            toField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                    // todo: update input
                    return false;
                }
            });
        }

        if (luggageField != null) {
            luggageField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                    // todo: update input
                    return false;
                }
            });
        }
    }

    private void setUpViewModel() {

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
