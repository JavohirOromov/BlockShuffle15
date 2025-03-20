package com.example.blockshuffle15.dialogs
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.example.blockshuffle15.R
import com.example.blockshuffle15.databinding.DialogRestartBinding
/**
 * Creator: Javohir Oromov
 * project: Block Shuffle 15
 * Javohir's MacBook Air
 */
class RestartDialog(context: Context): AlertDialog(context) {
    private val binding: DialogRestartBinding = DialogRestartBinding.inflate(LayoutInflater.from(context))

    private var yesClickListener: (() -> Unit)? = null
    private var noClickListener: (() -> Unit)? = null
    fun setYesClickListener(yesClickListener: () -> Unit){
        this.yesClickListener = yesClickListener
    }
    init {
        setView(binding.root)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.attributes?.windowAnimations = R.style.CustomDialogAnim
        binding.no.setOnClickListener {
            dismiss()
        }
        binding.yes.setOnClickListener {
            yesClickListener?.invoke()
        }
    }
}