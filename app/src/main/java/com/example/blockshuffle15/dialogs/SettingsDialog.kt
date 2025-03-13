package com.example.blockshuffle15.dialogs
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.example.blockshuffle15.R
import com.example.blockshuffle15.databinding.DialogSettingsBinding
class SettingsDialog(context: Context): AlertDialog(context) {
    private val binding: DialogSettingsBinding = DialogSettingsBinding.inflate(LayoutInflater.from(context))
    private var playClickListener: ((Boolean,Boolean) -> Unit)? = null
    private var initialSoundState = false
    private var initialMusicState = false

    fun setPlayClickListener(playClickListener: (Boolean,Boolean) -> Unit){
        this.playClickListener = playClickListener
    }
    init {
        setView(binding.root)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        initialSoundState = binding.switchSound.isChecked
        initialMusicState = binding.switchMusic.isChecked

        binding.switchSound.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.sound.setBackgroundResource(R.drawable.sound_on)
            } else {
                binding.sound.setBackgroundResource(R.drawable.sound_of)
            }
        }
        binding.switchMusic.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                binding.music.setBackgroundResource(R.drawable.music_on)
            }else{
                binding.music.setBackgroundResource(R.drawable.music_of)
            }
        }
        binding.play.setOnClickListener {
            playClickListener?.invoke(binding.switchMusic.isChecked,binding.switchSound.isChecked)
            dismiss()
        }
        binding.cancel.setOnClickListener {
            binding.switchSound.isChecked = initialSoundState
            binding.switchMusic.isChecked = initialMusicState
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
}
