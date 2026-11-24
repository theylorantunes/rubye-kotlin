package com.example.rubyeestofados

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("RubyeSession", Context.MODE_PRIVATE)

    fun setLoggedIn(isLoggedIn: Boolean, nome: String = "", email: String = "", isAdmin: Boolean = false) {
        prefs.edit().apply {
            putBoolean("IS_LOGGED_IN", isLoggedIn)
            putString("USER_NOME", nome)
            putString("USER_EMAIL", email)
            putBoolean("IS_ADMIN", isAdmin)
            apply()
        }
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean("IS_LOGGED_IN", false)
    }

    fun getUserNome(): String {
        return prefs.getString("USER_NOME", "Visitante") ?: "Visitante"
    }

    fun getUserEmail(): String {
        return prefs.getString("USER_EMAIL", "") ?: ""
    }

    fun isAdmin(): Boolean {
        return prefs.getBoolean("IS_ADMIN", false)
    }

    fun clearSession() {
        prefs.edit().clear().apply()
    }
}