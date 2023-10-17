package com.example.marvelapp

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MarvelApp: Application() {
    init {
        instance = this
    }
    companion object {
        lateinit var instance: MarvelApp
        fun getAppContext(): Context = instance.applicationContext
    }
}