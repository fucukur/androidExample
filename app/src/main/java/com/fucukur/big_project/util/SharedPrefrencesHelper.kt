package com.fucukur.big_project.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class SharedPrefrencesHelper {

    companion object{
        private const val PREF_TIME = "Pref time"
        private var prefs : SharedPreferences? = null

        @Volatile private var instance : SharedPrefrencesHelper? =null
        private val LOCK = Any()
        operator fun invoke (context: Context): SharedPrefrencesHelper = instance ?: synchronized(LOCK){
            instance ?: buildHelper(context).also {
                instance = it
            }
        }
        private fun buildHelper(context: Context) : SharedPrefrencesHelper{
            prefs = PreferenceManager.getDefaultSharedPreferences(context)
            return SharedPrefrencesHelper()
        }
    }

    fun saveUpdateTime(time: Long) {
        prefs?.edit(commit = true){
            putLong(PREF_TIME,time)
        }
    }
    fun getUpdateTime() = prefs?.getLong(PREF_TIME,0)

}