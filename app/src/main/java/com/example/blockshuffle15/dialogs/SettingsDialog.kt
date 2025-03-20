package com.example.blockshuffle15.dialogs
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.BoringLayout
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.example.blockshuffle15.R
import com.example.blockshuffle15.databinding.DialogSettingsBinding
/**
 * Creator: Javohir Oromov
 * project: Block Shuffle 15
 * Javohir's MacBook Air
 */
class SettingsDialog(context: Context): AlertDialog(context) {
    private val binding: DialogSettingsBinding = DialogSettingsBinding.inflate(LayoutInflater.from(context))
    private var initialSoundState = false
    private var initialMusicState = false
    private var musicSwitchClickListener: ((Boolean) -> Unit)? = null
    private var soundSwitchClickListener: ((Boolean) -> Unit)? = null

    fun setMusicSwitchClickListener(musicSwitchClickListener: (Boolean) -> Unit){
        this.musicSwitchClickListener = musicSwitchClickListener
    }
    fun setSoundSwitchClickListener(soundSwitchClickListener: (Boolean) -> Unit){
        this.soundSwitchClickListener = soundSwitchClickListener
    }
    init {
        setView(binding.root)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.attributes?.windowAnimations = R.style.CustomDialogAnim
        initialSoundState = binding.switchSound.isChecked
        initialMusicState = binding.switchMusic.isChecked

        binding.switchSound.setOnCheckedChangeListener { _, isChecked ->
            soundSwitchClickListener?.invoke(isChecked)
            if (isChecked) {
                binding.sound.setBackgroundResource(R.drawable.sound_on)
            } else {
                binding.sound.setBackgroundResource(R.drawable.sound_of)
            }
        }
        binding.switchMusic.setOnCheckedChangeListener { _, isChecked ->
            musicSwitchClickListener?.invoke(isChecked)
            if (isChecked){
                binding.music.setBackgroundResource(R.drawable.music_on)
            }else{
                binding.music.setBackgroundResource(R.drawable.music_of)
            }
        }
        binding.cancel.setOnClickListener {
            dismiss()
        }
    }
    override fun show() {
        super.show()
        initialSoundState = currentSoundState()
        initialMusicState = currentMusicState()

        binding.switchSound.isChecked = initialSoundState
        binding.switchMusic.isChecked = initialMusicState
    }
    private fun currentSoundState(): Boolean {
        return binding.switchSound.isChecked
    }
    private fun currentMusicState(): Boolean {
        return binding.switchMusic.isChecked
    }
    fun checkMusic(check: Boolean){
        binding.switchMusic.isChecked = check
    }
    fun checkSound(check: Boolean){
        binding.switchSound.isChecked = check
    }
}
