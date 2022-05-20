package com.example.farmartpro

import android.app.Application
import android.util.Log
import com.example.learning.api.authservice.AuthService
import com.example.learning.api.authservice.retrofitHelper.RetrofitHelper
import com.example.learning.database.Database
import com.example.learning.repository.AuthRepository

class ApplicationClass:Application() {

   lateinit var authRepository: AuthRepository

    override fun onCreate() {
        super.onCreate()
        Log.d("application class","hi i am called")
        val authService= RetrofitHelper.getInstance().create(AuthService::class.java)
        val database=Database.getDatbase(applicationContext)
        authRepository= AuthRepository(authService,database)
        if (this::authRepository.isInitialized) {
            // File is initialized
            Log.d("application class","hi i am called2")
        } else {
            // File is not initialized
            Log.d("application class","hi i am called6")
        }
        Log.d("application class","hi i am called2")
    }


}