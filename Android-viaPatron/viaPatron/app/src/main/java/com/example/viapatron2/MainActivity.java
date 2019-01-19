package com.example.viapatron2;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.example.viapatron2.activity.AuthenticationActivity;
import com.example.viapatron2.core.models.MyViewModel;
import com.example.viapatron2.fragment.ProfileFragment;

public class MainActivity extends AppCompatActivity implements ProfileFragment.MyProfileFragmentListener {

    private static final String TAG = "viaPatron.MainActivity";

    private BottomNavigationView bottomNavigation;

    private NavHostFragment navHostFragment;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG, "onCreate");

        setUpViewModel();
        setupViews();
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
        NavigationUI.setupWithNavController(bottomNavigation, navController);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_trip:
//                    toolbar.setTitle("Trip");
                    Log.d(TAG, "selected trip");

                    // todo: save fragments if exit trip ui flow halfway
                    // todo: currently these methods are not executing. Suspect due to setupWithNavController (betw botNav and NavController)
                    navController.navigate(R.id.navigation_trip);

                    return true;
                case R.id.navigation_chats:
//                    toolbar.setTitle("Chat");
                    Log.d(TAG, "selected chat");

                    navController.navigate(R.id.navigation_chats);

                    return true;
                case R.id.navigation_profile:
//                    toolbar.setTitle("Profile");
                    Log.d(TAG, "selected profile");

                    navController.navigate(R.id.navigation_profile);

                    return true;
            }
            return false;
        }
    };

    private void setUpViewModel() {

        Log.d(TAG, "setUpViewModel");

        // Create a ViewModel the first time the system calls an activity's onCreate() method.
        // Re-created activities receive the same MyViewModel instance created by the first activity.

        // Todo: set up protocol between MainActivity and the child fragments
        MyViewModel model = ViewModelProviders.of(this).get(MyViewModel.class);
        model.getRequestSession().observe(this, users -> {
            // update UI
        });
    }

//    private void setUpFragments() {
//
//        Log.d(TAG, "setUpFragments");
//
//        homeFragment = new HomeFragment();
//        chatFragment = new ChatFragment();
//        profileFragment = new ProfileFragment();
////        activeFragment = homeFragment;
//
//        try {
//            mFragmentManager.beginTransaction().add(R.id.home_fragment, homeFragment, "1").commit();
////            mFragmentManager.beginTransaction().add(R.id.chat_fragment, chatFragment, "2").hide(chatFragment).commit();
////            mFragmentManager.beginTransaction().add(R.id.profile_fragment, profileFragment, "3").hide(profileFragment).commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


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
    protected void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestroy");
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
}