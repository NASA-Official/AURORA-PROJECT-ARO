package com.nassafy.aro.util

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesUtil(context: Context) {
    var preferences: SharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    companion object {
        const val SHARED_PREFERENCES_NAME = "aro_preference"
    }

//    fun addUserRefreshToken(refresh_token: String) {
//        val editor = preferences.edit()
//        editor.putString(REFRESH_TOKEN, refresh_token)
//        editor.apply()
//    } // End of addUserRefreshToken
//
//    fun getUserRefreshToken(): String {
//        return preferences.getString(REFRESH_TOKEN, "").toString()
//    } // End of getUserRefreshToken
//
//    fun addUserAccessToken(access_token: String) {
//        val editor = preferences.edit()
//        val temp = "Bearer $access_token"
//        editor.putString(ACCESS_TOKEN, temp)
//        editor.apply()
//    }
//
//    fun getUserAccessToken(): String {
//        return preferences.getString(ACCESS_TOKEN, "").toString()
//    } // End of getUserAccessToken
//
//    fun deleteAccessToken(){
//        preferences.edit().remove(ACCESS_TOKEN).apply()
//    }
//
//    fun deleteRefreshToken(){
//        preferences.edit().remove(REFRESH_TOKEN).apply()
//    }




} // End of SharedPreferencesUtil class