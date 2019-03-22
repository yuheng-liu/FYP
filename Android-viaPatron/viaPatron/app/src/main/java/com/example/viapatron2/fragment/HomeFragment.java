package com.example.viapatron2.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.*;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.example.viapatron2.R;
import com.example.viapatron2.TextValidator;
import com.example.viapatron2.activity.MainActivity;
import com.example.viapatron2.app.constants.AppConstants;
import com.example.viapatron2.core.models.MyViewModel;
import com.example.viapatron2.core.models.TripStatus;
import com.example.viapatron2.core.models.UserTripRequestSession;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.*;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;

import static com.example.viapatron2.app.constants.AppConstants.*;

public class HomeFragment extends Fragment
        implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnPoiClickListener {

    private static final String TAG = "viaPatron.HomeFragment";
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = AppConstants.PERMISSION_FINE_LOCATION;

    // App
    private MainActivity mActivity;

    // Views
    private View mMapView;
    private AppCompatSpinner stationSpinner;
    private ArrayAdapter<CharSequence> adapter;
    private AlertDialog reviewDialog;
    private Button nextButton;
    private FrameLayout swapDestination;
    private FrameLayout customLocationLayout;
    private LinearLayout trainLocationLayout;
    private EditText trainNumField;
    private EditText coachNumField;
    private EditText seatNumField;

    // View variables
    private boolean swapViewsFlag = true;
    private float pixelDifference = 0f;
    private String trainNumString;
    private String coachNumString;
    private String seatNumString;

    // Controllers
    private MyViewModel model;
    private NavController navController;
    private UserTripRequestSession userTripRequestSession;

    // Location Services variables
    private SupportMapFragment mapFragment;
    private GoogleMap mGoogleMap;
    private LatLng currentLocation;
    private GoogleApiClient mGoogleApiClient;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest mLocationRequest;
    private Marker mUserMarker;
    private Place mPlace;


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

        setUpMap();
        setUpPlaces();
//        setUpViews();
//        setUpViewModel();
//        setUpClickable();

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mActivity);
        navController = Navigation.findNavController(mActivity, R.id.my_nav_host_fragment);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void setUpPlaces() {

        /**
         * Initialize Places. For simplicity, the API key is hard-coded. In a production
         * environment we recommend using a secure mechanism to manage API keys.
         */
        if (!Places.isInitialized()) {
            Places.initialize(mActivity, API_KEY_STRING);
        }

        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                this.getChildFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        if (autocompleteFragment != null) {
            autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS));

            // Restrict autocomplete to Singapore's Boundaries
            // Next time to restrict autocomplete based on location/postal code of users.
            // Extracted boundaries from https://gist.github.com/graydon/11198540
            RectangularBounds bounds = RectangularBounds.newInstance(
                    new LatLng(1.1304753, 103.6920359),
                    new LatLng(1.4504753, 104.0120359));

            autocompleteFragment.setLocationBias(bounds);

            autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                @Override
                public void onPlaceSelected(@NonNull Place place) {
                    Log.i(TAG, "Place: " + place.getName() + ", " + place.getId() + ", " + place.getLatLng() + ", " + place.getAddress());

                    try {
                        mPlace = place;

                        // clear old markers
                        mGoogleMap.clear();

                        if (mPlace.getLatLng() != null) {
                            // add new marker to selected location
                            mUserMarker = mGoogleMap.addMarker(new MarkerOptions().position(mPlace.getLatLng())
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

                            // Edit marker title
                            setmUserMarkerTitle();

                            // move camera to selected location
                            updateMapCamera(mPlace.getLatLng());

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(Status status) {
                    // TODO: Handle the error.
                    Log.i(TAG, "An error occurred: " + status);
                }
            });
        }
    }

    private void setUpViews() {
        nextButton = (Button) mActivity.findViewById(R.id.stations_spinner_next);
        swapDestination = (FrameLayout) mActivity.findViewById(R.id.home_swap_to_from);
        customLocationLayout = (FrameLayout) mActivity.findViewById(R.id.home_custom_location);
        trainLocationLayout = (LinearLayout) mActivity.findViewById(R.id.home_train_coach_seat_location);
        trainNumField = (EditText) mActivity.findViewById(R.id.home_train_field);
        coachNumField = (EditText) mActivity.findViewById(R.id.home_coach_field);
        seatNumField = (EditText) mActivity.findViewById(R.id.home_seat_field);

        if (swapDestination != null) {
            swapDestination.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "swapDestination onClick");

                    swapDestinationViews(swapViewsFlag);
                    swapViewsFlag = !swapViewsFlag;
                    setmUserMarkerTitle();
                }
            });
        }

        if (trainNumField != null) {
            trainNumField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_NEXT) {
                        trainNumString = trainNumField.getText().toString();
                    }
                    return false;
                }
            });

            trainNumField.addTextChangedListener(new TextValidator(trainNumField) {
                @Override
                public void validate(TextView textView, String text) {
                    /* Validation code here */
                    if (trainNumField.getText().toString().length() <= 0) {
                        trainNumField.setError("Enter Train Number");
                    } else {
                        trainNumField.setError(null);
                    }
                }
            });
        }

        if (coachNumField != null) {
            coachNumField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_NEXT) {
                        coachNumString = coachNumField.getText().toString();
                    }
                    return false;
                }
            });

            coachNumField.addTextChangedListener(new TextValidator(coachNumField) {
                @Override
                public void validate(TextView textView, String text) {
                    /* Validation code here */
                    if (coachNumField.getText().toString().length() <= 0) {
                        coachNumField.setError("Enter Train Number");
                    } else {
                        coachNumField.setError(null);
                    }
                }
            });
        }

        if (seatNumField != null) {
            seatNumField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        seatNumString = seatNumField.getText().toString();
                    }
                    return false;
                }
            });

            seatNumField.addTextChangedListener(new TextValidator(seatNumField) {
                @Override
                public void validate(TextView textView, String text) {
                    /* Validation code here */
                    if (seatNumField.getText().toString().length() <= 0) {
                        seatNumField.setError("Enter Train Number");
                    } else {
                        seatNumField.setError(null);
                    }
                }
            });
        }

        if (nextButton != null) {

            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (fieldsValid()) {
                        userTripRequestSession = new UserTripRequestSession();
                        String concatenateDest = "Train: " + trainNumString + ", Coach: " + coachNumString + ", Seat: " + seatNumString;

                        if (swapViewsFlag) {
                            // From custom location to Train
                            userTripRequestSession.setTripStartLocation(mPlace.getName());
                            userTripRequestSession.setTripEndLocation(concatenateDest);
                        } else {
                            // From Train to custom location
                            userTripRequestSession.setTripStartLocation(concatenateDest);
                            userTripRequestSession.setTripEndLocation(mPlace.getName());
                        }

                        model.setRequestSession(userTripRequestSession);
                        navController.navigate(R.id.navigation_trip_request);
                    }
                }
            });
        }

        checkTripStatus();
    }

    private void swapDestinationViews(boolean swapFlag) {

        if (swapFlag) {
            if (customLocationLayout != null && trainLocationLayout != null) {

                int[] topViewLoc = new int[2];
                int[] botViewLoc = new int[2];

                // Retrieve location of the layouts into arrays
                customLocationLayout.getLocationOnScreen(topViewLoc);
                trainLocationLayout.getLocationOnScreen(botViewLoc);

                // Get the difference between the layouts
                pixelDifference = botViewLoc[1] - topViewLoc[1];

                // Animate the layout
                customLocationLayout.animate().translationY(pixelDifference).setDuration(500);
                trainLocationLayout.animate().translationY(-pixelDifference).setDuration(500);
            }
        } else {
            if (customLocationLayout != null && trainLocationLayout != null) {

                // Return the layouts their original positions (parameter == 0f for translationY)
                customLocationLayout.animate().translationY(0f).setDuration(500);
                trainLocationLayout.animate().translationY(0f).setDuration(500);
            }
        }
    }

    /*
     * Checks if user is returning from a recent completed trip.
     * If so, alert user to give the porter a review and returns to main screen afterwards.
     */
    private void checkTripStatus() {

        Log.d(TAG, "current trip status = " + mActivity.getmDataManager().getTripStatus());

        if (mActivity.getmDataManager().getTripStatus() == TripStatus.ENDED) {
            initReviewDialog();
        } else if (mActivity.getmDataManager().getTripStatus() == TripStatus.CANCELLED) {
            initCancellationPolicyDialog();
        }
    }

    private void initReviewDialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mActivity);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.review_custom_popup, null);
        dialogBuilder.setView(dialogView);

        // todo: update to actual stars rating
        Button reviewButton = dialogView.findViewById(R.id.trip_ended_review_button);

        if (reviewButton != null) {
            reviewButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reviewDialog.cancel();

                    // Update TripStatus
                    mActivity.getmDataManager().updateTripStatus(TripStatus.NOT_STARTED);
                }
            });
        }

        reviewDialog = dialogBuilder.create();
        if (reviewDialog.getWindow() != null) {
            reviewDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        reviewDialog.show();
    }

    private void initCancellationPolicyDialog() {
        // todo: future work
    }

    private void setUpMap() {

        Log.d(TAG, "setUpMap");
        try {
            mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.google_map);
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
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            return;
        }

        selectStartingPoint();

        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.setOnPoiClickListener(this);
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

        Log.d(TAG, "selectStartingPoint");

        try {
            if (ContextCompat.checkSelfPermission(mActivity, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(mActivity, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                return;
            }

            LocationManager locationManager = (LocationManager)mActivity.getSystemService(Context.LOCATION_SERVICE);
            Location tempLoc = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            currentLocation = new LatLng(tempLoc.getLatitude(), tempLoc.getLongitude());
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(currentLocation, MAP_CAMERA_ZOOM);
            mGoogleMap.moveCamera(update);
            mMapView.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void moveToPoiPoint(double lat, double lng) {

        try {
            currentLocation = new LatLng(lat, lng);
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

        // Move location button to bottom
        View locationButton = ((View) mMapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();

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

    private LocationCallback mLocationCallback = new LocationCallback() {
        private boolean mCameraUpdated = false;

        @Override
        public void onLocationResult(LocationResult locationResult) {

            Log.d(TAG, "onLocationResult");

            for (Location location : locationResult.getLocations()) {
                // update location
                currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                mActivity.getmDataManager().setCurrentLocation(currentLocation);

                // update camera
                if (!mCameraUpdated) {
                    updateMapCamera(currentLocation);
                    mCameraUpdated = true;
//                    CameraUpdate update = CameraUpdateFactory.newLatLngZoom(currentLocation, MAP_CAMERA_ZOOM);
//                    mGoogleMap.moveCamera(update);
//                    mMapView.setVisibility(View.VISIBLE);
                }
            }
        }
    };

    private void updateMapCamera(LatLng location) {

        // update camera
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(location, MAP_CAMERA_ZOOM);

        if (mGoogleMap != null) {
            mGoogleMap.moveCamera(update);
        }

        if (mMapView != null) {
            mMapView.setVisibility(View.VISIBLE);
        }
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
    public void onPoiClick(PointOfInterest pointOfInterest) {

        Log.d(TAG, "onPoiClick");

//        Toast.makeText(mActivity, "Clicked: " +
//                        pointOfInterest.name + "\nPlace ID:" + pointOfInterest.placeId +
//                        "\nLatitude:" + pointOfInterest.latLng.latitude +
//                        " Longitude:" + pointOfInterest.latLng.longitude,
//                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {}

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {

        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted
                    enableLocationService();
                }
                else {
                    Toast.makeText(mActivity.getApplicationContext(), R.string.user_location_access_denied, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    private void setUpViewModel() {

        // Re-created activities receive the same MyViewModel instance created by the first activity.
        model = ViewModelProviders.of(mActivity).get(MyViewModel.class);

        if (!model.getRequestSession().hasObservers()) {
            Log.d(TAG, "no observers yet");

            model.getRequestSession().observe(this, users -> {
                // update UI
            });
        }
    }

    private void setmUserMarkerTitle() {

        if (mUserMarker != null) {
            mUserMarker.hideInfoWindow();

            if (swapViewsFlag) {
                mUserMarker.setTitle("Pickup Here");
            } else {
                mUserMarker.setTitle("Dropoff Here");
            }

            mUserMarker.showInfoWindow();
        }
    }

    private boolean fieldsValid() {

        if (mPlace == null) {
            Toast.makeText(mActivity, "Please enter a destination for Pickup or Dropoff", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            /* Verify before confirmation that all inputs are entered accordingly. */
            if (trainNumField.getText().toString().length() <= 0) {
                trainNumField.setError("Enter Train Number");
                trainNumField.requestFocus();
                return false;
            } else {
                // length() > 0
                trainNumString = trainNumField.getText().toString();
            }

            if (coachNumField.getText().toString().length() <= 0) {
                coachNumField.setError("Enter Coach Number");
                coachNumField.requestFocus();
                return false;
            } else {
                // length() > 0
                coachNumString = coachNumField.getText().toString();
            }

            if (seatNumField.getText().toString().length() <= 0) {
                seatNumField.setError("Enter Seat Number");
                seatNumField.requestFocus();
                return false;
            } else {
                // length() > 0
                seatNumString = seatNumField.getText().toString();
            }

            return true;
        }
    }

//    private void setUpClickable() {
//
//        if (stationSpinner != null) {
//            // Create an ArrayAdapter using the string array and a default spinner layout
//            adapter = ArrayAdapter.createFromResource(mActivity, R.array.stations_array, android.R.layout.simple_spinner_item);
//
//            // Specify the layout to use when the list of choices appears
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//            // Apply the adapter to the spinner
//            stationSpinner.setAdapter(adapter);
//
//            // Apply an observer to the item selected
//            stationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                    userTripRequestSession = new UserTripRequestSession();
//                    userTripRequestSession.setStation(parent.getSelectedItem().toString());
//                    model.setRequestSession(userTripRequestSession);
//
//                    switch (parent.getSelectedItem().toString()) {
//
//                        case ("Utown"):
//                            moveToPoiPoint(PLACE_NUS_UTOWN_LAT, PLACE_NUS_UTOWN_LNG);
//                            break;
//                        case("Faculty of Science"):
//                            moveToPoiPoint(PLACE_NUS_FOS_LAT, PLACE_NUS_FOS_LNG);
//                            break;
//                        default:
//                            break;
//                    }
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> parent) {
//                    Log.d(TAG, "onNothingSelected");
//
//                }
//            });
//        }
//    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d(TAG, "onResume");

        setUpViews();
        setUpViewModel();
    }

    @Override
    public void onPause() {
        super.onPause();

        //stop location updates when Activity is no longer active
//        if (mFusedLocationClient != null) {
//            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
//        }
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
