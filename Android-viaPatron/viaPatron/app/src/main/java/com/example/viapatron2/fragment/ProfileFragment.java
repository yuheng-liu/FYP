package com.example.viapatron2.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.example.viapatron2.R;
import com.example.viapatron2.activity.AuthenticationActivity;

public class ProfileFragment extends Fragment {

    private static final String TAG = "ProfileFragment";
    private Button logoutButton;

    public ProfileFragment () {
        // Empty Constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView");

        View v = inflater.inflate(R.layout.profile_fragment, container, false);
        logoutButton = (Button) getActivity().findViewById(R.id.logout_button);

        setUpButtons();

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    //Include logout button testing
    private void setUpButtons() {

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
                        Intent authIntent = new Intent(getActivity(), AuthenticationActivity.class);
                        getActivity().finish();
                        startActivity(authIntent);
                    } catch (Exception e) {
                        Log.d(TAG, "error on log out.");
                    }
                }
            });
        }
    }
}
