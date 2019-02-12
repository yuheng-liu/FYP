package com.example.viapatron2.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.example.viapatron2.R;
import com.example.viapatron2.activity.MainActivity;
import com.example.viapatron2.core.models.MyViewModel;
import com.example.viapatron2.core.models.UserTripRequestSession;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TripRequestFragment extends Fragment {

    private static final String TAG = "viaPatron.TRFragment";

    private MainActivity mActivity;
    private NavController navController;
    private MyViewModel model;
    private UserTripRequestSession userTripRequestSession;

    // Views
    private Button nextButton;
    private TextView stationTitle;
    private TextView dateTitle;
    private EditText fromField;
    private EditText toField;
    private EditText luggageField;

    // For model tracking
    private String fromFieldString;
    private String toFieldString;
    private int luggageFieldInt;
    private String dateString;


    public TripRequestFragment() {
        // Empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.trip_request_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mActivity = (MainActivity) requireActivity();
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
//                    NavDestination currentDest = navController.getCurrentDestination();
//                    if (currentDest != null) {
//                        Log.d(TAG, "currentDest.getId = " + currentDest.getId());
//                        Log.d(TAG, "currentDest.getLabel = " + currentDest.getLabel());
//                    }

                    // Todo: alert user for empty field (from, to, luggages)
                    userTripRequestSession.setDate(dateString);
                    userTripRequestSession.setFromLocation(fromFieldString);
                    userTripRequestSession.setToLocation(toFieldString);
                    userTripRequestSession.setNoOfLuggages(luggageFieldInt);
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
                        fromFieldString = fromField.getText().toString();
                    }
                    return false;
                }
            });
        }

        if (toField != null) {
            toField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_NEXT) {
                        toFieldString = toField.getText().toString();
                    }
                    return false;
                }
            });
        }

        if (luggageField != null) {
            luggageField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        String tempLuggageFieldString = luggageField.getText().toString();
                        luggageFieldInt = Integer.parseInt(tempLuggageFieldString);
                    }
                    return false;
                }
            });
        }
    }

    private void setUpViewModel() {

        // Re-created activities receive the same MyViewModel instance created by the first activity.
        model = ViewModelProviders.of(mActivity).get(MyViewModel.class);

        // Extract out the current UserTripRequestSession (initialised in HomeFragment)
        userTripRequestSession = model.getUserTripRequestSession();

        model.getRequestSession().observe(this, users -> {
            // update UI
            if (stationTitle != null) {
                String modelStationName = model.getRequestSession().getValue().getStation();

                if (modelStationName != null) {
                    stationTitle.setText(modelStationName);
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
