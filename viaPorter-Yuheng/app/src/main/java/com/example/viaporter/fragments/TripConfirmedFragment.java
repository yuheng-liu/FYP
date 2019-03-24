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

import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import io.reactivex.functions.Consumer;

import com.example.viaporter.R;
import com.example.viaporter.MainActivity;
import com.example.viaporter.models.LocationUpdate;
import com.example.viaporter.models.PatronTripSuccess;
import com.example.viaporter.models.TripStatus;
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
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import static android.view.View.GONE;
import static com.example.viaporter.constants.AppConstants.*;

public class TripConfirmedFragment extends Fragment
        implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "TripConfirmedFragment";

    private NavController navController;
    private MainActivity mActivity;
    private Gson gson;
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
    private LatLng currentLocation;
    private Marker patronMarker;

    private static final LatLng UTOWN = new LatLng(PLACE_NUS_UTOWN_LAT, PLACE_NUS_UTOWN_LNG);
    private static final LatLng FOS = new LatLng(PLACE_NUS_FOS_LAT, PLACE_NUS_FOS_LNG);

    private PatronTripSuccess currentTrip;

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
        gson = new Gson();

        setupMap();
        setupViews();
        setupSocketListeners();
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

        currentTrip = mActivity.dataManager.getCurrentTrip();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady");

        mGoogleMap = googleMap;
        enableLocationService();

        if (currentTrip != null) {
            patronMarker = googleMap.addMarker(new MarkerOptions()
                    .position(currentTrip.getPatronLocation())
                    .title("Patron Location")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        }
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
//            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(UTOWN, MAP_CAMERA_ZOOM);
//            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(FOS, MAP_CAMERA_ZOOM);
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
                mActivity.dataManager.setCurrentLocation(currentLocation);

                // Broadcast Location Update
                try {
                    LocationUpdate newLocation = new LocationUpdate(currentLocation);
                    JSONObject data = new JSONObject(gson.toJson(newLocation));
                    mActivity.socketManager.emitJSON("location_update_porter", data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

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
                updateTripInProgressUI();
            }
        });

        cancelTripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.dialogManager.showTripConfirmedCancelTripDialog();
            }
        });

        stopTripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.dialogManager.showTripConfirmedStopTripDialog();
            }
        });

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                Log.d(TAG, "onDestinationChanged " + destination.getLabel() + destination.getId());
            }
        });
    }

    private void setupSocketListeners() {
        mActivity.addDisposable(mActivity.socketManager.addOnPatronLocationUpdate(new Consumer<LocationUpdate>() {
            @Override
            public void accept(final LocationUpdate locationUpdate) {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        patronMarker.setPosition(locationUpdate.getLocation());
                        // Update UI when porter is near enough to patron
                        if (mActivity.dataManager.getTripStatus() == TripStatus.PROCEEDING){
                            if (distanceBetweenUsers(currentLocation, locationUpdate.getLocation()) < MAP_DISTANCE_BETWEEN_PROXIMITY) {
                                porterArrived();
                            }
                        }
                    }
                });
            }
        }));
    }

    private float distanceBetweenUsers(LatLng porterLatLng, LatLng patronLatLng) {
        Location loc1 = new Location("");
        loc1.setLatitude(patronLatLng.latitude);
        loc1.setLongitude(patronLatLng.longitude);

        Location loc2 = new Location("");
        loc2.setLatitude(porterLatLng.latitude);
        loc2.setLongitude(porterLatLng.longitude);

        return loc1.distanceTo(loc2);
    }

    private void porterArrived() {
        mActivity.dataManager.setTripStatus(TripStatus.IN_PROGRESS);
        notifyBlue.setVisibility(GONE);
        notifyGreen.setVisibility(View.VISIBLE);
        startTripButton.setVisibility(View.VISIBLE);
    }

    private void updateTripInProgressUI() {
        // Update UI changes
        viewTitle.setText(getString(R.string.trip_confirmed_trip_in_progress));
        notifyBlue.setVisibility(GONE);
        notifyGreen.setVisibility(GONE);
        startTripButton.setVisibility(GONE);
        cancelTripButton.setVisibility(GONE);
        stopTripButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}