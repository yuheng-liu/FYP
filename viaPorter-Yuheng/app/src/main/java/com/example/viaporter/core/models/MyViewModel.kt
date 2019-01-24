package com.example.viaporter.core.models

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class MyViewModel : ViewModel() {

    private var userInfo: MutableLiveData<List<String>>? = null

    fun getUserInfo(): LiveData<List<String>> {
        return userInfo ?: MutableLiveData()
    }

    private fun loadUserInfo() {
        // Todo: Do an asynchronous operation to fetch users.
    }
}
