package com.example.kotlinflow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.flow.merge

class ZipViewModel : ViewModel() {

    val flow1 = (1..10).asFlow().onEach { delay(300L) }
    val flow2 = (10..20).asFlow().onEach { delay(500L) }

    init {
//        zip()
        merge()
    }

    private fun zip() {
        flow1.zip(flow2) { number1, number2 ->
            println("result: [$number1, $number2]\n")
        }.launchIn(viewModelScope)
    }

    private fun merge() {
        merge(flow1, flow2).onEach {
            println("result: $it\n")
        }.launchIn(viewModelScope)
    }
}