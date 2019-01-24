package com.example.viaporter.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.example.viaporter.R

class ProfileFragment : Fragment() {
    private var logoutButton: FrameLayout? = null
    private var profileListener: MyProfileFragmentListener? = null

    // Define the events that the fragment will use to communicate
    interface MyProfileFragmentListener {
        fun onLogoutButtonSelected()
    }

    // Store the listener (activity) that will have events fired once the fragment is attached
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is MyProfileFragmentListener) {
            profileListener = context
        } else {
            throw ClassCastException(context!!.toString() + " must implement ProfileFragment.MyProfileFragmentListener")
        }
    }

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        Log.d(TAG, "onCreateView")

        val v = inflater.inflate(R.layout.profile_fragment, container, false)


        return v
    }

    // This event is triggered soon after onCreateView().
    // onViewCreated() is only called if the view returned from onCreateView() is non-null.
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        logoutButton = activity!!.findViewById<View>(R.id.logout_button) as FrameLayout

        setUpButtons()
    }

    // This method is called after the parent Activity's onCreate() method has completed.
    // Accessing the view hierarchy of the parent activity must be done in the onActivityCreated.
    // At this point, it is safe to search for activity View objects by their ID, for example.
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }


    //Include logout button testing
    private fun setUpButtons() {

        Log.d(TAG, "setUpButtons")

        if (logoutButton != null) {
            logoutButton!!.setOnClickListener {
                // todo: create alert dialog popup

                try {
                    Log.d(TAG, "attempting to log out.")
                    profileListener!!.onLogoutButtonSelected()

                } catch (e: Exception) {
                    Log.d(TAG, "error on log out.")
                }
            }
        }
    }

    companion object {

        private val TAG = "viaPatron.ProfFragment"
    }
}// Empty Constructor
