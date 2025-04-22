package com.aman.retrofitmoshihiltapp.dependency_injection

import android.content.SharedPreferences
import androidx.core.content.edit
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import javax.inject.Inject


class AppUserPreferences @Inject constructor(
    private val sharedPreferences: SharedPreferences
){
    fun <T> setModel(keyName: String, data: T, clazz: Class<T>) {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val adapter = moshi.adapter(clazz)
        val json = adapter.toJson(data)
        sharedPreferences.edit {
            putString(keyName, json)
            apply()
        }
    }

    fun <T> getModel(keyName: String, clazz: Class<T>): T? {
        val json = sharedPreferences.getString(keyName, null) ?: return null
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val adapter = moshi.adapter(clazz)
        return adapter.fromJson(json)
    }

    fun setBooleanValue(key: String, value: Boolean){
        sharedPreferences.edit {
            putBoolean(key, value)
            apply()
            commit()
        }
    }

    fun getBooleanValue(key: String) = sharedPreferences.getBoolean(key, false)

    fun setStringValue(key: String, value: String){
        sharedPreferences.edit {
            putString(key, value)
            apply()
            commit()
        }
    }

    fun getStringValue(key: String) = sharedPreferences.getString(key, "")

    fun clearUserPreferences() {
        sharedPreferences.edit {
            clear()
            commit()
        }
    }


}
