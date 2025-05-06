package com.example.vladosposos.helpers

import android.content.Context
import androidx.core.content.edit

class SharedPreferencesHelper(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)

    fun rememberMe() {
        sharedPreferences.edit() { putBoolean("rememberMe", true) }
    }

    fun notRememberMe() {
        sharedPreferences.edit() { putBoolean("rememberMe", false) }
    }

    fun isUserRemembered() : Boolean {
        return sharedPreferences.getBoolean("rememberMe", false)
    }

    fun checkOnboarding() {
        sharedPreferences.edit() { putBoolean("onboardingChecked", true) }
    }

    fun isOnboardingChecked() : Boolean {
        return sharedPreferences.getBoolean("onboardingChecked", false)
    }
}