package com.example.viapatron2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.viapatron2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Lim Zhiming on 14/3/19.
 */
public class LoginActivity extends AppCompatActivity {

    // configs
    private static final String TAG = "LoginActivity";

    // views
    private FrameLayout logInButton;
    private TextView registerButton;
    private EditText usernameField;
    private EditText passwordField;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onRestoreInstanceState");

        setContentView(R.layout.login_layout);
        initViews();
        initFirebase();
    }

    private void initFirebase() {
        mAuth = FirebaseAuth.getInstance();

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    Log.d(TAG, "user display name = " + user.getDisplayName());
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
    }

    private void initViews() {
        usernameField = (EditText) findViewById(R.id.login_username_field);
        passwordField = (EditText) findViewById(R.id.login_password_field);
        logInButton = (FrameLayout) findViewById(R.id.login_button);
        registerButton = (TextView) findViewById(R.id.registration_button);

        if (registerButton != null) {
            registerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String usernameString = "";
                    String passwordString = "";

                    if (usernameField != null) {
                        usernameString = usernameField.getText().toString();
                        Log.d(TAG, "username = " + usernameString);
                    }

                    if (passwordField != null) {
                        passwordString = passwordField.getText().toString();
                        Log.d(TAG, "password = " + usernameString);
                    }

                    mAuth.createUserWithEmailAndPassword(usernameString, passwordString)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(LoginActivity.this, "Registration Error", Toast.LENGTH_SHORT).show();
                                    } else {
                                        String userId = mAuth.getCurrentUser().getUid();
                                        DatabaseReference currentUserDb = FirebaseDatabase.getInstance().getReference().child("User").child("patron").child(userId);
                                        currentUserDb.setValue(true);
                                    }
                                }
                            });
                }
            });
        }

        if (logInButton != null) {
            logInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String usernameString = "";
                    String passwordString = "";

                    if (usernameField != null) {
                        usernameString = usernameField.getText().toString();
                        Log.d(TAG, "username = " + usernameString);
                    }

                    if (passwordField != null) {
                        passwordString = passwordField.getText().toString();
                        Log.d(TAG, "password = " + usernameString);
                    }

                    mAuth.signInWithEmailAndPassword(usernameString, passwordString)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(LoginActivity.this, "Sign in Error", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            });
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");

        mAuth.addAuthStateListener(firebaseAuthListener);
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
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onPause");

        mAuth.removeAuthStateListener(firebaseAuthListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}
