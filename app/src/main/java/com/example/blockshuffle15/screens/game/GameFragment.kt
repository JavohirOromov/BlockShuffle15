package com.example.blockshuffle15.screens.game
import SwallowDialog
import android.annotation.SuppressLint
import android.icu.util.Calendar
import android.media.MediaPlayer
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.Chronometer
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import com.example.blockshuffle15.R
import com.example.blockshuffle15.databinding.FragmentGameBinding
import com.example.blockshuffle15.dialogs.RestartDialog
import com.example.blockshuffle15.dialogs.SettingsDialog
import com.example.blockshuffle15.storage.LocalStorage
import dev.androidbroadcast.vbpd.viewBinding
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.abs
/**
 * Creator: Javohir Oromov
 * project: Block shuffle 15
 * Date: 15/03/25
 * Javohir's MacBook Air
 */
class GameFragment : Fragment(R.layout.fragment_game) {
    private val binding: FragmentGameBinding by viewBinding(FragmentGameBinding::bind)
    private lateinit var list: ArrayList<Int>
    private lateinit var buttons: Array<Array<AppCompatButton>>
    private var emptyX = 0
    private var emptyY = 0
    private var score = 0
    private var chronometer: Chronometer? = null
    private var storage: LocalStorage? = null
    private val calendar = Calendar.getInstance()
    private val dateFormat = SimpleDateFormat("dd - MM", Locale.getDefault())
    private val currentDate = dateFormat.format(calendar.time)
    private var settingsDialog: SettingsDialog? = null
    private var restartDialog: RestartDialog? = null
    private var swallowDialog: SwallowDialog? = null
    private var checkClick: Boolean = true
    private val music: MediaPlayer by lazy {
        MediaPlayer.create(requireContext(),R.raw.music1)
    }
    private val sound: MediaPlayer by lazy {
        MediaPlayer.create(requireContext(),R.raw.click1)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val isContinue = arguments?.getInt("continue",-1)?: -1
        val isNewGame = arguments?.getInt("newGame",-1)?: -1
        if (isContinue == 0){
            loadViews()
            setShuffleDate()
            clickEvent()
            setViewGetStorage()
        }else if (isNewGame == 1){
            loadViews()
            setShuffleDate()
            clickEvent()
        }
    }
    private fun loadViews() {
        binding.score.text = "0"
        binding.swallow.setBackgroundResource(R.drawable.time1)
        chronometer = Chronometer(requireContext())
        storage = LocalStorage.getInstance()
        settingsDialog = SettingsDialog(requireContext())
        restartDialog = RestartDialog(requireContext())
        swallowDialog = SwallowDialog(requireContext())
        binding.time.base = SystemClock.elapsedRealtime()
        binding.time.start()
        music.start()
        buttons = Array(4) { Array(4) { AppCompatButton(requireContext()) } }
        val layout = binding.container
        for (i in 0 until layout.childCount) {
            val temp = layout.getChildAt(i) as LinearLayout
            for (j in 0 until temp.childCount) {
                buttons[i][j] = temp.getChildAt(j) as AppCompatButton
            }
        }
    }
    private fun setShuffleDate() {
        list = ArrayList()
        for (i in 1 until 16){
            list.add(i)
        }
        list.add(0)
//        do {
//            list.shuffle()
//        }while (isSolvable())
        for (i in buttons.indices) {
            for (j in buttons[i].indices) {
                if (list[i * 4 + j] == 0) {
                    buttons[i][j].visibility = View.INVISIBLE
                    buttons[i][j].text = "0"
                    emptyX = i
                    emptyY = j
                } else {
                    buttons[i][j].text = list[i * 4 + j].toString()
                    buttons[i][j].visibility = View.VISIBLE
                }
            }
        }
    }

