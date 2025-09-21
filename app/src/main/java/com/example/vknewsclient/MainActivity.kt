package com.example.vknewsclient

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.vknewsclient.ui.theme.VkNewsClientTheme
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAuthenticationResult
import com.vk.api.sdk.auth.VKScope

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VkNewsClientTheme {
                val launcher =
                    rememberLauncherForActivityResult(contract = VK.getVKAuthActivityResultContract()) {
                        when (it) {
                            is VKAuthenticationResult.Success -> {
                                Log.d("Main activity", "Залогинился")
                            }

                            is VKAuthenticationResult.Failed -> {
                                Log.d("Main activity", "Не залогинился")
                            }
                        }
                    }

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White,
                        containerColor = Color.Blue
                    ), onClick = { launcher.launch(input = listOf(VKScope.WALL)) }

                ) {
                    Text("Test login")
                }
            }

            ActivityResultTest() // MainScreen()
        }
    }
}

