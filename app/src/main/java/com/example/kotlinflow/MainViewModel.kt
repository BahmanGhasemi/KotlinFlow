package com.example.kotlinflow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    val timerFlow = flow<Int> {
        val startTimer = 10
        var currentTimer = startTimer
        emit(currentTimer)
        while (currentTimer > 0) {
            delay(1000L)
            currentTimer--
            emit(currentTimer)
        }
    }

    init {
//        useFilter()
//        useMap()
//        useOnEach()
//        useCount()
//        useReduce()
//        useFold()
//        useFlatMapConcat()
//        useFlatMapLatest()
//        useFlatMapMerge()
        useBuffer()
    }

    private fun useFilter() {
        viewModelScope.launch {
            timerFlow
                .filter { it % 2 == 0 }
                .collect {
                    println("The timer is: $it")
                }
        }
    }

    private fun useMap() {
        viewModelScope.launch {
            timerFlow.map { "$it * 2 : ${it * 2}" }
                .collect {
                    println("result of $it")
                }
        }
    }

    private fun useOnEach() {
        viewModelScope.launch {
            timerFlow
                .onEach { print("$it * $it: ") }
                .map { it * it }
                .collect {
                    print("$it")
                }
        }
    }

    private fun useCount() {
        viewModelScope.launch {
            val count = timerFlow.filter { it > 3 }
                .map { it * it }
                .count { it % 2 == 0 }
            println(count)
        }
    }

    private fun useReduce() {
        viewModelScope.launch {
            val reduce = timerFlow.reduce { accumulator, value ->
                accumulator + value
            }
            println("the result of reduce: $reduce")
        }
    }

    private fun useFold() {
        viewModelScope.launch {
            val fold = timerFlow.fold(100) { accumulate, value ->
                accumulate + value
            }
            println("the result of fold: $fold")
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun useFlatMapConcat() {
        viewModelScope.launch {
            timerFlow.flatMapConcat { value ->
                flow {
                    emit(value * 1)
                    delay(1500L)
                    emit(value * 2)
                }
            }.collect {
                println("flat concat: $it")
            }

        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun useFlatMapLatest() {
        viewModelScope.launch {
            timerFlow.flatMapLatest { value ->
                flow {
                    delay(1500L)
                    emit(value * 1)
                    emit(value * 2)
                }
            }.collect {
                println("flat latest: $it")
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun useFlatMapMerge() {
        viewModelScope.launch {
            timerFlow.flatMapMerge { value ->
                flow {
                    delay(1500L)
                    emit(value * 1)
                    emit(value * 2)
                }
            }.collect {
                println("flat merge: $it")
            }
        }
    }

    private suspend fun getOrderFlow() = flow {
        delay(250L)
        emit("Appetizer")
        delay(1000L)
        emit("Main Dish")
        delay(100L)
        emit("Dessert")
    }

    private fun useBuffer() {
        viewModelScope.launch {
            getOrderFlow()
                .onEach {
                    println("OrderFlow: $it delivered")
                }
                .buffer()
                .collect {
                    println("OrderFlow: start eating $it")
                    delay(2000L)
                    println("OrderFlow: finished eating $it")
                }
        }
    }

}
