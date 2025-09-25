package com.example.vknewsclient

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vknewsclient.ui.theme.AuthState
import com.vk.id.AccessToken
import com.vk.id.VKIDAuthFail
import com.vk.id.auth.VKIDAuthCallback

class MainViewModel() : ViewModel() {

    private val _authState = MutableLiveData<AuthState>(AuthState.Initial)
    val authState: LiveData<AuthState> = _authState

    private var token : String? = null

    init {
        _authState.value = if (!token.isNullOrBlank()) {
            AuthState.Authorized
        } else {
            AuthState.NotAuthorized
        }
    }

    val vkAuthCallback = object : VKIDAuthCallback {
        override fun onAuth(accessToken: AccessToken) {
            token = accessToken.token
            _authState.value = AuthState.Authorized
            Log.i("MainViewModel", "Auth Access : ${accessToken.userData}")
        }

        override fun onFail(fail: VKIDAuthFail) {
            _authState.value = AuthState.NotAuthorized
            Log.e("MainViewModel", "Auth Failed : ${fail.description}")
        }
    }
}