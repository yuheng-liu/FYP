package com.example.viaporter.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import com.example.viaporter.R;
import com.example.viaporter.MainActivity;
//import com.example.viapatron2.core.models.MyViewModel;
import com.example.viaporter.managers.DataManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.LatLng;

import static android.view.View.GONE;
import static com.example.viaporter.constants.AppConstants.*;

public class TripConfirmedFragment extends Fragment
        implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "TripConfirmedFragment";

    private NavController navController;
    private MainActivity mActivity;
//    private MyViewModel model;
    private View mMapView;

    // Views
    private FrameLayout notifyBlue;
    private FrameLayout notifyGreen;
    private Button startTripButton;
    private Button cancelTripButton;
    private Button stopTripButton;

    // Local variables
    private boolean isStartButtonPressed = false;
    private boolean isPorterNearby = false;

    // Location Services variables
    private SupportMapFragment mapFragment;
    private GoogleMap mGoogleMap;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private LatLng currentLocation;

//    private Marker driverMarker;
//    private Marker originMarker;
//    private Marker destMarker;
//    private Bitmap driverCarMarkerBitmap, driverAutoMarkerBitmap, driverSuvMarkerBitmap;
//
//    private LatLng currentLocation;

    public TripConfirmedFragment() {
        // Empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");

        return inflater.inflate(R.layout.trip_confirmed_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mActivity = (MainActivity) requireActivity();

        setUpMap();
        setViews();
//        setUpViewModel();
    }

    private void setUpMap() {

        Log.d(TAG, "setUpMap");
        try {
            mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.google_map_confirmed);
            mMapView = mapFragment.getView();
            if (mapFragment != null) {
                mapFragment.getMapAsync(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady");

        mGoogleMap = googleMap;
        enableLocationService();
    }

    private void enableLocationService() {

        Log.d(TAG, "enableLocationService");

        // Check permission to get User's location
        if (ContextCompat.checkSelfPermission(mActivity, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mActivity, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSION_FINE_LOCATION);
            return;
        }

        selectStartingPoint();

        mGoogleMap.setMyLocationEnabled(true);
        setMapUiSettings();

        mGoogleApiClient = new GoogleApiClient.Builder(mActivity)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mGoogleApiClient.connect();
        LocationServices.getFusedLocationProviderClient(mActivity).requestLocationUpdates(mLocationRequest, mLocationCallback, null);
    }

    private void selectStartingPoint() {
        try {
            currentLocation = mActivity.dataManager.getCurrentLocation();
            Log.d(TAG, "selectStartingPoint, currentLocation = " + currentLocation.toString());
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(currentLocation, MAP_CAMERA_ZOOM);
            mGoogleMap.moveCamera(update);
            mMapView.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setMapUiSettings() {

        // Map UI settings
        UiSettings mUiSettings = mGoogleMap.getUiSettings();
        mUiSettings.setMyLocationButtonEnabled(true);
        mUiSettings.setCompassEnabled(false);
        mUiSettings.setMapToolbarEnabled(false);

        // Move location button to bottom
        View locationButton = ((View) mMapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();

        // position on right bottom
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 0);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        rlp.setMargins(0, 50, 50, 50);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        Log.d(TAG, "onConnected");

        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        mLocationRequest.setInterval(MAP_LOCATION_REQUEST_INTERVAL);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (mActivity.checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }

        LocationServices.getFusedLocationProviderClient(mActivity).requestLocationUpdates(mLocationRequest, mLocationCallback, null);
    }

    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {}

    private LocationCallback mLocationCallback = new LocationCallback() {
        private boolean mCameraUpdated = false;

        @Override
        public void onLocationResult(LocationResult locationResult) {

            Log.d(TAG, "onLocationResult");

            for (Location location : locationResult.getLocations()) {
                // update location
                currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                mActivity.dataManager.setCurrentLocation(currentLocation);

                // update camera
                if (!mCameraUpdated) {
                    CameraUpdate update = CameraUpdateFactory.newLatLngZoom(currentLocation, MAP_CAMERA_ZOOM);
                    mGoogleMap.moveCamera(update);
                    mCameraUpdated = true;
                    mMapView.setVisibility(View.VISIBLE);
                }
            }
        }
    };

    private void setViews() {

        Log.d(TAG, "setViews");

        notifyBlue = mActivity.findViewById(R.id.notification_porter_on_the_way);
        notifyGreen = mActivity.findViewById(R.id.notification_porter_arrived);
        startTripButton = mActivity.findViewById(R.id.button_start_official_trip);
        cancelTripButton = mActivity.findViewById(R.id.button_cancel_official_trip);
        stopTripButton = mActivity.findViewById(R.id.button_stop_official_trip);
        navController = Navigation.findNavController(mActivity, R.id.my_nav_host_fragment);

        if (startTripButton != null) {
            startTripButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isStartButtonPressed) {
                        startTripButton.setText(R.string.trip_confirmed_button_start_confirm_trip);
                        isStartButtonPressed = true;
                    } else {
                        // todo: start trip
                        // Testing visibility changes. todo: move this part to porter location proximity checks
                        if (notifyBlue != null) {
                            notifyBlue.setVisibility(GONE);
                        }

                        if (notifyGreen != null) {
                            notifyGreen.setVisibility(View.VISIBLE);
                        }

//                        startTripButton.setVisibility(View.GONE);
//                        cancelTripButton.setVisibility(View.GONE);
//                        stopTripButton.setVisibility(View.VISIBLE);
                    }
                }
            });
        }

        if (cancelTripButton != null) {
            cancelTripButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(mActivity)
                            .setTitle(R.string.trip_confirmed_button_cancel_trip)
                            .setMessage(R.string.trip_confirmed_button_cancel_msg)
                            .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    // todo: cancel trip and go back to home page
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .create()
                            .show();
                }
            });
        }

        if (stopTripButton != null) {
            stopTripButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(mActivity)
                            .setTitle(R.string.trip_confirmed_button_stop_title)
                            .setMessage(R.string.trip_confirmed_button_stop_msg)
                            .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    // todo: show review page and go back to home page
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .create()
                            .show();
                }
            });
        }

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                Log.d(TAG, "onDestinationChanged " + destination.getLabel() + destination.getId());
            }
        });
    }

//    private void setUpViewModel() {
//
//        Log.d(TAG, "setUpViewModel");
//
//        // Re-created activities receive the same MyViewModel instance created by the first activity.
//        model = ViewModelProviders.of(mActivity).get(MyViewModel.class);
//    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) { super.onActivityCreated(savedInstanceState); }

    @Override
    public void onStart() { super.onStart(); }

    @Override
    public void onResume() { super.onResume(); }

    @Override
    public void onPause() { super.onPause(); }

    @Override
    public void onDestroy() { super.onDestroy(); }
}