package com.example.blockshuffle15.dialogs
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.example.blockshuffle15.R
import com.example.blockshuffle15.databinding.DialogQuitBinding
/**
 * Creator: Javohir Oromov
 * project: Block Shuffle 15
 * Javohir's MacBook Air
 */
class QuitDialog(context: Context): AlertDialog(context) {

    private val binding: DialogQuitBinding = DialogQuitBinding.inflate(LayoutInflater.from(context))
    private var yesClickListener: (() -> Unit)? = null

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