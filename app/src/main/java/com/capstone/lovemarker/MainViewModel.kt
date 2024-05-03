package com.capstone.lovemarker

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import java.util.Timer
import kotlin.concurrent.timer

class MainViewModel: ViewModel() {
    private var time = 0
    private var lap = 1
    private var timerTask: Timer? = null

    private val _isRunning = mutableStateOf(false)
    val isRunning : State<Boolean> = _isRunning

    private val _sec = mutableStateOf(0)
    val sec : State<Int> = _sec

    private val _millis = mutableStateOf(0)
    val millis : State<Int> = _millis

    private val _lapTimes = mutableStateOf(mutableListOf<String>())
    val lapTimes: State<List<String>> = _lapTimes

    fun start() {
        _isRunning.value = true

        timerTask = timer(period = 10) {
            time++
            _sec.value = time / 100
            _millis.value = time % 100
        }
    }

    fun pause() {
        _isRunning.value = false
        timerTask?.cancel()
    }

    fun reset() {
        timerTask?.cancel()

        time = 0
        lap = 1
        _lapTimes.value.clear()

        _isRunning.value = false
        _sec.value = 0
        _millis.value = 0
    }

    fun recordLapTime() {
        _lapTimes.value.add(0, "$lap LAP : ${sec.value} ${millis.value}")
        lap++
    }
}