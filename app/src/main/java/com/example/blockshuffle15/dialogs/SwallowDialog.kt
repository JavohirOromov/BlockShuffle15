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
    private var noClickListener: (() -> Unit)? = null
    fun setYesClickListener(yesClickListener: () -> Unit){
        this.yesClickListener = yesClickListener
    }
    fun setNoClickListener(noClickListener: () -> Unit){
        this.noClickListener = noClickListener
    }
    init {
        setView(binding.root)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.attributes?.windowAnimations = R.style.CustomDialogAnim
        binding.no.setOnClickListener {
            noClickListener?.invoke()
        }
        binding.yes.setOnClickListener {
            yesClickListener?.invoke()
        }
    }
}