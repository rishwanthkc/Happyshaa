package com.auraai

import android.app.Application
import android.util.Log
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

/**
 * Custom Application class for Aura AI.
 * Annotated with HiltAndroidApp to trigger Hilt's code generation.
 */
@HiltAndroidApp
class AuraApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        
        // Initialize Firebase SDK
        try {
            FirebaseApp.initializeApp(this)
            Log.i("AuraApplication", "Firebase successfully initialized.")
        } catch (e: Exception) {
            Log.e("AuraApplication", "Error initializing Firebase: ${e.message}")
        }
    }
}
