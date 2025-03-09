package com.example.blockshuffle15.utils
import androidx.fragment.app.Fragment
import com.example.blockshuffle15.R

fun Fragment.replaceFragment(fm: Fragment){
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.puzzle,fm)
            .addToBackStack(fm.javaClass.name)
            .commit()
    }
