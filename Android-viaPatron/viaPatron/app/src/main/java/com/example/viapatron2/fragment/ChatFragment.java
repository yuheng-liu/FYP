package com.example.viapatron2.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.example.viapatron2.R;

public class ChatFragment extends Fragment {

    private static final String TAG = "ChatFragment";
    private LinearLayout fragmentContainer;

    public ChatFragment() {
        // Empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView");

        View v = inflater.inflate(R.layout.chat_fragment, container, false);
//        fragmentContainer = (LinearLayout) getActivity().findViewById(R.id.chat_fragment);

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

//    /**
//     * Called when a fragment will be displayed
//     */
//    public void willBeDisplayed() {
//        // Do what you want here, for example animate the content
//        if (fragmentContainer != null) {
//            Animation fadeIn = AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_in);
//            fragmentContainer.startAnimation(fadeIn);
//        }
//    }
//
//    /**
//     * Called when a fragment will be hidden
//     */
//    public void willBeHidden() {
//        if (fragmentContainer != null) {
//            Animation fadeOut = AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_out);
//            fragmentContainer.startAnimation(fadeOut);
//        }
//    }
}
