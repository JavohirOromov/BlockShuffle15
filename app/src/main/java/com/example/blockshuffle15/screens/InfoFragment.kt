package com.example.blockshuffle15.screens
import Media
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.blockshuffle15.R
import com.example.blockshuffle15.databinding.FragmentInfoBinding
import com.example.blockshuffle15.storage.LocalStorage
import dev.androidbroadcast.vbpd.viewBinding
/**
 * Creator: Javohir Oromov
 * project: Block Shuffle 15
 * Javohir's MacBook Air
 */
class InfoFragment: Fragment(R.layout.fragment_info) {
    private val binding:FragmentInfoBinding by viewBinding(FragmentInfoBinding::bind)
    private var storage: LocalStorage? = null
    private var music: Media? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        storage = LocalStorage.getInstance()
        music = Media.getInstance()
        addClickEvents()
        checkMusic()
    }

    private fun addClickEvents(){
        binding.back.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        binding.linkedinText.setOnClickListener {
            val profileUsername = "javohir-oromov-812aab317" // LinkedIn username
            val linkedInAppUri = Uri.parse("linkedin://in/$profileUsername") // Ilova uchun deep link
            val linkedInWebUri = Uri.parse("https://www.linkedin.com/in/$profileUsername") // Brauzer uchun

            val intent = Intent(Intent.ACTION_VIEW, linkedInAppUri)
            intent.setPackage("com.linkedin.android") // Ilovada ochishga harakat qiladi

            try {
                startActivity(intent) // LinkedIn ilovasida ochiladi
            } catch (e: Exception) {
                startActivity(Intent(Intent.ACTION_VIEW, linkedInWebUri)) // Brauzerda ochiladi
            }
        }
        binding.telegramText.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/Javohir_Oromov"))
            startActivity(intent)
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