package com.example.viapatron2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.example.viapatron2.activity.AuthenticationActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "viaPatron.MainActivity";

    private ImageButton logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG, "onCreate");

        logoutButton = findViewById(R.id.main_test_log_out);

        if (logoutButton != null) {
            logoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // todo: create alert dialog popup

                    try {

                        // Log out
                        Log.d(TAG, "attempting to log out.");
                        AWSMobileClient.getInstance().signOut();

                        // go back to authentication screen
                        // todo URGENT: currently returning to blank screen. need to fix this
                        Intent authIntent = new Intent(MainActivity.this, AuthenticationActivity.class);
                        finish();
                        startActivity(authIntent);
                    } catch (Exception e) {
                        Log.d(TAG, "error on log out.");
                    }
                }
            });
        }
    }



    @Override
    public void onResume() {
        super.onResume();

        Log.d(TAG, "onResume");
//        if (AWSMobileClient.getInstance().isSignedIn()) {
//        }

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