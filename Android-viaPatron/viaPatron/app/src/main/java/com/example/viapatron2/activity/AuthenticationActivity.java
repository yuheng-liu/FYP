package com.example.viapatron2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.SignInUIOptions;
import com.amazonaws.mobile.client.UserStateDetails;
import com.example.viapatron2.MainActivity;
import com.example.viapatron2.R;

/**
 * Created by Lim Zhiming on 10/1/19.
 */

public class AuthenticationActivity extends AppCompatActivity {

    private static final String TAG = "viaPatron.AuthActivity";
    //private final String TAG = AuthenticationActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_authentication);

        if(AWSMobileClient.getInstance().getConfiguration() != null) {
            Log.d(TAG, "onCreate, getConfiguration != null");

            UserStateDetails userStateDetails = AWSMobileClient.getInstance().currentUserState();
            showSignInForUser(userStateDetails);
        } else {
            Log.d(TAG, "onCreate, getConfiguration == null");

            AWSMobileClient.getInstance().initialize(getApplicationContext(), new Callback<UserStateDetails>() {

                @Override
                public void onResult(UserStateDetails userStateDetails) {
                    showSignInForUser(userStateDetails);
                }

                @Override
                public void onError(Exception e) {
                    Log.e(TAG, e.toString());
                }
            });
        }
    }

    private void showSignInForUser(UserStateDetails userStateDetails) {
        Log.i(TAG, "showSignInForUser " + userStateDetails.getUserState().toString());

        switch (userStateDetails.getUserState()){
            case SIGNED_IN:
                Intent i = new Intent(AuthenticationActivity.this, MainActivity.class);
                startActivity(i);
                break;
            case SIGNED_OUT:
                showSignIn();
                break;
            default:
                AWSMobileClient.getInstance().signOut();
                showSignIn();
                break;
        }
    }

    // Old method
//    private void showSignIn() {
//        try {
//
//            Log.d(TAG, "showSignIn");
//
//            // todo: FINETUNE UI
//            AWSMobileClient.getInstance().showSignIn(this,
//                    SignInUIOptions.builder()
//                            .nextActivity(MainActivity.class)
//                            .logo(R.drawable.screen1_banner)
//                            .backgroundColor(R.color.colorPrimary)
//                            .canCancel(false)
//                            .build());
//        } catch (Exception e) {
//            Log.e(TAG, "onError : ", e);
//        }
//    }

    private void showSignIn() {
        try {
            AWSMobileClient.getInstance().showSignIn(this,
                    SignInUIOptions.builder().nextActivity(MainActivity.class).build(),
                    new Callback<UserStateDetails>() {
                        @Override
                        public void onResult(UserStateDetails result) {
                            Log.d(TAG, "Showing Signin UI: ");
                        }

                        @Override
                        public void onError(Exception e) {
                            Log.e(TAG, "onError: ", e);
                        }
                    });
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d(TAG, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "onResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestroy");
    }
}
