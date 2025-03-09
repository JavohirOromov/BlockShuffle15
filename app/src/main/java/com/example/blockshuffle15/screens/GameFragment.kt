package com.example.blockshuffle15.screens
import android.media.MediaPlayer
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.Chronometer
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import com.example.blockshuffle15.R
import com.example.blockshuffle15.databinding.FragmentGameBinding
import dev.androidbroadcast.vbpd.viewBinding
import kotlin.math.abs
class GameFragment: Fragment(R.layout.fragment_game) {
    private val binding: FragmentGameBinding by viewBinding(FragmentGameBinding::bind)
    private lateinit var list: MutableList<Int>
    private lateinit var buttons: Array<Array<AppCompatButton>>
    private var emptyX = 0
    private var emptyY = 0
    private var score = 0
    private var chronometer: Chronometer? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadViews()
        setShuffleDate()
        clickEvent()
    }
    private fun loadViews(){
        binding.score.text = "0"
        chronometer = Chronometer(requireContext())
        binding.time.base = SystemClock.elapsedRealtime()
        binding.time.start()
        buttons = Array(4){Array(4){ AppCompatButton(requireContext()) } }
        val layout = binding.container
        for (i in 0 until layout.childCount){
            val temp = layout.getChildAt(i) as LinearLayout
            for (j in 0 until temp.childCount){
                buttons[i][j] = temp.getChildAt(j) as AppCompatButton
            }
        }
    }

    private fun setShuffleDate(){
        list = MutableList(16){it}
        while (isSolvable()){
            list.shuffle()
        }
        for (i in buttons.indices){
            for (j in buttons[i].indices){
                if (list[i * 4 + j] == 0){
                    buttons[i][j].visibility = View.INVISIBLE
                    emptyX = i
                    emptyY = j
                }else{
                    buttons[i][j].text = list[i * 4 + j].toString()
                    buttons[i][j].visibility = View.VISIBLE
                }
            }
        }
    }
    private fun clickEvent(){
        for (i in buttons.indices){
            for (j in buttons[i].indices){
                buttons[i][j].tag = MyCoordinate(i,j)
                buttons[i][j].setOnClickListener {
                    val temp = it as AppCompatButton
                    val myCoordinate = temp.tag as MyCoordinate
                    checkCanMove(myCoordinate.x,myCoordinate.y)
                }
            }
        }
        binding.restart.setOnClickListener {
            loadViews()
            score = 0
            setShuffleDate()
        }
    }
    private fun checkCanMove(x: Int, y: Int){
        if ((abs(emptyX - x) == 1 && emptyY == y) || (abs(emptyY - y) == 1 && emptyX == x)){
            buttons[emptyX][emptyY].apply {
                visibility = View.VISIBLE
                text = buttons[x][y].text
            }
            buttons[x][y].apply {
                text = "0"
                visibility = View.INVISIBLE
            }
            setScore()
            clickMusic()
            emptyX = x
            emptyY = y
            if (gameOver()){
                Toast.makeText(requireContext(),"Yutingiz",Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun setScore(){
        score++
        binding.score.text = score.toString()
    }
    private fun clickMusic(){
        val media = MediaPlayer.create(requireContext(),R.raw.click1)
        media.start()

    }
    private fun gameOver(): Boolean{
        for (i in 0 until 4) {
            for (j in 0 until 4) {
                if ((i * 4 + j) == 15) return true
                if (buttons[i][j].text.toString().toInt() != (i * 4 + j + 1)) {
                    return false
                }
            }
        }
        return true
    }
    private fun isSolvable(): Boolean {
        val flatNumbers = mutableListOf<Int>()

        for (num in list) {
            if (num != 0) {
                flatNumbers.add(num)
            }
        }

        var inversions = 0
        for (i in flatNumbers.indices) {
            for (j in i + 1 until flatNumbers.size) {
                if (flatNumbers[i] > flatNumbers[j]) {
                    inversions++
                }
            }
        }
        val emptyRow = list.indexOf(0) / 4 + 1
        return (emptyRow % 2 == 0 && inversions % 2 != 0) || (emptyRow % 2 != 0 && inversions % 2 == 0)
    }
}
data class MyCoordinate(val x: Int, val y: Int)