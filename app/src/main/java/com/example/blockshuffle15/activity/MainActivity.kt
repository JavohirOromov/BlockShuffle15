package com.example.blockshuffle15.activity

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.blockshuffle15.R
import com.example.blockshuffle15.dialogs.SettingsDialog
import com.example.blockshuffle15.screens.MenuFragment
import com.example.blockshuffle15.storage.LocalStorage

class MainActivity : AppCompatActivity() {
    private var music: MediaPlayer? = null
    private var storage: LocalStorage? = null
    private var settingsDialog: SettingsDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        storage = LocalStorage.getInstance()
        settingsDialog = SettingsDialog(this)
        music = MediaPlayer.create(this,R.raw.music2)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.puzzle, MenuFragment())
            .commit()

        if (storage?.getMusicCheck() == true) {
            music?.start()
            music?.isLooping = true
            settingsDialog?.checkMusic(true)
            Log.d("TTT","if ${storage?.getMusicCheck()}")
        } else {
            music?.pause()
            settingsDialog?.checkMusic(false)
            Log.d("TTT","else ${storage?.getMusicCheck()}")
        }
    }
    fun toggleMusic(isOn: Boolean) {
        if (isOn) {
            music?.release()
            music = MediaPlayer.create(this, R.raw.music2)
            music?.isLooping = true
            music?.start()
        } else {
            music?.stop()
            music = null
        }
        storage?.saveMusicCheck(isOn)
    }

    override fun onResume() {
        super.onResume()
        if (storage?.getMusicCheck() == true) {
            music?.start()
            music?.isLooping = true
            settingsDialog?.checkMusic(true)
            Log.d("TTT","if ${storage?.getMusicCheck()}")
        } else {
            music?.pause()
            settingsDialog?.checkMusic(false)
            Log.d("TTT","else ${storage?.getMusicCheck()}")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        music?.pause()
    }

    override fun onPause() {
        super.onPause()
        music?.pause()
    }
}