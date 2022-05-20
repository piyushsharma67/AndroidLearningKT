package com.example.learning.fragments.login


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.learning.ApplicationClass
import com.example.learning.R
import com.example.learning.apiResponse.ApiResponse
import com.example.learning.dataClass.getOtpResponse.GetOtpResponse
import com.example.learning.repository.AuthRepository
import com.google.android.material.snackbar.Snackbar


class Login : Fragment() {

    private lateinit var viewModel: LoginViewModel
    lateinit var editText:EditText
    private lateinit var button:Button
    lateinit var mobileNumber:String
    lateinit var repository: AuthRepository
    lateinit var progressbar:ProgressBar
    lateinit var isSendObserver:Observer<ApiResponse<GetOtpResponse>>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        repository=(activity?.application as ApplicationClass).authRepository
        viewModel = ViewModelProvider(this,LoginViewModelFactory(repository)).get(LoginViewModel::class.java)

        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button= requireView().findViewById(R.id.button)
        editText=requireView().findViewById(R.id.editTextPhone)
        progressbar=requireView().findViewById(R.id.loading)
        progressbar.visibility=View.INVISIBLE

        editText.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->

            if (hasFocus) {
                editText.hint = ""
            }else{
                editText.hint="Phone Number"
            }
        }

        button.setOnClickListener {
           if(editText.text.toString()==="" || editText.text.toString().length!=10 ){
               Snackbar.make(
                   requireActivity().findViewById(android.R.id.content),
                   "Please enter a valid number", Snackbar.LENGTH_SHORT
               ).show()
           }
            else{
               sendOTP()
               progressbar.visibility=View.VISIBLE
           }
        }

        isSendObserver= Observer<ApiResponse<GetOtpResponse>>{
            when(it) {
                is ApiResponse.Success -> {
                    Log.d("Called","Called inside success")
                    progressbar.visibility=View.INVISIBLE
                    viewModel.userClicksOnButton()

                }
                is ApiResponse.Error -> {
                    progressbar.visibility=View.INVISIBLE
                    val snackBar = Snackbar.make(
                        requireActivity().findViewById(android.R.id.content),
                        it.error.toString(), Snackbar.LENGTH_SHORT
                    )
                    snackBar.show()
                }
                is ApiResponse.Loading -> {
                }
            }
        }

        viewModel.isSend.observe(viewLifecycleOwner, isSendObserver)

       viewModel.navigateToDetails.observe(viewLifecycleOwner, Observer {
           if(it===true){
               Log.d("Notification","true")
               val action=LoginDirections.actionLoginToSubmittOTP(editText.text.toString())
               activity?.findNavController(R.id.mainactivityfragmentcontainer)?.navigate(action)
               Log.d("value",it.toString())
               Snackbar.make(
                   requireActivity().findViewById(android.R.id.content),
                   "OTP Sent", Snackbar.LENGTH_SHORT
               ).show()
           }else {
               Log.d("Notification","false")
           }
       })


    }

    private fun sendOTP(){
        viewModel.sendOTP(editText.text.toString())
    }
//
    override fun onStart() {
        super.onStart()
        Log.d("START","on start called")
    }

    override fun onDestroyView() {
        super.onDestroyView()

//        viewModel.navigateToDetailsHandled()
        viewModel.isSend.removeObserver(isSendObserver)
        Log.d("Destroy view","on destry view called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("DESTROY","on destroy called")
    }

}
