package com.example.blockshuffle15.app
import android.app.Application
import com.example.blockshuffle15.storage.LocalStorage
/**
 * Creator: Javohir Oromov
 * project: Block Shuffle 15
 * Javohir's MacBook Air
 */
class App: Application() {
    override fun onCreate() {
        super.onCreate()
        LocalStorage.init(this)
    }
}