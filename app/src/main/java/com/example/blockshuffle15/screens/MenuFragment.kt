package com.example.blockshuffle15.screens
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.blockshuffle15.R
import com.example.blockshuffle15.databinding.FragmentMenuBinding
import com.example.blockshuffle15.dialogs.QuitDialog
import com.example.blockshuffle15.screens.game.GameFragment
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
    private val music: MediaPlayer by lazy {
        MediaPlayer.create(requireContext(),R.raw.music1)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        quitDialog = QuitDialog(requireContext())
        music.start()
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

    override fun onResume() {
        super.onResume()
        music.start()
    }
    override fun onPause() {
        super.onPause()
        music.pause()
    }
}