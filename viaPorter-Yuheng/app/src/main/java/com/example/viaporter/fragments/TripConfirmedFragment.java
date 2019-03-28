package com.example.viaporter.fragments;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.example.viaporter.R;
import com.example.viaporter.MainActivity;
import com.example.viaporter.models.LocationUpdate;
import com.example.viaporter.models.TripState;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import static android.view.View.GONE;
import static com.example.viaporter.constants.AppConstants.*;

public class TripConfirmedFragment extends Fragment
        implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "TripConfirmedFragment";

    private NavController navController;
    private MainActivity mActivity;
    private View mMapView;

    // Views
    private TextView viewTitle;
    private FrameLayout notifyBlue;
    private FrameLayout notifyGreen;
    private Button startTripButton;
    private Button cancelTripButton;
    private Button stopTripButton;

    // Location Services variables
    private SupportMapFragment mapFragment;
    private GoogleMap mGoogleMap;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Marker patronMarker;

    public TripConfirmedFragment() {}

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

        setupMap();
        setupViews();
        setupFirebaseListeners();
    }

    @Override
    public void onResume() {
        super.onResume();
        mActivity.getBottomNavigationView().getMenu().getItem(1).setEnabled(true);
    }

    private void setupMap() {

        Log.d(TAG, "setupMap");
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

        patronMarker = googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(0,0))
                .title("Patron Location")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
    }

    private void enableLocationService() {

        Log.d(TAG, "enableLocationService");

        // Check permission to get User's location
        if (ContextCompat.checkSelfPermission(mActivity, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mActivity, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSION_FINE_LOCATION);
            return;
        }

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
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        rlp.setMargins(0, 50, 50, 50);
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
                Double latitude = location.getLatitude();
                Double longitude = location.getLongitude();
                LatLng currentLocation = new LatLng(latitude, longitude);

                mActivity.getDataManager().setCurrentLocation(currentLocation);
                mActivity.getFirebaseDatabaseManager().updateMyCurrentLocation(latitude, longitude);

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

    private void setupViews() {
        Log.d(TAG, "setupViews");

        // for setting chat icon to be enabled
        mActivity.getBottomNavigationView().getMenu().getItem(1).setEnabled(true);

        viewTitle = mActivity.findViewById(R.id.trip_confirmed_title);
        notifyBlue = mActivity.findViewById(R.id.notification_porter_on_the_way);
        notifyGreen = mActivity.findViewById(R.id.notification_porter_arrived);
        startTripButton = mActivity.findViewById(R.id.button_start_official_trip);
        cancelTripButton = mActivity.findViewById(R.id.button_cancel_official_trip);
        stopTripButton = mActivity.findViewById(R.id.button_stop_official_trip);
        navController = Navigation.findNavController(mActivity, R.id.my_nav_host_fragment);

        startTripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mActivity.getDataManager().getTripState() == TripState.PATRON_ARRIVED){
                    updateTripInProgress();
                } else {
                    Toast.makeText(mActivity,"Please proceed to find your patron.", Toast.LENGTH_LONG).show();
                }
            }
        });

        cancelTripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.getDialogManager().showTripConfirmedCancelTripDialog();
            }
        });

        stopTripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.getDialogManager().showTripConfirmedStopTripDialog();
            }
        });

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                Log.d(TAG, "onDestinationChanged " + destination.getLabel() + destination.getId());
            }
        });
    }

    private void setupFirebaseListeners() {
        String patronUid = mActivity.getDataManager().getSelectedBidRequest().getPatronUid();

        mActivity.getFirebaseDatabaseManager().patronDatabase
                .child(patronUid)
                .child("currentLocation")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            LocationUpdate newLocation = dataSnapshot.getValue(LocationUpdate.class);
                            LatLng newCoordinates = new LatLng(newLocation.getLat(), newLocation.getLong());

                            patronMarker.setPosition(newCoordinates);

                            if (mActivity.getDataManager().getTripState() == TripState.PATRON_STARTED) {
                                if (mActivity.getDataManager().getCurrentLocation() != null) {
                                    if (distanceBetweenUsers(mActivity.getDataManager().getCurrentLocation(), newCoordinates) < MAP_DISTANCE_BETWEEN_PROXIMITY) {
                                        porterArrived();
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        mActivity.getFirebaseDatabaseManager().setListeners(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    TripState curState = dataSnapshot.getValue(TripState.class);
                    switch (curState){
                        case NOT_STARTED:
                            break;
                        case PATRON_STARTED:
                            break;
                        case CANCELLED:
                            navToHome();
                            break;
                        case ENDED:
                            Toast.makeText(mActivity,"Your patron has stopped their trip.", Toast.LENGTH_SHORT).show();
                            break;
                        default :
                            Log.d(TAG,"State change value not in switch statement");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        }, "TripConfirmedPatronTripStatus");
        mActivity.getFirebaseDatabaseManager().startPatronTripConfirmedStatusListener();
    }

    private float distanceBetweenUsers(LatLng porterLatLng, LatLng patronLatLng) {
        Location loc1 = new Location("");
        loc1.setLatitude(porterLatLng.latitude);
        loc1.setLongitude(porterLatLng.longitude);

        Location loc2 = new Location("");
        loc2.setLatitude(patronLatLng.latitude);
        loc2.setLongitude(patronLatLng.longitude);

        return loc1.distanceTo(loc2);
    }

    private void porterArrived() {
        mActivity.getDataManager().setTripState(TripState.PATRON_ARRIVED);
        notifyBlue.setVisibility(GONE);
        notifyGreen.setVisibility(View.VISIBLE);
    }

    private void updateTripInProgress() {
        mActivity.getDataManager().setTripState(TripState.IN_PROGRESS);

        // Update UI changes
        viewTitle.setText(getString(R.string.trip_confirmed_trip_in_progress));
        notifyBlue.setVisibility(GONE);
        notifyGreen.setVisibility(GONE);
        startTripButton.setVisibility(GONE);
        cancelTripButton.setVisibility(GONE);
        stopTripButton.setVisibility(View.VISIBLE);
    }

    private void navToHome() {
        if (mActivity.getDataManager().getTripState() == TripState.IN_PROGRESS
                || mActivity.getDataManager().getTripState() == TripState.PATRON_STARTED) {
            mActivity.getDataManager().setTripState(TripState.CANCELLED);
            NavOptions navOptions = new NavOptions.Builder()
                    .setPopUpTo(R.id.navigation_jobs, false)
                    .build();
            navController.navigate(R.id.navigation_jobs, null, navOptions);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mActivity.getBottomNavigationView().getMenu().getItem(1).setEnabled(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}