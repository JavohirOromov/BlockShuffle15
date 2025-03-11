package com.example.blockshuffle15.screens
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.blockshuffle15.R
import com.example.blockshuffle15.databinding.FragmentRecordBinding
import com.example.blockshuffle15.storage.LocalStorage
import dev.androidbroadcast.vbpd.viewBinding

class RecordFragment: Fragment(R.layout.fragment_record) {
    private val binding: FragmentRecordBinding by viewBinding(FragmentRecordBinding::bind)
    private val storage = LocalStorage.getInstance()

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.score1.text = storage.getRecord1().toString()
        binding.score2.text = storage.getRecord2().toString()
        binding.score3.text = storage.getRecord3().toString()
        binding.time1.text = storage.getTime1()
        binding.time2.text = storage.getTime2()
        binding.time3.text = storage.getTime3()
        binding.date1.text = storage.getDate1()
        binding.date2.text = storage.getDate2()
        binding.date3.text = storage.getDate3()

        binding.back.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
}