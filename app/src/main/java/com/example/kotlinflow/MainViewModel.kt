package com.example.kotlinflow

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {
    private val _state = MutableStateFlow<Int>(0)
    val state = _state.asStateFlow()

    fun updateState() {
        _state.value += 1
    }
}