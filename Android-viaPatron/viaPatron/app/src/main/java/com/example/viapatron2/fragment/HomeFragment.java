package com.example.viapatron2.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
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
import android.widget.Toast;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.example.viapatron2.R;
import com.example.viapatron2.activity.MainActivity;
import com.example.viapatron2.core.models.MyViewModel;
import com.example.viapatron2.core.models.UserTripRequestSession;
import com.google.android.gms.location.*;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class HomeFragment extends Fragment
        implements OnMapReadyCallback,
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener {

    private static final String TAG = "viaPatron.HomeFragment";

    // App
    private MainActivity mActivity;

    // Views
    private Button nextButton;
    private AppCompatSpinner stationSpinner;
    private ArrayAdapter<CharSequence> adapter;

    // Controllers
    private MyViewModel model;
    private NavController navController;
    private UserTripRequestSession userTripRequestSession;

    // Location Services variables
    private SupportMapFragment mapFragment;
    private GoogleMap mGoogleMap;
    private LocationRequest mLocationRequest;
    private Location mLastLocation;
    private Marker mCurrLocationMarker;
    private FusedLocationProviderClient mFusedLocationClient;


    public HomeFragment() {
        // Empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    // This event is triggered soon after onCreateView().
    // onViewCreated() is only called if the view returned from onCreateView() is non-null.
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d(TAG, "onViewCreated");

        mActivity = (MainActivity) requireActivity();

        stationSpinner = (AppCompatSpinner) mActivity.findViewById(R.id.stations_spinner);
        nextButton = (Button) mActivity.findViewById(R.id.stations_spinner_next);
        mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.google_map);

        setUpViewModel();
        setUpClickable();

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mActivity);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        navController = Navigation.findNavController(getActivity(), R.id.my_nav_host_fragment);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void setUpViewModel() {

//        Log.d(TAG, "setUpViewModel");

        // Re-created activities receive the same MyViewModel instance created by the first activity.
        model = ViewModelProviders.of(mActivity).get(MyViewModel.class);

        if (!model.getRequestSession().hasObservers()) {
            Log.d(TAG, "no observers yet");

            model.getRequestSession().observe(this, users -> {
                // update UI
            });
        }
    }

    private void setUpClickable() {

        if (stationSpinner != null) {

            // Create an ArrayAdapter using the string array and a default spinner layout
            adapter = ArrayAdapter.createFromResource(mActivity,
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
    public void onMapReady(GoogleMap map) {

        Log.d(TAG, "onMapReady");
        mGoogleMap = map;

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(120000); // two minute interval
        mLocationRequest.setFastestInterval(120000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());

        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.setOnMyLocationButtonClickListener(this);
        mGoogleMap.setOnMyLocationClickListener(this);
    }

    LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            List<Location> locationList = locationResult.getLocations();
            if (locationList.size() > 0) {
                //The last location in the list is the newest
                Location location = locationList.get(locationList.size() - 1);
                Log.i("MapsActivity", "Location: " + location.getLatitude() + " " + location.getLongitude());
                mLastLocation = location;
                if (mCurrLocationMarker != null) {
                    mCurrLocationMarker.remove();
                }

                //Place current location marker
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title("Current Position");
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);

                //move map camera
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11));
            }
        }
    };

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(mActivity, "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(mActivity, "Finding your location..", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
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
