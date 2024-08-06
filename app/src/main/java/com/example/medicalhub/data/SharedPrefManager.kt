package com.example.i_freezemanager.data

import android.content.Context
import android.content.SharedPreferences
import com.example.medicalhub.adapter.DayTimeItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SharedPrefManager(private val context: Context) {
    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences("settings", Context.MODE_PRIVATE)
    }
    private val gson = Gson()

    //get shared preferences
    fun getPrefVal(context: Context): SharedPreferences {
        val sharedPreferences =
            context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        return sharedPreferences
    }


    //set shared preferences
    fun setPrefVal(context: Context, key: String, value: String) {
        if (key != null) {
            val editor: SharedPreferences.Editor = getPrefVal(context).edit()
            editor.putString(key, value)
            editor.apply()
        }
    }


    //delete shared preferences
    fun delSharedVal(context: Context) {
        val editor: SharedPreferences.Editor = getPrefVal(context).edit()
        editor.clear()
        editor.apply()
    }

    // Save an ArrayList to SharedPreferences
    fun saveList(key: String, list: List<String>) {
        val jsonString = gson.toJson(list)
        sharedPreferences.edit().putString(key, jsonString).apply()
    }

    // Retrieve an ArrayList from SharedPreferences
    fun getList(key: String): ArrayList<String> {
        val jsonString = sharedPreferences.getString(key, null)
        return if (jsonString != null) {
            gson.fromJson(jsonString, object : TypeToken<ArrayList<String>>() {}.type)
        } else {
            ArrayList()
        }
    }

    // Save a list of pairs to SharedPreferences
    fun savePairList(key: String, list: List<Pair<String, String>>) {
        val jsonString = gson.toJson(list)
        sharedPreferences.edit().putString(key, jsonString).apply()
    }

    // Retrieve a list of pairs from SharedPreferences
    fun getPairList(key: String): List<Pair<String, String>> {
        val jsonString = sharedPreferences.getString(key, null)
        return if (jsonString != null) {
            gson.fromJson(jsonString, object : TypeToken<List<Pair<String, String>>>() {}.type)
        } else {
            emptyList()
        }
    }

    // Save a list of DayTimeItem to SharedPreferences
    fun saveDayTimeItemList(key: String, list: List<DayTimeItem>) {
        val jsonString = gson.toJson(list)
        sharedPreferences.edit().putString(key, jsonString).apply()
    }

    // Retrieve a list of DayTimeItem from SharedPreferences
    fun getDayTimeItemList(key: String): List<DayTimeItem> {
        val jsonString = sharedPreferences.getString(key, null)
        return if (jsonString != null) {
            gson.fromJson(jsonString, object : TypeToken<List<DayTimeItem>>() {}.type)
        } else {
            emptyList()
        }
    }

}