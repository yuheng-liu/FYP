package com.example.viapatron2.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.example.viapatron2.R;
import com.example.viapatron2.core.models.MyViewModel;
import com.example.viapatron2.core.models.UserTripRequestSession;

public class HomeFragment extends Fragment {

    private static final String TAG = "viaPatron.HomeFragment";

    private AppCompatSpinner stationSpinner;
    private Button nextButton;
    private NavController navController;
    private MyViewModel model;
    private UserTripRequestSession userTripRequestSession;
    private ArrayAdapter<CharSequence> adapter;

    public HomeFragment() {
        // Empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView");

        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    // This event is triggered soon after onCreateView().
    // onViewCreated() is only called if the view returned from onCreateView() is non-null.
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d(TAG, "onViewCreated");

        stationSpinner = (AppCompatSpinner) getActivity().findViewById(R.id.stations_spinner);
        nextButton = (Button) getActivity().findViewById(R.id.stations_spinner_next);

        setUpViewModel();
        setUpClickable();

        navController = Navigation.findNavController(getActivity(), R.id.my_nav_host_fragment);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.d(TAG, "onActivityCreated");

    }

    private void setUpViewModel() {

        Log.d(TAG, "setUpViewModel");

        // Re-created activities receive the same MyViewModel instance created by the first activity.
        model = ViewModelProviders.of(getActivity()).get(MyViewModel.class);

        if (!model.getRequestSession().hasObservers()) {
            Log.d(TAG, "no observers yet");

            model.getRequestSession().observe(this, users -> {
                // update UI
            });
        }
    }

    private void setUpClickable() {

        Log.d(TAG, "setUpClickable");

        if (stationSpinner != null) {

            // Create an ArrayAdapter using the string array and a default spinner layout
            adapter = ArrayAdapter.createFromResource(getActivity(),
                    R.array.stations_array, android.R.layout.simple_spinner_item);

            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // Apply the adapter to the spinner
            stationSpinner.setAdapter(adapter);

            // Apply an observer to the item selected
            stationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    userTripRequestSession = new UserTripRequestSession();
                    userTripRequestSession.setStation(parent.getSelectedItem().toString());
                    model.setRequestSession(userTripRequestSession);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    Log.d(TAG, "onNothingSelected");

                }
            });
        }

        if (nextButton != null) {

            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick, navigating to tripRequestFragment");

                    navController.navigate(R.id.navigation_trip_request);
                }
            });
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d(TAG, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();

        Log.d(TAG, "onPause");

        //todo: find a way to save the current state as there is a problem of creating a new HomeFragment everytime reclicking into trip tab in bottom navigation
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestroy");
    }


}
