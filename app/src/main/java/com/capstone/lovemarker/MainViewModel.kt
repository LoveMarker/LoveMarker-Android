package com.capstone.lovemarker

import android.app.Application
import android.media.SoundPool
import androidx.lifecycle.AndroidViewModel

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val applicationContext = application.applicationContext
    private val soundPool = SoundPool.Builder().setMaxStreams(ITEM_COUNT).build()
    private val sounds = listOf(
        soundPool.load(applicationContext, R.raw.do1,1),
        soundPool.load(applicationContext, R.raw.re,1),
        soundPool.load(applicationContext, R.raw.mi,1),
        soundPool.load(applicationContext, R.raw.fa,1),
        soundPool.load(applicationContext, R.raw.sol,1),
        soundPool.load(applicationContext, R.raw.la,1),
        soundPool.load(applicationContext, R.raw.si,1),
        soundPool.load(applicationContext, R.raw.do2,1),
    )

    fun playSound(index: Int) {
        soundPool.play(sounds[index], 1f, 1f, 0, 0, 1f)
    }

    override fun onCleared() {
        super.onCleared()
        soundPool.release() // 메모리에서 해제
    }

    companion object {
        private const val ITEM_COUNT = 8
    }
}