    private fun clickEvent() {
        for (i in buttons.indices) {
            for (j in buttons[i].indices) {
                buttons[i][j].tag = MyCoordinate(i, j)
                buttons[i][j].setOnClickListener {
                    val temp = it as AppCompatButton
                    val myCoordinate = temp.tag as MyCoordinate
                    checkCanMove(myCoordinate.x, myCoordinate.y)
                }
            }
        }
        binding.restart.setOnClickListener {
            restartDialog?.show()
        }
        restartDialog?.setYesClickListener {
            binding.score.text = "0"
            binding.swallow.setBackgroundResource(R.drawable.time1)
            binding.time.base = SystemClock.elapsedRealtime()
            binding.time.start()
            score = 0
            setShuffleDate()
            restartDialog?.dismiss()
        }
        binding.menu.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        binding.settings.setOnClickListener {
            settingsDialog?.show()
        }
        settingsDialog?.setPlayClickListener { musicCheck, soundCheck ->
            if (musicCheck){
                music.start()
            }else{
                music.pause()
            }
            if (soundCheck){
               sound.start()
                checkClick = true
            }else{
                sound.pause()
                checkClick = false
            }
        }
        swallowDialog?.setYesClickListener {
            requireActivity().supportFragmentManager.popBackStack()
            swallowDialog?.dismiss()
        }
    }
    private fun checkCanMove(x: Int, y: Int) {
        if ((abs(emptyX - x) == 1 && emptyY == y) || (abs(emptyY - y) == 1 && emptyX == x)) {
            buttons[emptyX][emptyY].apply {
                visibility = View.VISIBLE
                text = buttons[x][y].text
            }
            buttons[x][y].apply {
                text = "0"
                visibility = View.INVISIBLE
            }
            setScore()
            if (sound.isPlaying){
                sound.seekTo(0)
            }else{
                if (checkClick){
                    sound.start()
                    checkClick = true
                }else{
                    sound.pause()
                    checkClick = false
                }
            }
            checkSwallow()
            emptyX = x
            emptyY = y
            if (x == 3 && y == 3) {
                if (gameOver()) {
                    swallowDialog?.show()
                    saveRecord()
                }
            }
        }
    }
   @SuppressLint("DefaultLocale")
    private fun saveRecord(){
        Log.d("TTT","ishlayapti")
        if ((score < (storage?.getRecord1() ?: 0)) || storage?.getRecord1() == 0){ // score = 30, getRecord1 = 10
            storage?.saveRecord1(score)
            val elapsedTime = SystemClock.elapsedRealtime() - binding.time.base
            val minutes = (elapsedTime / 1000) / 60
            val seconds = (elapsedTime / 1000) % 60
            val formattedTime = String.format("%02d:%02d", minutes, seconds)
            storage?.saveTime1(formattedTime)
            storage?.saveDate1(currentDate)
        }
       else if ((score < (storage?.getRecord2() ?: 0)) || storage?.getRecord2() == 0){ // score = 30, getRecord2 = 15
            storage?.saveRecord2(score)
            val elapsedTime = SystemClock.elapsedRealtime() - binding.time.base
            val minutes = (elapsedTime / 1000) / 60
            val seconds = (elapsedTime / 1000) % 60
            val formattedTime = String.format("%02d:%02d", minutes, seconds)
            storage?.saveTime2(formattedTime)
            storage?.saveDate2(currentDate)

        }
        else if((score < (storage?.getRecord3()?: 0)) || storage?.getRecord3() == 0){   // score = 30, getRecord = 40
            storage?.saveRecord3(score)
            val elapsedTime = SystemClock.elapsedRealtime() - binding.time.base
            val minutes = (elapsedTime / 1000) / 60
            val seconds = (elapsedTime / 1000) % 60
            val formattedTime = String.format("%02d:%02d", minutes, seconds)
            storage?.saveTime3(formattedTime)
            storage?.saveDate3(currentDate)
        }
    }
    private fun setScore() {
        score++
        binding.score.text = score.toString()
    }
    private fun gameOver(): Boolean {
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

    private fun checkSwallow() {
        if (isGameComplete()){
            binding.swallow.setBackgroundResource(R.drawable.time5)
            return
        }
        var progress = 0
        for (i in 0 until 4) {
            if (!isRowCorrect(i)) break
            progress = i + 1
        }
        when (progress) {
            0 -> binding.swallow.setBackgroundResource(R.drawable.time1)
            1 -> binding.swallow.setBackgroundResource(R.drawable.time2)
            2 -> binding.swallow.setBackgroundResource(R.drawable.time3)
            3 -> binding.swallow.setBackgroundResource(R.drawable.time4)
        }
    }

    private fun isRowCorrect(row: Int): Boolean {
        for (j in 0 until 4) {
            val num = buttons[row][j].text.toString().toIntOrNull()
            if (num == null || num == 0 || num != row * 4 + j + 1) {
                return false
            }
        }
        return true
    }
    private fun isGameComplete(): Boolean{
        var expected = 1
        for (i in 0 until 4) {
            for (j in 0 until 4) {
                if (i == 3 && j == 3) continue
                val num = buttons[i][j].text.toString().toIntOrNull()
                if (num == null || num != expected) return false
                expected++
            }
        }
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        settingsDialog = null
    }

    override fun onPause() {
        super.onPause()
        music.pause()
        val stringBuilder = StringBuilder()
        val elapsedTime = SystemClock.elapsedRealtime() - binding.time.base
        for (i in buttons.indices){
            for (j in buttons.indices){
                stringBuilder.append(buttons[i][j].text).append('#')
            }
        }
        storage?.saveButton(stringBuilder.toString())
        storage?.saveScore(score)
        storage?.saveTime(elapsedTime)
    }

    override fun onResume() {
        super.onResume()
        music.start()
    }
    private fun setViewGetStorage(){
        val savedData = storage?.getButton()?.split("#")?.filter { it.isNotEmpty() } ?: listOf()
        val savedTime = storage?.getTime()?: 0L
        if (savedData.size == 16) {
            list.clear()
            list.addAll(savedData.map { it.toInt() }) // To'g'ri list qayta tiklanadi
                    for (i in buttons.indices) {
                        for (j in buttons[i].indices) {
                            if (list[i * 4 + j] == 0) {
                                buttons[i][j].visibility = View.INVISIBLE
                                buttons[i][j].text = "0"
                                emptyX = i
                                emptyY = j
                            } else {
                                buttons[i][j].text = list[i * 4 + j].toString()
                                buttons[i][j].visibility = View.VISIBLE
                            }
                }
            }
        } else {
            Log.e("GameFragment", "Saqlangan ma’lumot buzilgan! O'yin qayta yuklanmoqda.")
            setShuffleDate()
        }
        score = storage?.getScore() ?: 0
        binding.score.text = score.toString()
        binding.time.base = SystemClock.elapsedRealtime() - savedTime
        binding.time.start()
    }
}
data class MyCoordinate(val x: Int, val y: Int)