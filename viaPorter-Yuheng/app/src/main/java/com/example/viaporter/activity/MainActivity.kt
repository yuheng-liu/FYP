package com.example.viaporter.activity

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.amazonaws.mobile.client.AWSMobileClient
import com.example.viaporter.R
import com.example.viaporter.core.models.MyViewModel
import com.example.viaporter.fragment.ProfileFragment

class MainActivity : AppCompatActivity(), ProfileFragment.MyProfileFragmentListener {

    private var bottomNavigation: BottomNavigationView? = null

    var homeFragment: Fragment? = null
    private val chatFragment: Fragment? = null
    private val profileFragment: Fragment? = null
    private val activeFragment: Fragment? = null
    internal val mFragmentManager = supportFragmentManager
    private var navHostFragment: NavHostFragment? = null
    private var navController: NavController? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_trip -> {
                Log.d(TAG, "selected trip")
                navController!!.navigate(R.id.navigation_trip)

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_jobs -> {
                Log.d(TAG,"selected job")
                navHostFragment!!.navController.navigate(R.id.navigation_jobs)

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_chats -> {
                Log.d(TAG, "selected chat")
                navHostFragment!!.navController.navigate(R.id.navigation_chats)

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profile -> {
                Log.d(TAG, "selected profile")
                navHostFragment!!.navController.navigate(R.id.navigation_profile)

                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.i(TAG, "onCreate")

        setupViews()
    }

    private fun setupViews() {
        Log.d(TAG, "setupViews")

        // Initialise the bottom navigationbar_items bar
        bottomNavigation = findViewById(R.id.bot_navigation_view)
        bottomNavigation!!.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        // Initialise a navHostFragment containing the navigationbar_items graph chart
        navHostFragment = NavHostFragment.create(R.navigation.nav_graph)

        // Initialise a navigationbar_items controller for controlling navigationbar_items
        navController = Navigation.findNavController(findViewById(R.id.my_nav_host_fragment))

        // Pair navigationbar_items controller with the bottom navigationbar_items bar
        NavigationUI.setupWithNavController(bottomNavigation!!, navController!!)
    }

    public override fun onResume() {
        super.onResume()

        Log.d(TAG, "onResume")
    }

    public override fun onPause() {
        super.onPause()

        Log.d(TAG, "onPause")
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.d(TAG, "onDestroy")
    }

    /*
     * Method from ProfileFragment: Used to activate logout functionality.
     * Upon logging out, MainActivity will be destroyed
     */
    override fun onLogoutButtonSelected() {

        Log.d(TAG, "onLogoutButtonSelected")

        try {
            AWSMobileClient.getInstance().signOut()

            // Tips: Intents should be created and activated within activities
            // go back to authentication screen
            val authIntent = Intent(this, AuthenticationActivity::class.java)
            this.finish()
            startActivity(authIntent)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        Log.d(TAG, "onRestoreInstanceState")
    }

    /*
     * Called to retrieve per-instance state from an activity before being killed
     * so that the state can be restored in onCreate(Bundle) or
     * onRestoreInstanceState(Bundle) (the Bundle populated by this method will be passed to both).
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        Log.d(TAG, "onSaveInstanceState")

        //        mFragmentManager.putFragment(outState, "newFragment", new TripRequestFragment());

    }

    override fun onBackPressed() {
        super.onBackPressed()

        Log.d(TAG, "onBackPressed")
    }

    companion object {

        private val TAG = "viaPatron.MainActivity"
    }
}