package com.example.viapatron2;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.example.viapatron2.activity.AuthenticationActivity;
import com.example.viapatron2.core.models.MyViewModel;
import com.example.viapatron2.fragment.ChatFragment;
import com.example.viapatron2.fragment.HomeFragment;
import com.example.viapatron2.fragment.ProfileFragment;

public class MainActivity extends AppCompatActivity implements ProfileFragment.MyProfileFragmentListener {

    private static final String TAG = "viaPatron.MainActivity";

    private BottomNavigationView bottomNavigation;

    public Fragment homeFragment;
    private Fragment chatFragment;
    private Fragment profileFragment;
    private Fragment activeFragment;
    final FragmentManager mFragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG, "onCreate");

        bottomNavigation = (BottomNavigationView) findViewById(R.id.bot_navigation_view);
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        setUpViewModel();
        setUpFragments();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_trip:
//                    toolbar.setTitle("Trip");
                    Log.d(TAG, "selected trip");

                    mFragmentManager.beginTransaction().hide(activeFragment).show(homeFragment).commit();
                    activeFragment = homeFragment;
                    return true;
                case R.id.navigation_chats:
//                    toolbar.setTitle("Chat");
                    Log.d(TAG, "selected chat");

                    mFragmentManager.beginTransaction().hide(activeFragment).show(chatFragment).commit();
                    activeFragment = chatFragment;
                    return true;
                case R.id.navigation_profile:
//                    toolbar.setTitle("Profile");
                    Log.d(TAG, "selected profile");

                    mFragmentManager.beginTransaction().hide(activeFragment).show(profileFragment).commit();
                    activeFragment = profileFragment;
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
        model.getUserInfo().observe(this, users -> {
            // update UI
        });
    }

    private void setUpFragments() {

        Log.d(TAG, "setUpFragments");

        homeFragment = new HomeFragment();
        chatFragment = new ChatFragment();
        profileFragment = new ProfileFragment();
        activeFragment = homeFragment;

        try {
            mFragmentManager.beginTransaction().add(R.id.main_container, homeFragment, "1").commit();
            mFragmentManager.beginTransaction().add(R.id.main_container, chatFragment, "2").hide(chatFragment).commit();
            mFragmentManager.beginTransaction().add(R.id.main_container, profileFragment, "3").hide(profileFragment).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
}