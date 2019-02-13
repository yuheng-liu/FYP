package com.example.viaporter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "viaPorter.MainActivity";

    private BottomNavigationView bottomNavigation;
    private NavHostFragment navHostFragment;
    private NavController navController;
    private List<Disposable> disposables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        // todo: implement custom navigator here to fix fragment back stack reset bug
        //navController.getNavigatorProvider();

        // Pair navigation controller with the bottom navigation bar
        NavigationUI.setupWithNavController(bottomNavigation, navController);
    }

    private void setupManagers() {

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
                case R.id.navigation_jobs:
                    Log.d(TAG, "selected job");

                    navController.navigate(R.id.navigation_jobs);

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

    /* Utility methods */
    public void addDisposable(Disposable disposable) {
        disposables.add(disposable);
    }
}
