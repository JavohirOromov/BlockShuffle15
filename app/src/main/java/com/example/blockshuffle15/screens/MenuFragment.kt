package com.example.blockshuffle15.screens
import Media
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.blockshuffle15.R
import com.example.blockshuffle15.databinding.FragmentMenuBinding
import com.example.blockshuffle15.dialogs.QuitDialog
import com.example.blockshuffle15.screens.game.GameFragment
import com.example.blockshuffle15.storage.LocalStorage
import com.example.blockshuffle15.utils.replaceFragment
import dev.androidbroadcast.vbpd.viewBinding
/**
 * Creator: Javohir Oromov
 * project: Block Shuffle 15
 * Javohir's MacBook Air
 */
class MenuFragment: Fragment(R.layout.fragment_menu) {
    private val binding: FragmentMenuBinding by viewBinding(FragmentMenuBinding::bind)
    private var quitDialog: QuitDialog? = null
    private var storage: LocalStorage? = null
    private var music: Media? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        quitDialog = QuitDialog(requireContext())
        storage = LocalStorage.getInstance()
        music = Media.getInstance()
        addClickEvents()
        checkMusic()
    }
    private fun addClickEvents(){
        binding.newGameBtn.setOnClickListener {
            val bundle = Bundle().apply {
                putInt("newGame",1)
            }
            val fragment = GameFragment().apply {
                arguments = bundle
            }
            replaceFragment(fragment)
        }
        binding.recordBtn.setOnClickListener {
            replaceFragment(RecordFragment())
        }
        binding.InfoBtn.setOnClickListener {
            replaceFragment(InfoFragment())
        }
        binding.quitBtn.setOnClickListener {
            quitDialog?.show()
        }
        binding.continueBtn.setOnClickListener {
            val bundle = Bundle().apply {
                putInt("continue",0)
            }
            val fragment = GameFragment().apply {
                arguments = bundle
            }
            replaceFragment(fragment)
        }
        quitDialog?.setYesClickListener {
            requireActivity().finish()
            quitDialog?.dismiss()
        }
    }
    private fun checkMusic(){
        if (storage?.getMusicCheck() == true){
            music?.play()
        }else{
            music?.pause()
        }
    }
    override fun onResume() {
        super.onResume()
        if (storage?.getMusicCheck() == true){
            music?.play()
        }else{
            music?.pause()
        }
    }
    override fun onPause() {
        super.onPause()
        music?.pause()
    }
}