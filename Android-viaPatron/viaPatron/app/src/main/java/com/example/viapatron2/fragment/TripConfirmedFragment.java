package com.example.viapatron2.fragment;

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
import android.widget.TextView;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import com.example.viapatron2.R;
import com.example.viapatron2.activity.MainActivity;
import com.example.viapatron2.core.models.LocationUpdate;
import com.example.viapatron2.core.models.MyViewModel;
import com.example.viapatron2.core.models.PorterTripAccept;
import com.example.viapatron2.core.models.TripStatus;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import io.reactivex.functions.Consumer;

import static android.view.View.GONE;
import static com.example.viapatron2.app.constants.AppConstants.*;

public class TripConfirmedFragment extends Fragment
        implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "TripConfirmedFragment";

    private NavController navController;
    private MainActivity mActivity;
    private MyViewModel model;
    private View mMapView;

    // Views
    private FrameLayout notifyBlue;
    private FrameLayout notifyGreen;
    private Button startTripButton;
    private Button cancelTripButton;
    private Button stopTripButton;
    private TextView pageTitle;

    // Local variables
    private boolean isStartButtonPressed = false;

    // Location Services variables
    private SupportMapFragment mapFragment;
    private GoogleMap mGoogleMap;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private LatLng currentLocation;

    private Marker porterMarker;
    private AlertDialog reviewDialog;

