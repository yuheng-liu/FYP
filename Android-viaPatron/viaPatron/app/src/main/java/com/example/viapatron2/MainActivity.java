package com.example.viapatron2;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageButton;
import com.example.viapatron2.fragment.ChatFragment;
import com.example.viapatron2.fragment.HomeFragment;
import com.example.viapatron2.fragment.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "viaPatron.MainActivity";

    private ImageButton logoutButton;
    private BottomNavigationView bottomNavigation;
    private PatronFragmentStatePagerAdapter mFragmentStatePagerAdapter;
    private ViewPager mPager;

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

//        setUpViewPager();
        setUpFragments();
//        setUpButtons();
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

//    private void setUpViewPager() {
//
//        Log.d(TAG, "setUpViewPager");
//
//        mPager = (ViewPager) findViewById(R.id.view_pager);
//
//        mFragmentStatePagerAdapter = new PatronFragmentStatePagerAdapter(mFragmentManager) {
//            @Override
//            public void destroyItem(ViewGroup container, int position, Object object) {
//                super.destroyItem(container, position, object);
//            }
//
//            @Override
//            public Fragment getRegisteredFragment(int position) {
//                return super.getRegisteredFragment(position);
//            }
//
//            @Override
//            public void addFragments(Fragment fragment) {
//                super.addFragments(fragment);
//            }
//
//            @Override
//            public Fragment getItem(int position) {
//                return super.getItem(position);
//            }
//
//            @Override
//            public int getCount() {
//                return super.getCount();
//            }
//        };
//
//        // Disable swiping
//        mFragmentStatePagerAdapter.setPagingEnabled(false);
//
//    }


    private void setUpFragments() {

        Log.d(TAG, "setUpFragments");

        homeFragment = new HomeFragment();
        chatFragment = new ChatFragment();
        profileFragment = new ProfileFragment();
        activeFragment = homeFragment;

//        mPager.setAdapter(mFragmentStatePagerAdapter);

        try {
            mFragmentManager.beginTransaction().add(R.id.main_container, homeFragment, "1").commit();
            mFragmentManager.beginTransaction().add(R.id.main_container, chatFragment, "2").hide(chatFragment).commit();
            mFragmentManager.beginTransaction().add(R.id.main_container, profileFragment, "3").hide(profileFragment).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Include logout button testing
//    private void setUpButtons() {
//
//        logoutButton = findViewById(R.id.main_test_log_out);
//
//        if (logoutButton != null) {
//            logoutButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    // todo: create alert dialog popup
//
//                    try {
//                        // Log out
//                        Log.d(TAG, "attempting to log out.");
//                        AWSMobileClient.getInstance().signOut();
//
//                        // go back to authentication screen
//                        Intent authIntent = new Intent(MainActivity.this, AuthenticationActivity.class);
//                        finish();
//                        startActivity(authIntent);
//                    } catch (Exception e) {
//                        Log.d(TAG, "error on log out.");
//                    }
//                }
//            });
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
}