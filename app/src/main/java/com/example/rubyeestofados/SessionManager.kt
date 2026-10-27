package com.example.rubyeestofados

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("RubyeSession", Context.MODE_PRIVATE)

    fun setLoggedIn(isLoggedIn: Boolean) {
        prefs.edit().putBoolean("IS_LOGGED_IN", isLoggedIn).apply()
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean("IS_LOGGED_IN", false)
    }

    fun clearSession() {
        prefs.edit().clear().apply()
    }
}