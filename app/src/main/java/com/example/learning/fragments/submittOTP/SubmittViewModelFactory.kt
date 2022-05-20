package com.example.farmartpro.fragments.submittOTP

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.learning.repository.AuthRepository

class SubmittViewModelFactory(private val repository: AuthRepository):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SubmittOTPViewModel(repository) as T
    }

}