//    private Marker originMarker;
//    private Marker destMarker;
//    private Bitmap driverCarMarkerBitmap, driverAutoMarkerBitmap, driverSuvMarkerBitmap;

    public TripConfirmedFragment() {
        // Empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            Log.d(TAG, "onCreateView - " + savedInstanceState.describeContents());
        } else {
            Log.d(TAG, "onCreateView - savedInstanceState is null");
        }

        return inflater.inflate(R.layout.trip_confirmed_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mActivity = (MainActivity) requireActivity();

        setUpMap();
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
        buildGoogleApiClient();
        LocationServices.getFusedLocationProviderClient(mActivity).requestLocationUpdates(mLocationRequest, mLocationCallback, null);
    }

    private void selectStartingPoint() {
        try {
            currentLocation = mActivity.getmDataManager().getCurrentLocation();

            if(mGoogleMap != null) {
                porterMarker = mGoogleMap.addMarker((new MarkerOptions().position(currentLocation))
                        .title("Patron's Current Location")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            }

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
        mUiSettings.setCompassEnabled(true);
        mUiSettings.setMapToolbarEnabled(false);

        // Retrieve location button
        View locationButton = ((View) mMapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();

        // Retrieve compass button
        View compassButton = ((View) mMapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("5"));
        RelativeLayout.LayoutParams rlpCompass = (RelativeLayout.LayoutParams) compassButton.getLayoutParams();

        // position location button at right bottom
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        rlp.setMargins(0, 50, 100, 50);

        // position compass button at left button
        rlpCompass.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        rlpCompass.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        rlpCompass.setMargins(100, 50, 0, 50);
    }

    private void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(mActivity)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        Log.d(TAG, "onConnected");

        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
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
                mActivity.getmDataManager().setCurrentLocation(currentLocation);

                // Send viaPorter my updated location
                LocationUpdate patronUpdatedLocation = new LocationUpdate();
                patronUpdatedLocation.setUpdatedLocation(currentLocation);
                mActivity.getmSocketManager().sendLocation(patronUpdatedLocation);

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

        mActivity.getBottomNavigationView().getMenu().getItem(1).setEnabled(true);

        notifyBlue = mActivity.findViewById(R.id.notification_porter_on_the_way);
        notifyGreen = mActivity.findViewById(R.id.notification_porter_arrived);
        startTripButton = mActivity.findViewById(R.id.button_start_official_trip);
        cancelTripButton = mActivity.findViewById(R.id.button_cancel_official_trip);
        pageTitle = mActivity.findViewById(R.id.trip_confirm_page_title);
        stopTripButton = mActivity.findViewById(R.id.button_stop_official_trip);
        navController = Navigation.findNavController(mActivity, R.id.my_nav_host_fragment);

        Log.d(TAG, "TripStatus = " + mActivity.getmDataManager().getTripStatus());
        if (mActivity.getmDataManager().getTripStatus() == TripStatus.IN_PROGRESS) {
            stopTripButton.setVisibility(View.VISIBLE);
            startTripButton.setVisibility(View.GONE);
            cancelTripButton.setVisibility(View.GONE);
            notifyGreen.setVisibility(View.GONE);
            notifyBlue.setVisibility(View.GONE);
            pageTitle.setText(R.string.trip_in_progress_main_title);
        }

        if (startTripButton != null) {
            startTripButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isStartButtonPressed) {
                        startTripButton.setText(R.string.trip_confirmed_button_start_confirm_trip);
                        isStartButtonPressed = true;
                    } else {
                        userStartTrip();
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
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    try {
                                        mActivity.getmDataManager().updateTripStatus(TripStatus.CANCELLED);
                                        mActivity.getBottomNavigationView().getMenu().getItem(1).setEnabled(false);
                                        mActivity.getmDatabase().child("chats").removeValue();
                                        // todo: to fix the bug later on
                                        mActivity.getmDatabase().child("patron_trip_requests").removeValue();

                                        // Cancel trip and go back to home page
                                        NavOptions navOptions = new NavOptions.Builder()
                                                .setPopUpTo(R.id.navigation_trip_confirmed, true)
                                                .build();
                                        navController.navigate(R.id.navigation_trip, null, navOptions);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
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
                            .setTitle(R.string.trip_confirmed_button_stop_trip)
                            .setMessage(R.string.trip_confirmed_button_stop_msg)
                            .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    try {
                                        mActivity.getmDataManager().updateTripStatus(TripStatus.ENDED);
                                        mActivity.getBottomNavigationView().getMenu().getItem(1).setEnabled(false);
                                        mActivity.getmDatabase().child("chats").removeValue();
                                        // todo: to fix the bug later on
                                        mActivity.getmDatabase().child("patron_trip_requests").removeValue();

                                        // Cancel trip and go back to home page
                                        NavOptions navOptions = new NavOptions.Builder()
                                                .setPopUpTo(R.id.navigation_trip_confirmed, true)
                                                .build();
                                        navController.navigate(R.id.navigation_trip, null, navOptions);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
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
    }

    private void setUpViewModel() {

        Log.d(TAG, "setUpViewModel");

        // Re-created activities receive the same MyViewModel instance created by the first activity.
        model = ViewModelProviders.of(mActivity).get(MyViewModel.class);
    }

    private void setupSocketListeners() {
        mActivity.addDisposable(mActivity.getmSocketManager().addOnPorterTripAccept(new Consumer<PorterTripAccept>() {
            @Override
            public void accept(final PorterTripAccept porterInfo) {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Log.d(TAG, "porterInfo: name = " + porterInfo.getPorterName());
                            Log.d(TAG, "porterInfo: id = " + porterInfo.getPorterId());
                            Log.d(TAG, "porterInfo: location = " + porterInfo.getPorterLocation().toString());

                            if(mGoogleMap != null) {
//                                porterMarker = mGoogleMap.addMarker(new MarkerOptions().position(porterInfo.getPorterLocation())
//                                        .title("Porter's Current Location")
//                                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

                                CameraUpdate update = CameraUpdateFactory.newLatLngZoom(porterInfo.getPorterLocation(), MAP_CAMERA_ZOOM);
                                mGoogleMap.moveCamera(update);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }));

        mActivity.addDisposable(mActivity.getmSocketManager().addOnPorterLocationUpdate(new Consumer<LocationUpdate>() {
            @Override
            public void accept(final LocationUpdate porterUpdatedLocation) {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            porterMarker.setPosition(porterUpdatedLocation.getUpdatedLocation());

                            Log.d(TAG, "distance = " + distanceBetwUsers(currentLocation, porterUpdatedLocation.getUpdatedLocation()));

                            if (mActivity.getmDataManager().getTripStatus() != TripStatus.IN_PROGRESS) {
                                if (distanceBetwUsers(currentLocation, porterUpdatedLocation.getUpdatedLocation()) < MAP_DISTANCE_BETWEEN_PROXIMITY) {
                                    notifyPorterArrived();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }));
    }

    private float distanceBetwUsers(LatLng patronLatLng, LatLng porterLatLng) {

        Location loc1 = new Location("");
        loc1.setLatitude(patronLatLng.latitude);
        loc1.setLongitude(patronLatLng.longitude);

        Location loc2 = new Location("");
        loc2.setLatitude(porterLatLng.latitude);
        loc2.setLongitude(porterLatLng.longitude);

        return loc1.distanceTo(loc2);
    }

    private void notifyPorterArrived() {
        // Update UI changes
        if (notifyBlue != null) {
            notifyBlue.setVisibility(GONE);
        }

        if (notifyGreen != null) {
            notifyGreen.setVisibility(View.VISIBLE);
        }
    }

    private void userStartTrip() {

        if (isStartButtonPressed) {
            startTripButton.setVisibility(GONE);
            cancelTripButton.setVisibility(View.GONE);

            if (stopTripButton != null) {
                stopTripButton.setVisibility(View.VISIBLE);
            }

            if (pageTitle != null) {
                pageTitle.setText(R.string.trip_in_progress_main_title);
            }

            if (notifyGreen != null) {
                notifyGreen.setVisibility(View.GONE);
            }
        }

        mActivity.getmDataManager().updateTripStatus(TripStatus.IN_PROGRESS);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) { super.onActivityCreated(savedInstanceState); }

    @Override
    public void onStart() { super.onStart(); }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");

        setViews();
        setUpViewModel();
        setupSocketListeners();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}
