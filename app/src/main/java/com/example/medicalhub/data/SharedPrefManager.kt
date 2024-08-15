package com.example.i_freezemanager.data

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.model.DayTimeItemDoctor

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
    fun saveDayTimeItemList(key: String, list: List<DayTimeItemDoctor>) {
        val jsonString = gson.toJson(list)
        sharedPreferences.edit().putString(key, jsonString).apply()
    }

    // Retrieve a list of DayTimeItem from SharedPreferences
    fun getDayTimeItemList(key: String): List<DayTimeItemDoctor> {
        val jsonString = sharedPreferences.getString(key, null)
        return if (jsonString != null) {
            gson.fromJson(jsonString, object : TypeToken<List<DayTimeItemDoctor>>() {}.type)
        } else {
            emptyList()
        }
    }

    fun saveWorkingTimeList(key: String, list: List<DayTimeItemDoctor>) {
        val jsonString = gson.toJson(list)
        sharedPreferences.edit().putString(key, jsonString).apply()
    }

    // Retrieve a list of WorkingTime from SharedPreferences
    fun getWorkingTimeList(key: String): List<DayTimeItemDoctor> {
        val jsonString = sharedPreferences.getString(key, null)
        return if (jsonString != null) {
            gson.fromJson(jsonString, object : TypeToken<List<DayTimeItemDoctor>>() {}.type)
        } else {
            emptyList()
        }
    }

    // Save a boolean value to SharedPreferences
    fun saveBoolean(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    // Retrieve a boolean value from SharedPreferences
    fun getBoolean(key: String, defaultValue: Boolean = true): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }


}