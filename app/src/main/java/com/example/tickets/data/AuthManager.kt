package com.example.tickets.data

import android.content.Context
import android.content.SharedPreferences

/**
 * Управляет состоянием авторизации пользователя
 * Использует SharedPreferences для хранения ID текущего авторизованного пользователя
 */
class AuthManager(private val context: Context) {
    private val prefs: SharedPreferences = 
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    
    companion object {
        private const val PREFS_NAME = "auth_prefs"
        private const val KEY_CURRENT_USER_ID = "current_user_id"
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
    }
    
    /**
     * Сохраняет ID авторизованного пользователя
     */
    fun setCurrentUserId(userId: Long) {
        prefs.edit()
            .putLong(KEY_CURRENT_USER_ID, userId)
            .putBoolean(KEY_IS_LOGGED_IN, true)
            .apply()
    }
    
    /**
     * Получает ID текущего авторизованного пользователя
     */
    fun getCurrentUserId(): Long {
        return prefs.getLong(KEY_CURRENT_USER_ID, -1L)
    }
    
    /**
     * Проверяет, авторизован ли пользователь
     */
    fun isLoggedIn(): Boolean {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false) && getCurrentUserId() != -1L
    }
    
    /**
     * Выход из аккаунта
     */
    fun logout() {
        prefs.edit()
            .remove(KEY_CURRENT_USER_ID)
            .putBoolean(KEY_IS_LOGGED_IN, false)
            .apply()
    }
    
    /**
     * Очищает все данные авторизации
     */
    fun clearAuth() {
        prefs.edit().clear().apply()
    }
}

