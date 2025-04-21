package com.example.vladosposos

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

class SupabaseHelper {
    val supabase = createSupabaseClient(
        "https://kpsiwwnacletmxukrhpf.supabase.co",
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Imtwc2l3d25hY2xldG14dWtyaHBmIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDQxMTcwNjEsImV4cCI6MjA1OTY5MzA2MX0.Q1LAKP6URY0jeuF3lwX49A_6e6sjXi6kOvTKAhvpdmo"
    ) {
        install(Auth)
        install(Postgrest)
    }
    val adminAuthClient = supabase.auth.admin

    suspend fun signUpWithEmail(mail: String, pass: String) {
        val user = supabase.auth.signUpWith(provider = Email) {
            email = mail
            password = pass
        }
    }

    suspend fun signInWithEmail(mail: String, pass: String) {
        val user = supabase.auth.signInWith(provider = Email) {
            email = mail
            password = pass
        }
    }

    suspend fun userSignOut() {
        supabase.auth.signOut()
    }

    suspend fun isUserExist(mail: String) : Boolean {
        supabase.auth.importAuthToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Imtwc2l3d25hY2xldG14dWtyaHBmIiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsImlhdCI6MTc0NDExNzA2MSwiZXhwIjoyMDU5NjkzMDYxfQ.FnCZ7dCsFPL7yOssTX5WtaZ65zAskHbfCwZPA7dCGzg")
        val userInfoList = adminAuthClient.retrieveUsers()
        val emailList = userInfoList.map { it.email }
        return emailList.contains(mail)
    }
}