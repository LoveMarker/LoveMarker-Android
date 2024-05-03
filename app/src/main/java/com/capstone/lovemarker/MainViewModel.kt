package com.capstone.lovemarker

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.lifecycle.ViewModel
import kotlin.math.pow

class MainViewModel: ViewModel() {
    private val _bmi = mutableDoubleStateOf(0.0)
    val bmi : State<Double> = _bmi

    fun calcBMI(height: Double, weight: Double) {
        _bmi.doubleValue = weight / (height / 100.0).pow(2.0)
    }
}