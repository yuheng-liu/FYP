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
import com.example.viapatron2.TextValidator;
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
    private EditText luggageWeightField;

    // For model tracking
    private String fromFieldString;
    private String toFieldString;
    private int luggageFieldInt;
    private int luggageWeightFieldInt;
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
        setUpListeners();
        setUpViewModel();
    }

    private void setUpViews() {

        navController = Navigation.findNavController(mActivity, R.id.my_nav_host_fragment);
        nextButton = mActivity.findViewById(R.id.trip_request_next_button);
        stationTitle = mActivity.findViewById(R.id.trip_request_station_title);
        dateTitle = mActivity.findViewById(R.id.trip_request_date_title);
        fromField = mActivity.findViewById(R.id.from_request_field);
        toField = mActivity.findViewById(R.id.to_request_field);
        luggageField = mActivity.findViewById(R.id.luggages_request_field);
        luggageWeightField = mActivity.findViewById(R.id.luggage_weight_field);

        String pattern = "dd MMM";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        dateString = simpleDateFormat.format(new Date());

        if (dateTitle != null) {
            dateTitle.setText(dateString);
        }
    }

    private void setUpListeners() {

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

                    /* Verify before confirmation that all inputs are entered accordingly. */
                    if (fromField.getText().toString().length() <= 0) {
                        fromField.setError("Enter Start Location");
                        fromField.requestFocus();
                        return;
                    } else {
                        // length() > 0
                        fromFieldString = fromField.getText().toString();
                    }

                    if (toField.getText().toString().length() <= 0) {
                        toField.setError("Enter End Location");
                        toField.requestFocus();
                        return;
                    } else {
                        // length() > 0
                        toFieldString = toField.getText().toString();
                    }

                    if (luggageField.getText().toString().length() <= 0) {
                        luggageField.setError("Enter Number of Luggage");
                        luggageField.requestFocus();
                        return;
                    } else {
                        // length() > 0
                        String tempLuggageFieldString = luggageField.getText().toString();
                        luggageFieldInt = Integer.parseInt(tempLuggageFieldString);
                    }

                    // Save all data if inputs are valid
                    userTripRequestSession.setDate(dateString);
                    userTripRequestSession.setFromLocation(fromFieldString);
                    userTripRequestSession.setToLocation(toFieldString);
                    userTripRequestSession.setNoOfLuggage(luggageFieldInt);
                    userTripRequestSession.setTotalLuggageWeight(luggageWeightFieldInt);

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

            fromField.addTextChangedListener(new TextValidator(fromField) {
                @Override
                public void validate(TextView textView, String text) {
                    /* Validation code here */
                    if (fromField.getText().toString().length() <= 0) {
                        fromField.setError("Enter Start Location");
                    } else {
                        fromField.setError(null);
                    }
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

            toField.addTextChangedListener(new TextValidator(toField) {
                @Override
                public void validate(TextView textView, String text) {
                    /* Validation code here */
                    if (toField.getText().toString().length() <= 0) {
                        toField.setError("Enter End Location");
                    } else {
                        toField.setError(null);
                    }
                }
            });
        }

        if (luggageField != null) {
            luggageField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_NEXT) {
                        try {
                            String tempLuggageFieldString = luggageField.getText().toString();
                            luggageFieldInt = Integer.parseInt(tempLuggageFieldString);
                        } catch (NumberFormatException e) {
                            luggageField.setError("Enter a Valid Number");
                        }
                    }
                    return false;
                }
            });

            luggageField.addTextChangedListener(new TextValidator(luggageField) {
                @Override
                public void validate(TextView textView, String text) {
                    /* Validation code here */
                    if (luggageField.getText().toString().length() <= 0) {
                        luggageField.setError("Enter Number of Luggage");
                    } else {
                        luggageField.setError(null);
                    }
                }
            });
        }

        if (luggageWeightField != null) {
            luggageWeightField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        try {
                            String tempLuggageWeightFieldString = luggageWeightField.getText().toString();
                            luggageWeightFieldInt = Integer.parseInt(tempLuggageWeightFieldString);
                        } catch (NumberFormatException e) {
                            luggageWeightField.setError("Enter a Valid Number");
                        }
                    }
                    return false;
                }
            });

            luggageWeightField.addTextChangedListener(new TextValidator(luggageWeightField) {
                @Override
                public void validate(TextView textView, String text) {
                    /* Validation code here */
                    if (luggageWeightField.getText().toString().length() <= 0) {
                        luggageWeightField.setError("Enter Total Luggage Weight");
                    } else {
                        luggageWeightField.setError(null);
                    }
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
                String modelStationName = userTripRequestSession.getStation();

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
