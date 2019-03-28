/*
    THIS FRAGMENT IS NO LONGER USED
 */

//package com.example.viaporter.fragments;
//
//import android.Manifest;
//import android.content.Context;
//import android.content.pm.PackageManager;
//import android.location.Location;
//import android.location.LocationManager;
//import android.os.Build;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.app.Fragment;
//import android.support.v4.content.ContextCompat;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.RelativeLayout;
//import android.widget.Toast;
//
//import com.example.viaporter.MainActivity;
//import com.example.viaporter.R;
//import androidx.navigation.NavController;
//import androidx.navigation.Navigation;
//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.location.*;
//import com.google.android.gms.location.places.Place;
//import com.google.android.gms.maps.*;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.Marker;
//
//import static com.example.viaporter.constants.AppConstants.MAP_CAMERA_ZOOM;
//import static com.example.viaporter.constants.AppConstants.MAP_LOCATION_REQUEST_INTERVAL;
//import static com.example.viaporter.constants.AppConstants.PERMISSION_FINE_LOCATION;
//
//public class HomeFragment extends Fragment
//        implements OnMapReadyCallback,
//        GoogleApiClient.ConnectionCallbacks,
//        GoogleApiClient.OnConnectionFailedListener {
//
//    private static final String TAG = "viaPatron.HomeFragment";
//
//    private MainActivity mActivity;
//    private View mMapView;
//    private NavController navController;
//
//    // Location Services variables
//    private SupportMapFragment mapFragment;
//    private GoogleMap mGoogleMap;
//    private LatLng currentLocation;
//    private GoogleApiClient mGoogleApiClient;
//    private Location mLastLocation;
//    private Marker mCurrLocationMarker;
//    private FusedLocationProviderClient mFusedLocationClient;
//
//    // Google Places Variables
//    private Place originPlace, destPlace;
//    private LocationRequest mLocationRequest;
//    private boolean hasStartingPointChanged = false;
//    private boolean hasEndingPointChanged = false;
//
//    public HomeFragment() {}
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.home_fragment, container, false);
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        mActivity = (MainActivity) requireActivity();
//
//        setUpMap();
//
//        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mActivity);
//        navController = Navigation.findNavController(mActivity, R.id.my_nav_host_fragment);
//    }
//
//    private void setUpMap() {
//        Log.d(TAG, "setUpMap");
//
//        try {
//            mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.google_map);
//            mMapView = mapFragment.getView();
//            if (mapFragment != null) {
//                mapFragment.getMapAsync(this);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        Log.d(TAG, "onMapReady");
//
//        mGoogleMap = googleMap;
//        enableLocationService();
//    }
//
//    private void enableLocationService() {
//        Log.d(TAG, "enableLocationService");
//
//        // Check permission to get User's location
//        if (ContextCompat.checkSelfPermission(mActivity, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(mActivity, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
//                    PERMISSION_FINE_LOCATION);
//            return;
//        }
//
//        selectStartingPoint();
//
//        mGoogleMap.setMyLocationEnabled(true);
//        setMapUiSettings();
//
//        mGoogleApiClient = new GoogleApiClient.Builder(mActivity)
//                .addApi(LocationServices.API)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .build();
//
//        mGoogleApiClient.connect();
//        Log.d(TAG, "Client isConnecting = " + mGoogleApiClient.isConnecting());
//        LocationServices.getFusedLocationProviderClient(mActivity).requestLocationUpdates(mLocationRequest, mLocationCallback, null);
//    }
//
//    private void selectStartingPoint() {
//
//        Log.d(TAG, "selectStartingPoint");
//
//        try {
//            LocationManager locationManager = (LocationManager) mActivity.getSystemService(Context.LOCATION_SERVICE);
//            if (mActivity.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                Location tempLoc = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
//                Log.d(TAG, "Latitude = " + tempLoc.getLatitude());
//                Log.d(TAG, "Longitude = " + tempLoc.getLongitude());
//
//                currentLocation = new LatLng(tempLoc.getLatitude(), tempLoc.getLongitude());
//                CameraUpdate update = CameraUpdateFactory.newLatLngZoom(currentLocation, MAP_CAMERA_ZOOM);
//                mGoogleMap.moveCamera(update);
//                mMapView.setVisibility(View.VISIBLE);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void setMapUiSettings() {
//
//        Log.d(TAG, "setMapUiSettings");
//
//        // Map UI settings
//        UiSettings mUiSettings = mGoogleMap.getUiSettings();
//        mUiSettings.setMyLocationButtonEnabled(true);
//        mUiSettings.setCompassEnabled(false);
//        mUiSettings.setMapToolbarEnabled(false);
//
//        // Move location button to bottom
//        View locationButton = ((View) mMapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
//        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
//
//        // position on right bottom
//        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
//        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
//        rlp.setMargins(0, 0, 50, 50);
//    }
//
//    private LocationCallback mLocationCallback = new LocationCallback() {
//        private boolean mCameraUpdated = false;
//
//        @Override
//        public void onLocationResult(LocationResult locationResult) {
//            for (Location location : locationResult.getLocations()) {
//                // update location
//                currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
//                mActivity.getDataManager().setCurrentLocation(currentLocation);
//
//                // update camera
//                if (!mCameraUpdated) {
//                    CameraUpdate update = CameraUpdateFactory.newLatLngZoom(currentLocation, MAP_CAMERA_ZOOM);
//                    mGoogleMap.moveCamera(update);
//                    mCameraUpdated = true;
//                    mMapView.setVisibility(View.VISIBLE);
//                }
//            }
//        }
//    };
//
//    @Override
//    public void onConnected(@Nullable Bundle bundle) {
//
//        Log.d(TAG, "onConnected, clientConnected = " + mGoogleApiClient.isConnected());
//
//        mLocationRequest = LocationRequest.create();
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
//        mLocationRequest.setInterval(MAP_LOCATION_REQUEST_INTERVAL);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (mActivity.checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                return;
//            }
//        }
//
//        LocationServices.getFusedLocationProviderClient(mActivity).requestLocationUpdates(mLocationRequest, mLocationCallback, null);
//    }
//
//    @Override
//    public void onConnectionSuspended(int i) {
//
//    }
//
//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
//        Log.d(TAG, "onRequestPermissionsResult");
//
//        switch (requestCode) {
//            case PERMISSION_FINE_LOCATION: {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    // permission was granted
//                    enableLocationService();
//                }
//                else {
//                    Toast.makeText(mActivity.getApplicationContext(), R.string.user_location_access_denied, Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            // other 'case' lines to check for other
//            // permissions this app might request
//        }
//    }
//}
