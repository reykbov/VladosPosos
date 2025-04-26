package com.example.vladosposos

import android.content.Context

class SharedPreferencesHelper(context: Context) {
    val sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)

    fun rememberMe() {
        sharedPreferences.edit().putBoolean("rememberMe", true).apply()
    }

    fun notRememberMe() {
        sharedPreferences.edit().putBoolean("rememberMe", false).apply()
    }

    fun isUserRemembered() : Boolean {
        return sharedPreferences.getBoolean("rememberMe", false)
    }

    fun checkOnboarding() {
        sharedPreferences.edit().putBoolean("onboardingChecked", true).apply()
    }

    fun isOnboardingChecked() : Boolean {
        return sharedPreferences.getBoolean("onboardingChecked", false)
    }
}