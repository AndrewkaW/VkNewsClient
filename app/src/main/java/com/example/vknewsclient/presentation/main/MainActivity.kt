package com.example.vknewsclient.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vknewsclient.ui.theme.VkNewsClientTheme
import com.vk.id.VKID
import java.io.FileInputStream
import java.util.Properties

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VkNewsClientTheme {
                val viewModel: MainViewModel = viewModel(factory = MainViewModelFactory(VKID.instance))
                val authState = viewModel.authState.observeAsState(AuthState.Initial)

                when (authState.value) {
                    AuthState.Authorized -> {
                        MainScreen()
                    }

                    AuthState.NotAuthorized -> {
                        LoginScreen {
                            VKID.instance.authorize(
                                this@MainActivity,
                                viewModel.vkAuthCallback
                            )
                        }
                    }

                    AuthState.Initial -> {}
                }
            }
        }
    }
}
