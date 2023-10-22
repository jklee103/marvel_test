package com.example.marvelapp

import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import com.pixplicity.easyprefs.library.Prefs
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MarvelApp : Application() {
    var sharedFileName: String = ""
        set(projectName) {
            val sb = StringBuilder()

            sb.append("shared")
            sb.append("_")
            sb.append(projectName)
            sb.append("_file")

            field = sb.toString()
        }

    override fun onCreate() {
        super.onCreate()
        sharedFileName = "marvelapp"

        Prefs.Builder().run {
            setContext(this@MarvelApp)
            setMode(ContextWrapper.MODE_PRIVATE)
            setPrefsName(sharedFileName)
            setUseDefaultSharedPreference(true)
            build()
        }
    }

    init {
        instance = this
    }

    companion object {
        lateinit var instance: MarvelApp
        fun getAppContext(): Context = instance.applicationContext
    }
}