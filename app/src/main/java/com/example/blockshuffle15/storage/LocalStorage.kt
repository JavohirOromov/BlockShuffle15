package com.example.blockshuffle15.storage
import android.content.Context
import android.content.SharedPreferences
class LocalStorage private constructor(context: Context) {
    private val preferences: SharedPreferences =
        context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = preferences.edit()

    companion object {
        private lateinit var storage: LocalStorage
        private const val FILE_NAME = "Javohir's Puzzle"
        private const val SCORE = "score"
        private const val NUMBERS = "numbers"
        private const val TIME = "time"
        private const val RECORD1 = "record1"
        private const val RECORD2 = "record2"
        private const val RECORD3 = "record3"
        private const val TIME1 = "time1"
        private const val TIME2 = "time2"
        private const val TIME3 = "time3"
        private const val DATE1 = "date1"
        private const val DATE2 = "date2"
        private const val DATE3 = "date3"
        private const val MUSIC = "music"
        private const val SOUND = "sound"
        fun init(context: Context) {
            if (!(::storage.isInitialized)) {
                storage = LocalStorage(context)
            }
        }

        fun getInstance(): LocalStorage {
            return storage
        }
    }
        fun saveScore(score: Int) {
            editor.putInt(SCORE, score)
            editor.apply()
        }

        fun getScore(): Int {
            return preferences.getInt(SCORE, 0)
        }

        fun saveButton(numbers: String) {
            editor.putString(NUMBERS, numbers).apply()
        }

        fun getButton(): String? {
            return preferences.getString(NUMBERS, "")
        }

        fun saveTime(time: Long) {
            editor.putLong(TIME, time).apply()
        }

        fun getTime(): Long {
            return preferences.getLong(TIME, 0)
        }

        fun saveRecord1(record1: Int) {
            editor.putInt(RECORD1, record1)
            editor.apply()
        }

        fun getRecord1(): Int {
            return preferences.getInt(RECORD1, 0)
        }

        fun saveRecord2(record2: Int) {
            editor.putInt(RECORD2, record2)
            editor.apply()
        }

        fun getRecord2(): Int {
            return preferences.getInt(RECORD2, 0)
        }

        fun saveRecord3(record3: Int) {
            editor.putInt(RECORD3, record3)
            editor.apply()
        }

        fun getRecord3(): Int {
            return preferences.getInt(RECORD3, 0)
        }

        fun saveTime1(time1: String) {
            editor.putString(TIME1, time1)
            editor.apply()
        }

        fun getTime1(): String? {
            return preferences.getString(TIME1, "00:00")
        }

        fun saveTime2(time2: String) {
            editor.putString(TIME2, time2)
            editor.apply()
        }

        fun getTime2(): String? {
            return preferences.getString(TIME2, "00:00")
        }

        fun saveTime3(time3: String) {
            editor.putString(TIME3, time3)
            editor.apply()
        }

        fun getTime3(): String? {
            return preferences.getString(TIME3, "00:00")
        }

        fun saveDate1(date1: String) {
            editor.putString(DATE1, date1)
            editor.apply()
        }

        fun getDate1(): String? {
            return preferences.getString(DATE1, "00 - 00")
        }

        fun saveDate2(date2: String) {
            editor.putString(DATE2, date2).apply()
        }

        fun getDate2(): String? {
            return preferences.getString(DATE2, "00 - 00")
        }

        fun saveDate3(date3: String) {
            editor.putString(DATE3, date3).apply()
        }

        fun getDate3(): String? {
            return preferences.getString(DATE3, "00 - 00")
        }

        fun saveMusicCheck(checkMusic: Boolean) {
            editor.putBoolean(MUSIC, checkMusic).apply()
        }

        fun getMusicCheck(): Boolean {
            return preferences.getBoolean(MUSIC, false)
        }

        fun saveSoundCheck(checkSound: Boolean) {
            editor.putBoolean(SOUND, checkSound).apply()
        }

        fun getSoundCheck(): Boolean {
            return preferences.getBoolean(SOUND, false)
        }
    }
