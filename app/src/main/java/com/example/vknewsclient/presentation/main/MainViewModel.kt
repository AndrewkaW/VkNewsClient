package com.example.vknewsclient.presentation.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vk.id.AccessToken
import com.vk.id.VKID
import com.vk.id.VKIDAuthFail
import com.vk.id.auth.VKIDAuthCallback

class MainViewModel(vkid: VKID) : ViewModel() {

    private val _authState = MutableLiveData<AuthState>(AuthState.Initial)
    val authState: LiveData<AuthState> = _authState

    init {
        val token = vkid.accessToken
        val loggedIn = token != null
        _authState.value = if (loggedIn) {
            AuthState.Authorized
        } else {
            AuthState.NotAuthorized
        }
    }

    val vkAuthCallback = object : VKIDAuthCallback {
        override fun onAuth(accessToken: AccessToken) {
            _authState.value = AuthState.Authorized
            Log.i("MainViewModel", "Auth Access : ${accessToken.userData}")
        }

        override fun onFail(fail: VKIDAuthFail) {
            _authState.value = AuthState.NotAuthorized
            Log.e("MainViewModel", "Auth Failed : ${fail.description}")
        }
    }
}