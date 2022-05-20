package com.example.learning.fragments.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learning.apiResponse.ApiResponse
import com.example.learning.dataClass.getOtpResponse.GetOtpResponse
import com.example.learning.eventWrapper.EventWrapper
import com.example.learning.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(private val repository:AuthRepository) : ViewModel() {

    val isSend:LiveData<ApiResponse<GetOtpResponse>>
        get() = repository.responseLiveData

    private val _isSuccess = MutableLiveData<Boolean>()

    val navigateToDetails : LiveData<Boolean>
        get() = _isSuccess

    fun sendOTP(mobileNumber:String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getOTP(mobileNumber)
        }
    }

    fun navigateToDetailsHandled() {
        Log.d("viewmodel","i am called")
        _isSuccess.value = false
    }

    fun userClicksOnButton() {
        _isSuccess.value = true

    // Trigger the event by setting a new Event as a new value
    }

    //Bug which needs to be resolved regarding resetting live data
    fun resetSentValue(){
        repository.getOtpResponseLiveData.value=null
    }

}