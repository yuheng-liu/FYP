package com.example.viapatron2.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.example.viapatron2.R;
import com.example.viapatron2.app.constants.AppConstants;
import com.example.viapatron2.app.managers.SocketManager;
import com.example.viapatron2.core.models.MyViewModel;
import com.example.viapatron2.fragment.ProfileFragment;
import com.example.viapatron2.service.ViaPatronWorkerService;
import io.reactivex.disposables.Disposable;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ProfileFragment.MyProfileFragmentListener {

    private static final String TAG = "viaPatron.MainActivity";
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = AppConstants.PERMISSION_FINE_LOCATION;

    private ViaPatronWorkerService mService;
    private boolean mServiceBounded = false, mServiceConnected = false;

    private SocketManager mSocketManager;
    private List<Disposable> disposables;

    private BottomNavigationView bottomNavigation;
    private NavHostFragment navHostFragment;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG, "onCreate");

        disposables = new ArrayList<>();

        checkAppLocationPermission();
        setUpViewModel();
        setupViews();

        mSocketManager = new SocketManager();
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
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            // todo: disable location services upon permission denial
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
//        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    mLocationPermissionGranted = true;
                }
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Bind to LocalService
        Intent intent = new Intent(this, ViaPatronWorkerService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            ViaPatronWorkerService.LocalBinder binder = (ViaPatronWorkerService.LocalBinder) iBinder;
            mService = binder.getService();
            if (!mServiceConnected) {
                MainActivity.this.onServiceConnected();
            }
            mServiceBounded = true;
            mServiceConnected = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mServiceBounded = false;
        }
    };

    protected void onServiceConnected() {

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

        // todo: implement custom navigator here to fix fragment back stack reset bug
        //navController.getNavigatorProvider();

        // Pair navigation controller with the bottom navigation bar
        NavigationUI.setupWithNavController(bottomNavigation, navController);
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
                    navController.navigate(R.id.navigation_trip);

                    return true;
                case R.id.navigation_chats:
                    Log.d(TAG, "selected chat");

                    navController.navigate(R.id.navigation_chats);

                    return true;
                case R.id.navigation_profile:
                    Log.d(TAG, "selected profile");

                    navController.navigate(R.id.navigation_profile);

                    return true;
            }
            return false;
        }
    };

    private void setUpViewModel() {

        // Create a ViewModel the first time the system calls an activity's onCreate() method.
        // Re-created activities receive the same MyViewModel instance created by the first activity.

        // Todo: set up protocol between MainActivity and the child fragments
        MyViewModel model = ViewModelProviders.of(this).get(MyViewModel.class);
        model.getRequestSession().observe(this, users -> {
            // update UI
        });
    }

    /* Utility methods */
    public void addDisposable(Disposable disposable) {
        disposables.add(disposable);
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d(TAG, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();

        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(mConnection);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestroy");

        // clean disposable
        for (Disposable disposable : disposables) {
            if (!disposable.isDisposed()) {
                disposable.dispose();
            }
        }
        super.onDestroy();
    }

    /*
     * Method from ProfileFragment: Used to activate logout functionality.
     * Upon logging out, MainActivity will be destroyed
     */
    @Override
    public void onLogoutButtonSelected() {

        Log.d(TAG, "onLogoutButtonSelected");

        try {
            AWSMobileClient.getInstance().signOut();

            // Tips: Intents should be created and activated within activities
            // go back to authentication screen
            Intent authIntent = new Intent(this, AuthenticationActivity.class);
            this.finish();
            startActivity(authIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        Log.d(TAG, "onRestoreInstanceState");
    }

    /*
     * Called to retrieve per-instance state from an activity before being killed
     * so that the state can be restored in onCreate(Bundle) or
     * onRestoreInstanceState(Bundle) (the Bundle populated by this method will be passed to both).
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.d(TAG, "onSaveInstanceState");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Log.d(TAG, "onBackPressed");
    }

    public ViaPatronWorkerService getService() {
        return mService;
    }

    public SocketManager getmSocketManager() {
        return mSocketManager;
    }
}