package com.example.kotlinflow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _sharedFlow = MutableSharedFlow<Int>(3)
    val sharedFlow = _sharedFlow.asSharedFlow()

    init {
        squareNumber(5)

        viewModelScope.launch {
            sharedFlow.collect {
                delay(1000)
                println("collector 1: $it")
            }
        }

        viewModelScope.launch {
            delay(1000)
            sharedFlow.collect {
                println("collector 2: $it")
            }
        }

        viewModelScope.launch {
            delay(1000)
            sharedFlow.collect {
                println("collector 3: $it")
            }
        }
    }

    fun squareNumber(number: Int) {
        viewModelScope.launch {
            println("staring emission...")
            _sharedFlow.emit(number * 2)
            _sharedFlow.emit(number * 10)
            _sharedFlow.emit(number * number)
        }
    }

}