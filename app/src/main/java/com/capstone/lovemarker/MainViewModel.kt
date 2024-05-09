package com.capstone.lovemarker

import android.app.Application
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

class MainViewModel(application: Application) :
    AndroidViewModel(application), SensorEventListener, LifecycleEventObserver {
    private val _x = mutableStateOf(0f)
    val x : State<Float> = _x

    private val _y = mutableStateOf(0f)
    val y : State<Float> = _y

    private val sensorManager by lazy {
        application.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    override fun onSensorChanged(event: SensorEvent?) {
        // 변화하는 x, y 위치 저장
        event?.let {
            _x.value = event.values[0]
            _y.value = event.values[1]
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        // 액티비티가 유저와 상호작용 할 수 있을 때만 센서를 등록한다.
        if (event == Lifecycle.Event.ON_RESUME) {
            registerSensor()
        } else if(event == Lifecycle.Event.ON_PAUSE) {
            unregisterSensor()
        }
    }

    private fun registerSensor() {
        sensorManager.registerListener(
            this,
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    private fun unregisterSensor() {
        sensorManager.unregisterListener(this)
    }
}