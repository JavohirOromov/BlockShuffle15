import android.content.Context
import android.media.MediaPlayer
/**
 * Creator: Javohir Oromov
 * Project: Block Shuffle 15
 * Date: 22/03/25
 * Javohir's MacBook Air
 */
class Media private constructor(){
    private var media: MediaPlayer? = null

    companion object{
      private lateinit var instance: Media

        fun init(context: Context,resId: Int){
            if (!(::instance.isInitialized)){
                instance = Media()
                instance.media = MediaPlayer.create(context, resId)
            }
        }
        fun getInstance(): Media{
            return instance
        }
    }
    fun play(){
        media?.start()
    }
    fun pause(){
        media?.pause()
    }
    fun stop(){
        media?.stop()
        media?.release()
        media = null
    }
}