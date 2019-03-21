package com.example.viaporter;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.example.viaporter.fragments.ProfileFragment;
import com.example.viaporter.managers.DataManager;
import com.example.viaporter.managers.DialogManager;
import com.example.viaporter.managers.SocketManager;
import com.example.viaporter.models.TripStatus;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import io.reactivex.disposables.Disposable;

import static com.example.viaporter.constants.AppConstants.PERMISSION_FINE_LOCATION;

public class MainActivity extends AppCompatActivity implements ProfileFragment.MyProfileFragmentListener{
    private static final String TAG = "viaPorter.MainActivity";

    private BottomNavigationView bottomNavigation;
    private NavHostFragment navHostFragment;
    private List<Disposable> disposables;
    public NavController navController;
    public SocketManager socketManager;
    public DataManager dataManager;
    public DialogManager dialogManager;
    public DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkAppLocationPermission();

        disposables = new ArrayList<>();

        setupViews();
        setupManagers();
    }

    private void setupViews() {
        Log.d(TAG, "setupViews");

        // Initialise the bottom navigation bar
        bottomNavigation = (BottomNavigationView) findViewById(R.id.bot_navigation_view);
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // Initialise a navHostFragment containing the navigation graph chart
        navHostFragment = NavHostFragment.create(R.navigation.nav_graph);

        // Initialise a navigation controller for controlling navigation
        navController = Navigation.findNavController(findViewById(R.id.my_nav_host_fragment));
    }

    private void setupManagers() {
        socketManager = SocketManager.getSharedInstance();
        dataManager = DataManager.getSharedInstance();
        dialogManager = DialogManager.getSharedInstance();
        dialogManager.setMainActivity(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();
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

        switch (requestCode) {
            case PERMISSION_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    mLocationPermissionGranted = true;
                }
            }
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_jobs:
                    Log.d(TAG, "selected job");

                    if (dataManager.getTripStatus() == TripStatus.IN_PROGRESS){
                        navController.popBackStack(R.id.navigation_trip_confirmed, false);
                    } else {
                        if (navController.getCurrentDestination().getId() != R.id.navigation_jobs)
                            navController.navigate(R.id.navigation_jobs);
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
//            AWSMobileClient.getInstance().signOut();
            FirebaseAuth.getInstance().signOut();

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
            dialogManager.showTripConfirmedCancelTrip();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        // clean disposable
        for (Disposable disposable : disposables) {
            if (!disposable.isDisposed()) {
                disposable.dispose();
            }
        }

//        mDatabase.child("chats").removeValue();
        super.onDestroy();
    }

    /* Utility methods */
    public void addDisposable(Disposable disposable) {
        disposables.add(disposable);
    }
}
