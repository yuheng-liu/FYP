package com.example.viaporter.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.amazonaws.mobile.client.*
import com.example.viaporter.R

/**
 * Created by Lim Zhiming on 10/1/19.
 */

class AuthenticationActivity : AppCompatActivity() {
    //private final String TAG = AuthenticationActivity.class.getSimpleName();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "onCreate")
        setContentView(R.layout.activity_authentication)

        if (AWSMobileClient.getInstance().configuration != null) {
            Log.d(TAG, "onCreate, getConfiguration != null")

            // For users who logged out after signing in
            val userStateDetails = AWSMobileClient.getInstance().currentUserState()
            showSignInForUser(userStateDetails)
        } else {
            Log.d(TAG, "onCreate, getConfiguration == null")

            // First time signing in
            AWSMobileClient.getInstance().initialize(applicationContext, object : Callback<UserStateDetails> {

                override fun onResult(userStateDetails: UserStateDetails) {
                    showSignInForUser(userStateDetails)
                }

                override fun onError(e: Exception) {
                    Log.e(TAG, e.toString())
                }
            })
        }
    }

    private fun showSignInForUser(userStateDetails: UserStateDetails) {
        Log.i(TAG, "showSignInForUser " + userStateDetails.userState.toString())

        when (userStateDetails.userState) {
            UserState.SIGNED_IN -> {
                val i = Intent(this@AuthenticationActivity, MainActivity::class.java)
                startActivity(i)
            }
            UserState.SIGNED_OUT -> showSignIn()
            else -> {
                AWSMobileClient.getInstance().signOut()
                showSignIn()
            }
        }
    }

    /*
     * A private method adapted from AWS API to build the UI for us
     * todo: fine tune the UI to mimic actual viaPatron designs
     */
    private fun showSignIn() {

        Log.d(TAG, "showSignIn")

        try {
            AWSMobileClient.getInstance().showSignIn(this,
                SignInUIOptions.builder()
                    .nextActivity(MainActivity::class.java)
                    .logo(R.drawable.screen1_banner)
                    .backgroundColor(R.color.colorPrimary)
                    .canCancel(false)
                    .build(),
                object : Callback<UserStateDetails> {
                    override fun onResult(result: UserStateDetails) {
                        Log.d(TAG, "Showing Signin UI: ")
                    }

                    override fun onError(e: Exception) {
                        Log.e(TAG, "onError: ", e)
                    }
                })
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
        }

    }

    override fun onPause() {
        super.onPause()

        Log.d(TAG, "onPause")
    }

    override fun onResume() {
        super.onResume()

        Log.d(TAG, "onResume")
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.d(TAG, "onDestroy")
    }

    companion object {

        private val TAG = "viaPorter.AuthActivity"
    }
}
