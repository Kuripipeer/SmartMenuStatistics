package org.smartmenu.project.data.services

import com.russhwolf.settings.Settings

object Preferences {
    private val settings = Settings()

    fun saveAuthToken(token: String) {
        settings.putString("authToken", token)
    }

    fun getAuthToken(): String? {
        return settings.getStringOrNull("authToken")
    }

    fun saveIsLogged(isLogged: Boolean) {
        settings.putBoolean("isLogged", isLogged)
    }

    fun getIsLogged(): Boolean {
        return settings.getBoolean("isLogged", false)
    }

    fun clearPreferences() {
        settings.clear()
    }

    fun saveUserRole(role: String) {
        settings.putString("userRole", role)
    }
}