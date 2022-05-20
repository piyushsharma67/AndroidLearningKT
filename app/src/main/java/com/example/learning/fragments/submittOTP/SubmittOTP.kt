package com.example.farmartpro.fragments.submittOTP

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.example.learning.ApplicationClass
import com.example.learning.R
import com.example.learning.apiResponse.ApiResponse
import com.example.learning.repository.AuthRepository
import com.google.android.material.snackbar.Snackbar

class SubmittOTP : Fragment() {

    lateinit var repository:AuthRepository
    private lateinit var viewModel: SubmittOTPViewModel
    val args: SubmittOTPArgs by navArgs()
    lateinit var otp:EditText
    lateinit var mobileNumber:String
    lateinit var button:Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        repository=(activity?.application as ApplicationClass).authRepository
        viewModel = ViewModelProvider(this,SubmittViewModelFactory(repository)).get(SubmittOTPViewModel::class.java)

        return inflater.inflate(R.layout.submitt_otp_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button=requireView().findViewById(R.id.verify)
        otp=requireView().findViewById(R.id.otp)

        mobileNumber = args.mobileNumber

        button.setOnClickListener {

            if(otp.text.toString()==="" || otp.text.toString().length!==4){
                Snackbar.make( requireActivity().findViewById(android.R.id.content),
                    "OTP can be of 4 digits only", Snackbar.LENGTH_SHORT).show()
            }else {
                viewModel.verifyOtp(otp.text.toString(),mobileNumber)
            }
        }

        viewModel.data.observe( viewLifecycleOwner,{
            when(it){
                is ApiResponse.Success->{
                    Log.d("user is",it.toString())
                    Snackbar.make(requireActivity().findViewById(android.R.id.content),"logged in",Snackbar.LENGTH_SHORT).show()

                }
                is ApiResponse.Error -> {
                    Snackbar.make(requireActivity().findViewById(android.R.id.content),"Not Logged in in",Snackbar.LENGTH_SHORT).show()
                }
            }
        })

    }

}