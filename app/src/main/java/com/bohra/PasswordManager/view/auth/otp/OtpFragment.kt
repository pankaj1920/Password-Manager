package com.bohra.PasswordManager.view.auth.otp

import android.content.ContentValues.TAG

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.text.HtmlCompat
import com.bohra.PasswordManager.R
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.bohra.PasswordManager.databinding.FragmentOtpBinding
import com.bohra.PasswordManager.utils.*
import com.bohra.PasswordManager.view.dashboard.DashBoardActivity
import com.google.firebase.auth.*


class OtpFragment : Fragment(), VerificationId {

    private lateinit var binding: FragmentOtpBinding
    private var verificationId: String? = null
    private var mobile: String? = null
    private lateinit var mAuth: FirebaseAuth
    private lateinit var btnVerify: Button
    private lateinit var edtOtpOne: EditText
    private lateinit var edtOtpTwo: EditText
    private lateinit var edtOtpThree: EditText
    private lateinit var edtOtpFour: EditText
    private lateinit var edtOtpFive: EditText
    private lateinit var edtOtpSix: EditText
    private lateinit var tvOtpDec: TextView
    private lateinit var navigationHost: NavHostFragment
    private lateinit var controller: NavController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentOtpBinding.inflate(inflater, container, false)




        mAuth = FirebaseAuth.getInstance()
        navigationHost =
            requireActivity().supportFragmentManager.findFragmentById(R.id.authNavHostFragmentContainer) as NavHostFragment
        controller = navigationHost.navController
        mobile = arguments?.getString(Constants.mobile)
        initView()
        moveToNext()

        var help = getString(R.string.otp_desc)
        help = help.replace("mobile", "<font color='#000000'>$mobile</font>")
        tvOtpDec.text = Html.fromHtml(help, HtmlCompat.FROM_HTML_MODE_LEGACY)
        btnVerify.setOnClickListener {
            val userOtp = "${edtOtpOne.text.trim()}" +
                    "${edtOtpTwo.text.trim()}" + "${edtOtpThree.text.trim()}" +
                    "${edtOtpFour.text.trim()}" + "${edtOtpFive.text.trim()}" +
                    "${edtOtpSix.text.trim()}"

            if (chekLengthForSix(userOtp)) {
                showToast(requireContext(), getString(R.string.enter_six_digit_otp), true)

            } else {
                verifyVerificationCode(userOtp)
            }


        }
        if (mobile != null) {
            requireActivity().otpAuthentication(mAuth, mobile!!, this)
        }
        return binding.root
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    showToast(requireContext(), getString(R.string.login_success), true)

//                    showLog(TAG, "signInWithCredential:success")
//                    val user = task.result?.user

                    navigationIntent(
                        requireContext(),
                        requireContext(),
                        DashBoardActivity::class.java
                    )
                    activity?.finish()
                } else {
                    // Sign in failed, display a message and update the UI
                    showToast(requireContext(), task.exception?.message.toString(), true)
                    showLog(TAG, "signInWithCredential:failure ${task.exception}")
//                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
//                        // The verification code entered was invalid
//                    }
                    // Update UI
                }
            }
    }

    private fun verifyVerificationCode(otp: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId!!, otp)
        signInWithPhoneAuthCredential(credential)
    }

    override fun sendVerificationId(id: String) {
        showToast(requireContext(), id, true)
        verificationId = id
    }

    override fun success(message: String) {
        showToast(requireContext(), message, true)
    }

    override fun error(message: String) {
        showToast(requireContext(), message, true)
    }

    private fun moveToNext() {
        focusNext(edtOtpOne, edtOtpTwo)
        focusNext(edtOtpTwo, edtOtpThree, edtOtpOne)
        focusNext(edtOtpThree, edtOtpFour, edtOtpTwo)
        focusNext(edtOtpFour, edtOtpFive, edtOtpThree)
        focusNext(edtOtpFive, edtOtpSix, edtOtpFour)
        focusNext(fromEditText = edtOtpSix, previousEditText = edtOtpFive)
    }

    private fun initView() {
        btnVerify = binding.btnVerify
        edtOtpOne = binding.edtOtpOne
        edtOtpTwo = binding.edtOtpTwo
        edtOtpThree = binding.edtOtpThree
        edtOtpFour = binding.edtOtpFour
        edtOtpFive = binding.edtOtpFive
        edtOtpSix = binding.edtOtpSix
        tvOtpDec = binding.tvOtpDec

    }
}