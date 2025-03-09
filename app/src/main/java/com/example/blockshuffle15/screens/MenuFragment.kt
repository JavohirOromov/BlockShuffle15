package com.example.blockshuffle15.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.blockshuffle15.R
import com.example.blockshuffle15.databinding.FragmentMenuBinding
import com.example.blockshuffle15.utils.replaceFragment
import dev.androidbroadcast.vbpd.viewBinding

class MenuFragment: Fragment(R.layout.fragment_menu) {
    private val binding: FragmentMenuBinding by viewBinding(FragmentMenuBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.newGameBtn.setOnClickListener {
            replaceFragment(GameFragment())
        }
    }
}