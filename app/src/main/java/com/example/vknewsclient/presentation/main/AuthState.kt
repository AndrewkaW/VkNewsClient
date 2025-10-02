package com.example.vknewsclient.presentation.main

sealed class AuthState {

    object Initial : AuthState()
    object Authorized : AuthState()
    object NotAuthorized : AuthState()

}