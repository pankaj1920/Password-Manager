package com.bohra.PasswordManager.view.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bohra.PasswordManager.databinding.FragmentLoginBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import java.util.concurrent.TimeUnit


class LoginFragment : Fragment() {

    lateinit var binding:FragmentLoginBinding
    lateinit var mAuth:FirebaseAuth
    lateinit var mobileNo:String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth = FirebaseAuth.getInstance()

        binding.btnProceed.setOnClickListener {
           /* val action = LoginFragmentDirections.actionLoginFragmentToOtpFragment()
            findNavController().navigate(action)*/

            mobileNo = binding.edtMobileNum.text.toString().trim()

            getOtp();
        }



    }

    private fun getOtp() {
        val options = PhoneAuthOptions.newBuilder(mAuth)
            .setPhoneNumber(mobileNo)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity())                 // Activity (for callback binding)
            .setCallbacks(mCallBack)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private val mCallBack : PhoneAuthProvider.OnVerificationStateChangedCallbacks = object:PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

        override fun onCodeSent(p0: String, p1: ForceResendingToken) {
            super.onCodeSent(p0, p1)
        }

        override fun onVerificationCompleted(p0: PhoneAuthCredential) {

            TODO("Not yet implemented")
        }

        override fun onVerificationFailed(p0: FirebaseException) {
            TODO("Not yet implemented")
        }

    }

 /*   // callback method is called on Phone auth provider.
    private val   // initializing our callbacks for on
    // verification callback method.
            CallBack: OnVerificationStateChangedCallbacks =
        object : OnVerificationStateChangedCallbacks() {
            // below method is used when
            // OTP is sent from Firebase
            override fun onCodeSent(s: String, forceResendingToken: ForceResendingToken) {
                super.onCodeSent(s, forceResendingToken)
                // when we receive the OTP it
                // contains a unique id which
                // we are storing in our string
                // which we have already created.
                verificationId = s
            }

            // this method is called when user
            // receive OTP from Firebase.
            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                // below line is used for getting OTP code
                // which is sent in phone auth credentials.
                val code = phoneAuthCredential.smsCode

                // checking if the code
                // is null or not.
                if (code != null) {
                    // if the code is not null then
                    // we are setting that code to
                    // our OTP edittext field.
                    edtOTP.setText(code)

                    // after setting this code
                    // to OTP edittext field we
                    // are calling our verifycode method.
                    verifyCode(code)
                }
            }

            // this method is called when firebase doesn't
            // sends our OTP code due to any error or issue.
            override fun onVerificationFailed(e: FirebaseException) {
                // displaying error message with firebase exception.
                Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_LONG).show()
            }
        }*/

}