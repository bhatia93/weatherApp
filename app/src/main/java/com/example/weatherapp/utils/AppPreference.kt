package com.example.weather.utils

import android.app.Activity
import android.preference.PreferenceManager
import android.text.TextUtils
import com.google.gson.Gson

object AppPreference {
    const val SAVE_NAMES_SEARCH_NAME = "name_search_array"

    fun seArrayPreference(key: String?, value: ArrayList<String>, activity: Activity): Boolean {
        val preferences =
            PreferenceManager.getDefaultSharedPreferences(activity)
        if (preferences != null) {
            val gsonData = Gson()
            val textList: MutableList<String> = ArrayList()
            textList.addAll(value)
            val jsonText = gsonData.toJson(textList)
            val editor = preferences.edit()
            editor.putString(key, jsonText)
            return editor.commit()
        }
        return false
    }

    fun getArrayPreference(
        key: String?,
        activity: Activity
    ): ArrayList<String>? {
        val preferences =
            PreferenceManager.getDefaultSharedPreferences(activity)
        val gsonData = Gson()
        val stringData = preferences.getString(key, "")
        return if (!TextUtils.isEmpty(stringData))
            return gsonData.fromJson(stringData, Array<String>::class.java)
                .toCollection(ArrayList())
        else
            null
    }


}