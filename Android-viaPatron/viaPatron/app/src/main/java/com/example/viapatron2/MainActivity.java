package com.example.viapatron2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Log.i(TAG, "onCreate");

        // Create first view
        SignInFragment signInFragment = (SignInFragment) getSupportFragmentManager().findFragmentByTag("SignInFragment");

        // Check if first view is already created
        if (signInFragment != null) {
            return;
        } else {
            SignInFragment newSignInFragment = new SignInFragment();

            FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
            // Add tag for searching purposes
            ft.replace(R.id.activity_main, newSignInFragment, "SignInFragment");
            ft.commit();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

}