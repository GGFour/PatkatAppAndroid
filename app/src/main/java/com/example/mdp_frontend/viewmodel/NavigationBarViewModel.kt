package com.example.mdp_frontend.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel

class NavigationBarViewModel: ViewModel() {
    var selected: Int by mutableStateOf(0)

    fun updateSelected (newIndex: Int) {
        selected = newIndex
    }
}