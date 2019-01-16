package com.example.viapatron2.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.viapatron2.R;

public class ProfileFragment extends Fragment {

    private static final String TAG = "viaPatron.ProfFragment";
    private Button logoutButton;
    private MyProfileFragmentListener profileListener;

    // Define the events that the fragment will use to communicate
    public interface MyProfileFragmentListener {
        public void onLogoutButtonSelected();
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

        View v = inflater.inflate(R.layout.profile_fragment, container, false);


        return v;
    }

    // This event is triggered soon after onCreateView().
    // onViewCreated() is only called if the view returned from onCreateView() is non-null.
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        logoutButton = (Button) getActivity().findViewById(R.id.logout_button);

        setUpButtons();
    }

    // This method is called after the parent Activity's onCreate() method has completed.
    // Accessing the view hierarchy of the parent activity must be done in the onActivityCreated.
    // At this point, it is safe to search for activity View objects by their ID, for example.
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
