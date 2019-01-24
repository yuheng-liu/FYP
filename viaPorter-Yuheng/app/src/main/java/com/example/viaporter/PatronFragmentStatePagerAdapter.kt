package com.example.viaporter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.ViewGroup

import java.util.ArrayList

abstract class PatronFragmentStatePagerAdapter(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager) {

    // Sparse array to keep track of registered fragments in memory
    private val registeredFragments = ArrayList<Fragment>()
    private var swipeEnabled: Boolean = false

    // Register the fragment when the item is instantiated
    //    @Override
    //    public Object instantiateItem(ViewGroup container, int position) {
    //        Fragment fragment = (Fragment) super.instantiateItem(container, position);
    //        registeredFragments.put(position, fragment);
    //        return fragment;
    //    }

    // Unregister when the item is inactive
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        registeredFragments.removeAt(position)
        super.destroyItem(container, position, `object`)
    }

    // Returns the fragment for the position (if instantiated)
    fun getRegisteredFragment(position: Int): Fragment {
        return registeredFragments[position]
    }

    // Our custom method that populates this Adapter with Fragments
    fun addFragments(fragment: Fragment) {
        registeredFragments.add(fragment)
    }

    override fun getItem(position: Int): Fragment {
        return registeredFragments[position]
    }

    override fun getCount(): Int {
        return registeredFragments.size
    }

    fun setPagingEnabled(enabled: Boolean) {
        this.swipeEnabled = enabled
    }
}
