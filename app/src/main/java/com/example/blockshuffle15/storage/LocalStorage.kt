package com.example.blockshuffle15.storage
import android.content.Context
import android.content.SharedPreferences

class LocalStorage private constructor(context: Context){
    private val preferences: SharedPreferences = context.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = preferences.edit()

    companion object{
        private lateinit var storage: LocalStorage
        private const val FILE_NAME = "Javohir's Puzzle"
        private const val SCORE = "score"

        fun init(context: Context){
            if (!(::storage.isInitialized)){
                storage = LocalStorage(context)
            }
        }
        fun getInstance(): LocalStorage{
            return storage
        }
    }
    fun saveScore(score: Int){
        editor.putInt(SCORE, score)
        editor.apply()
    }
    fun getCount(): Int{
        return preferences.getInt(SCORE,0)
    }
}