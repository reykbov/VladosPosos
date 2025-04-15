package com.example.vladosposos.fragments

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.OtpType
import io.github.jan.supabase.auth.admin.AdminUserBuilder
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.createSupabaseClient

class SupabaseHelper {
    val supabase = createSupabaseClient(
        "https://kpsiwwnacletmxukrhpf.supabase.co",
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Imtwc2l3d25hY2xldG14dWtyaHBmIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDQxMTcwNjEsImV4cCI6MjA1OTY5MzA2MX0.Q1LAKP6URY0jeuF3lwX49A_6e6sjXi6kOvTKAhvpdmo"
    ) {
        install(Auth)
    }

    suspend fun signUpWithEmail(mail: String, pass: String) {
        val user = supabase.auth.signUpWith(provider = Email) {
            email = mail
            password = pass
        }
    }

}