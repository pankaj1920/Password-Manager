package com.bohra.PasswordManager.utils

import android.app.Activity
import com.bohra.PasswordManager.R
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

fun Activity.otpAuthentication(
    mAuth: FirebaseAuth,
    mobile: String,
    verificationId: VerificationId
) {

    val options = PhoneAuthOptions.newBuilder(mAuth)
        .setPhoneNumber("+91${mobile}")       // Phone number to verify
        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
        .setActivity(this)                 // Activity (for callback binding)
        .setCallbacks(callBack(verificationId,this))          // OnVerificationStateChangedCallbacks
        .build()
    PhoneAuthProvider.verifyPhoneNumber(options)
}

private fun callBack(verificationId: VerificationId,activity: Activity): PhoneAuthProvider.OnVerificationStateChangedCallbacks {

    return object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onCodeSent(
            idverification: String,
            p1: PhoneAuthProvider.ForceResendingToken
        ) {
            super.onCodeSent(idverification, p1)
            verificationId.sendVerificationId(idverification)
        }
        override fun onVerificationCompleted(auth: PhoneAuthCredential) {
            verificationId.success(activity.getString(R.string.success))
        }
        override fun onVerificationFailed(error: FirebaseException) {
            verificationId.error(error.localizedMessage!!)
        }
    }
}

interface VerificationId {
    fun sendVerificationId(id: String)
    fun success(message: String)
    fun error(message: String)
}



