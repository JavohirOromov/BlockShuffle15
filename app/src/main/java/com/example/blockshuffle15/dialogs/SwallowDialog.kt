import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.example.blockshuffle15.R
import com.example.blockshuffle15.databinding.DialogSwallowBinding

/**
 * Creator: Javohir Oromov
 * Project: Block Shuffle 15
 * Date: 17/03/25
 * Javohir's MacBook Air
 */
class SwallowDialog(context: Context): AlertDialog(context) {

    private val binding: DialogSwallowBinding = DialogSwallowBinding.inflate(LayoutInflater.from(context))

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