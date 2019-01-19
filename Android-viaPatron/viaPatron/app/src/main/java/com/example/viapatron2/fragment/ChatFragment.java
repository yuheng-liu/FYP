package com.example.viapatron2.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import com.example.viapatron2.R;
import com.example.viapatron2.core.models.MyViewModel;
import com.example.viapatron2.core.models.UserTripRequestSession;

public class ChatFragment extends Fragment {

    private static final String TAG = "viaPatron.ChatFragment";

    private NavController navController;
    private MyViewModel model;
    private UserTripRequestSession userTripRequestSession;

    public ChatFragment() {
        // Empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView");

        View v = inflater.inflate(R.layout.chat_fragment, container, false);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(getActivity(), R.id.my_nav_host_fragment);
        setUpViewModel();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void setUpViewModel() {

        Log.d(TAG, "setUpViewModel");

        // Re-created activities receive the same MyViewModel instance created by the first activity.
        model = ViewModelProviders.of(getActivity()).get(MyViewModel.class);

        if (!model.getRequestSession().hasObservers()) {
            Log.d(TAG, "no observers yet");

            model.getRequestSession().observe(this, users -> {
                // update UI
            });
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        Log.d(TAG, "onStart");
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

        // debug
        NavDestination currentDest = navController.getCurrentDestination();
        if (currentDest != null) {
            Log.d(TAG, "currentDest.getId = " + currentDest.getId());
            Log.d(TAG, "currentDest.getLabel = " + currentDest.getLabel());
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        Log.d(TAG, "onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestroy");
    }
}
