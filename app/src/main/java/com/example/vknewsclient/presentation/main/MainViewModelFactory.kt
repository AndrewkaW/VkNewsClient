package com.example.vknewsclient.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vk.id.VKID

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val vkid: VKID) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(vkid = vkid) as T
    }
}