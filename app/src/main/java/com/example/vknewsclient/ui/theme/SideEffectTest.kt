package com.example.vknewsclient.ui.theme

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun SideEffectTest(number: MyNumber) {
    LazyColumn {
        repeat(5) {
            item {
                Text(text = "Number: ${number.a}")
            }
        }
    }

}

data class MyNumber(var a: Int)
