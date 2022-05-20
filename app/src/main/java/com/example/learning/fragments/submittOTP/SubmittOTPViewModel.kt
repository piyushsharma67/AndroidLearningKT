package com.example.farmartpro.fragments.submittOTP

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learning.api.authservice.AuthService
import com.example.learning.apiResponse.ApiResponse
import com.example.learning.dataClass.user.User
import com.example.learning.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SubmittOTPViewModel(private val repository: AuthRepository) : ViewModel() {
    fun verifyOtp(otp:String,mobileNumber:String){
        viewModelScope.launch(Dispatchers.IO){
            repository.verifyOTP(otp,mobileNumber)
        }
    }

    val data: LiveData<ApiResponse<User>>
    get() = repository.SubmittOtpResponseLiveData

//    fun saveuserInDB(user:User){
//        viewModelScope.launch (Dispatchers.IO){
//            repository.saveUser(user)
//        }
//    }
}