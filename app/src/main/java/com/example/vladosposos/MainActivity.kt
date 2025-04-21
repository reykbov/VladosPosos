package com.example.vladosposos

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.vladosposos.databinding.ActivityMainBinding
import io.github.jan.supabase.auth.auth

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val supabaseHelper = SupabaseHelper()
    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        sharedPreferencesHelper = SharedPreferencesHelper(applicationContext)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        var session = supabaseHelper.supabase.auth.currentSessionOrNull()
        if(!sharedPreferencesHelper.isUserRemembered()) session = null
        if(session != null) {
            navController.navigate(R.id.tiVoshelFragment)
        }
        else {
            navController.navigate(R.id.loginFragment)
        }
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        if(!sharedPreferences.getBoolean("rememberMe", false)) {
//            lifecycleScope.launch {
//                supabaseHelper.userSignOut()
//            }
//        }
//    }
}