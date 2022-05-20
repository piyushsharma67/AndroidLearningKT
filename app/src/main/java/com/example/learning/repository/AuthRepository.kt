package com.example.farmartpro.repository

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.learning.api.authservice.AuthService
import com.example.learning.apiResponse.ApiResponse
import com.example.learning.bodyClass.submittOTP.SubmittOtpBody
import com.example.learning.dataClass.getOtpResponse.GetOtpResponse
import com.example.learning.dataClass.user.User
import com.example.learning.database.Farmart_Database
import java.lang.Exception
import kotlin.coroutines.coroutineContext

class AuthRepository(private val authService:AuthService,private val database:Database)  {

    val getOtpResponseLiveData= MutableLiveData<ApiResponse<GetOtpResponse>>()

    val responseLiveData: LiveData<ApiResponse<GetOtpResponse>>
    get() = getOtpResponseLiveData


    suspend fun getOTP(mobileNumber:String){
//        getOtpResponseLiveData.postValue(ApiResponse.Loading())
        val result=authService.getOTP(mobileNumber = mobileNumber.toLong())
       try{
           if(result.code()===200){
               Log.d("ran once","ran once")
               getOtpResponseLiveData.postValue(ApiResponse.Success(result.body()))
           }else{
               Log.d("Error",result.body().toString())
               getOtpResponseLiveData.postValue(ApiResponse.Error("user does not exist inside database"))
           }
       }catch(e:Exception){
           Log.d("Error","catch block")
           getOtpResponseLiveData.postValue(ApiResponse.Error(e.message.toString()))
       }
    }


    val submittOtpResponseData= MutableLiveData<ApiResponse<User>>()

    val SubmittOtpResponseLiveData: LiveData<ApiResponse<User>>
        get() = submittOtpResponseData
//
//
    suspend fun verifyOTP(otp:String,mobileNumber:String){

        val result=authService.submittOTP(SubmittOtpBody(otp,mobileNumber.toLong()))

        try{
            if(result.code()===200){
                Log.d("result",result.body().toString())

                submittOtpResponseData.postValue(ApiResponse.Success(result.body()))
            }else{
                Log.d("Error",result.body().toString())
                submittOtpResponseData.postValue(ApiResponse.Error("user does not exist inside database"))
            }
        }catch(e:Exception){
            Log.d("Error","catch block")
            submittOtpResponseData.postValue(ApiResponse.Error(e.message.toString()))
        }
    }


}