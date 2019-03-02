package com.example.viaporter;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.example.viaporter.fragments.ProfileFragment;
import com.example.viaporter.managers.DataManager;
import com.example.viaporter.managers.SocketManager;

import java.util.ArrayList;
import java.util.List;

import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import io.reactivex.disposables.Disposable;

import static com.example.viaporter.constants.AppConstants.PERMISSION_FINE_LOCATION;

public class MainActivity extends AppCompatActivity implements ProfileFragment.MyProfileFragmentListener {
    private static final String TAG = "viaPorter.MainActivity";

    private BottomNavigationView bottomNavigation;
    private NavHostFragment navHostFragment;
    private NavController navController;
    private List<Disposable> disposables;
    public SocketManager socketManager;
    public DataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkAppLocationPermission();

        disposables = new ArrayList<>();

        setupViews();
        setupManagers();
    }

    @Override
    protected void onDestroy() {
        // clean disposable
        for (Disposable disposable : disposables) {
            if (!disposable.isDisposed()) {
                disposable.dispose();
            }
        }
        super.onDestroy();
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

        // Pair navigation controller with the bottom navigation bar
//        NavigationUI.setupWithNavController(bottomNavigation, navController);
    }

    private void setupManagers() {
        socketManager = SocketManager.getSharedInstance();
        dataManager = DataManager.getSharedInstance();
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

//        mLocationPermissionGranted = false;
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
                case R.id.navigation_trip:
                    Log.d(TAG, "selected trip");

                    // todo: save fragments if exit trip ui flow halfway
                    // todo: currently these methods are not executing. Suspect due to setupWithNavController (betw botNav and NavController)
                    if (item.getItemId() != navController.getCurrentDestination().getId())
                        navController.navigate(R.id.navigation_trip);

                    return true;
                case R.id.navigation_jobs:
                    Log.d(TAG, "selected job");

                    if (navController.getCurrentDestination().getId() != R.id.navigation_jobs) {
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

    }

    /* Utility methods */
    public void addDisposable(Disposable disposable) {
        disposables.add(disposable);
    }
}
