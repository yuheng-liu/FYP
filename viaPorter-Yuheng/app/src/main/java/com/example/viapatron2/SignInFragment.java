package com.example.viapatron2;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;


public class SignInFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "SignInFragment";
    private EditText nameField;
    private EditText passwordField;
    private FrameLayout layoutSignIn;
    private EditText forgotPwField;

    // Testing
    private class Patron {
        private String patronName;
        private String patronPassword;
    }

    public Patron testPatron = new Patron();


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView");

        View v = inflater.inflate(R.layout.sign_in_fragment, container, false);


        nameField = (EditText) v.findViewById(R.id.screen1_name_field);
        passwordField = (EditText) v.findViewById(R.id.screen1_password_field);
        initFieldListeners(nameField, passwordField);

        layoutSignIn = (FrameLayout) v.findViewById(R.id.screen1_layout_signIn);
        if (layoutSignIn != null) {
            layoutSignIn.setOnClickListener(this);
        }

        forgotPwField = (EditText) v.findViewById(R.id.screen1_forgot_password);
        if (forgotPwField != null) {
            forgotPwField.setOnClickListener(this);
        }

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "onActivityCreated");

    }

    @Override
    public void onClick(View v) {

        Log.d(TAG, "onClick");

        switch (v.getId()) {

            case R.id.screen1_layout_signIn :
                Log.d(TAG, "layout_signIn");

                if (testPatron != null) {
                    if (testPatron.patronName != null && testPatron.patronPassword != null) {
                        Log.d(TAG, "patron name = " + testPatron.patronName + ", pw = " + testPatron.patronPassword);
                    }
                }
                break;

            case R.id.screen1_forgot_password :
                Log.d(TAG, "forgot_password");

                // todo: create new fragment for forgot password page


                break;

            // todo: include case for new registration
            // case R.id.screen1_sign_up_here :
        }
    }

    // initialise listeners for text fields
    private void initFieldListeners(EditText nameField, EditText passwordField) {

        if (nameField != null) {
            nameField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    Log.d(TAG, "onEditorAction_nameField");

                    if (actionId == EditorInfo.IME_ACTION_NEXT) {
                        testPatron.patronName = nameField.getText().toString();
                    }

                    // todo : save name if user goes back to edit name only

                    return false;
                }
            });
        } else {
            Log.d(TAG, "nameField not initialised.");
        }

        if (passwordField != null) {
            passwordField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    Log.d(TAG, "onEditorAction_passwordField");

                    boolean handled = false;

                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        testPatron.patronPassword = passwordField.getText().toString();

                        // Hide keyboard after pressing Done
                        InputMethodManager inputMethodManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                        handled = true;
                    }

                    return handled;
                }
            });
        } else {
            Log.d(TAG, "passwordField not initialised.");
        }
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
}
