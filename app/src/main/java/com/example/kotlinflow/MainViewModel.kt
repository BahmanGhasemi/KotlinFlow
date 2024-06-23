package com.example.kotlinflow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    val countDownTimer = flow<Int> {
        val startValue = 10
        var currentValue = startValue
//        emit(startValue)
        while (currentValue > 0) {
            delay(1000L)
            currentValue--
            emit(currentValue)
        }
    }

    init {
//        testCollect()
        testCollectLatest()
    }

    private fun testCollect() {
        viewModelScope.launch {
            countDownTimer.collect {
                println("timer is: $it")
            }
        }
    }

    private fun testCollectLatest() {
        viewModelScope.launch {
            countDownTimer.collectLatest {
                delay(1500L)
                println("timer is: $it")
            }
        }
    }

}