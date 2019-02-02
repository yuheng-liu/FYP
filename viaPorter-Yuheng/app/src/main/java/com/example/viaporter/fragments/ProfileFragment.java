package com.example.viaporter.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.example.viaporter.R;

public class ProfileFragment extends Fragment {
    private static final String TAG = "viaPatron.ProfFragment";

    private FrameLayout logoutButton;
    private MyProfileFragmentListener profileListener;

    // Define the events that the fragment will use to communicate
    public interface MyProfileFragmentListener {
        void onLogoutButtonSelected();
    }

    public ProfileFragment () {
        // Empty Constructor
    }

    // Store the listener (activity) that will have events fired once the fragment is attached
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MyProfileFragmentListener) {
            profileListener = (MyProfileFragmentListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement ProfileFragment.MyProfileFragmentListener");
        }
    }

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");

        return inflater.inflate(R.layout.profile_fragment, container, false);
    }

    // This event is triggered soon after onCreateView().
    // onViewCreated() is only called if the view returned from onCreateView() is non-null.
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        logoutButton = (FrameLayout) getActivity().findViewById(R.id.logout_button);

        setUpButtons();
    }

    //Include logout button testing
    private void setUpButtons() {

        Log.d(TAG, "setUpButtons");

        if (logoutButton != null) {
            logoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // todo: create alert dialog popup

                    try {
                        Log.d(TAG, "attempting to log out.");
                        profileListener.onLogoutButtonSelected();

                    } catch (Exception e) {
                        Log.d(TAG, "error on log out.");
                    }
                }
            });
        }
    }
}
