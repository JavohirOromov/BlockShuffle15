package com.example.blockshuffle15.app
import android.app.Application
import com.example.blockshuffle15.storage.LocalStorage

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        LocalStorage.init(this)
    }
}