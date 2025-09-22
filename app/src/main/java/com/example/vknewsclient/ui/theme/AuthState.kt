package com.example.vknewsclient.ui.theme

sealed class AuthState {

    object Initial : AuthState()
    object Authorized : AuthState()
    object NotAuthorized : AuthState()

}