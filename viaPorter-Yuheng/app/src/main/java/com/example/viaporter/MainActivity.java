package com.example.viaporter;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.MenuItem;

import com.example.viaporter.fragments.ProfileFragment;
import com.example.viaporter.managers.DataManager;
import com.example.viaporter.managers.DialogManager;
import com.example.viaporter.managers.FirebaseAdaptersManager;
import com.example.viaporter.managers.FirebaseDatabaseManager;
import com.example.viaporter.models.TripState;
import com.google.firebase.auth.FirebaseAuth;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import static com.example.viaporter.constants.AppConstants.PERMISSION_FINE_LOCATION;

public class MainActivity extends AppCompatActivity implements ProfileFragment.MyProfileFragmentListener{
    private static final String TAG = "viaPorter.MainActivity";

    private BottomNavigationView bottomNavigation;
    private NavController navController;
    private DataManager dataManager;
    private DialogManager dialogManager;
    private FirebaseDatabaseManager firebaseDatabaseManager;
    private FirebaseAdaptersManager firebaseAdaptersManager;

    // getters
    public BottomNavigationView getBottomNavigationView() { return bottomNavigation; }
    public DataManager getDataManager() { return dataManager; }
    public DialogManager getDialogManager() { return dialogManager; }
    public FirebaseDatabaseManager getFirebaseDatabaseManager() { return firebaseDatabaseManager; }
    public FirebaseAdaptersManager getFirebaseAdaptersManager() { return firebaseAdaptersManager; }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkAppLocationPermission();

        setupManagers();
        setupViews();
    }

    private void setupViews() {
        Log.d(TAG, "setupViews");

        initBottomNavigationBar();
        navController = Navigation.findNavController(findViewById(R.id.my_nav_host_fragment));
    }

    private void setupManagers() {
        dataManager = DataManager.getSharedInstance();
        dialogManager = DialogManager.getSharedInstance();
        dialogManager.setMainActivity(this);
        firebaseDatabaseManager = FirebaseDatabaseManager.getSharedInstance();
        firebaseDatabaseManager.setMainActivity(this);
        firebaseAdaptersManager = FirebaseAdaptersManager.getSharedInstance();
        firebaseAdaptersManager.setMainActivity(this);
    }

    private void initBottomNavigationBar() {

        bottomNavigation = findViewById(R.id.bot_navigation_view);
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        ColorStateList mColorStateList = new ColorStateList(
                new int[][]{
                        new int[]{-android.R.attr.state_enabled}, // disabled
                        new int[]{android.R.attr.state_checked}, // checked
                        new int[]{android.R.attr.state_enabled}  // pressed
                },
                new int[]{
                        Color.LTGRAY,
                        Color.BLACK,
                        Color.BLACK});

        bottomNavigation.setItemIconTintList(mColorStateList);
        bottomNavigation.setItemTextColor(mColorStateList);

        // if patron has not started a trip with a porter
        if (dataManager.getTripState() != TripState.PATRON_STARTED || dataManager.getTripState() != TripState.IN_PROGRESS) {

            // retrieve chat button and disable it
            if (bottomNavigation.getMenu().getItem(1) != null) {
                bottomNavigation.getMenu().getItem(1).setEnabled(false);
                // for testing
//                bottomNavigation.getMenu().getItem(1).setEnabled(true);
            }
        } else {
            if (bottomNavigation.getMenu().getItem(1) != null) {
                bottomNavigation.getMenu().getItem(1).setEnabled(true);
            }
        }
    }

    // Google Maps
    private void checkAppLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "user has granted location permissions");
        } else {
            Log.d(TAG, "user has not granted location permissions");
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_FINE_LOCATION);
            // todo: disable location services upon permission denial
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case PERMISSION_FINE_LOCATION: {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
////                    mLocationPermissionGranted = true;
//                }
//            }
//        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_jobs:
                    Log.d(TAG, "selected job");

                    if (dataManager.getTripState() == TripState.PATRON_STARTED
                        || dataManager.getTripState() == TripState.IN_PROGRESS
                        || dataManager.getTripState() == TripState.PATRON_ARRIVED){
                        navController.popBackStack(R.id.navigation_trip_confirmed, false);
                    } else {
                        if (navController.getCurrentDestination().getId() != R.id.navigation_jobs) {
                            navController.navigate(R.id.navigation_jobs);
                        }
                    }

                    return true;
                case R.id.navigation_chats:
                    Log.d(TAG, "selected chat");

                    if (item.getItemId() != navController.getCurrentDestination().getId())
                        navController.navigate(R.id.navigation_chats);

                    return true;
                case R.id.navigation_profile:
                    Log.d(TAG, "selected profile");

                    if (item.getItemId() != navController.getCurrentDestination().getId())
                        navController.navigate(R.id.navigation_profile);

                    return true;
            }
            return false;
        }
    };

    @Override
    public void onLogoutButtonSelected() {
        Log.d(TAG, "onLogoutButtonSelected");

        try {
            FirebaseAuth.getInstance().signOut();
            firebaseDatabaseManager.porterDatabase
                    .child(firebaseDatabaseManager.myUid)
                    .removeValue();

            // Tips: Intents should be created and activated within activities
            // go back to authentication screen
            Intent authIntent = new Intent(this, LoginActivity.class);
            this.finish();
            startActivity(authIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        if (navController.getCurrentDestination().getId() == R.id.navigation_trip_confirmed){
            dialogManager.showTripConfirmedCancelTripDialog();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
