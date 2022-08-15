package com.example.textmeapp

import `in`.aabhasjindal.otptextview.OTPListener
import `in`.aabhasjindal.otptextview.OtpTextView
import android.R
import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.textmeapp.databinding.ActivityPhoneNoVerificationBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit


class PhoneNoVerification : AppCompatActivity() {
    val auth = Firebase.auth
    var verificationId:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPhoneNoVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        binding.otpView.requestFocusOTP()

        /** Dialog box - loading for OTP **/
        val dialog = ProgressDialog(this)
        dialog.setMessage("Sending OTP")
        dialog.setCancelable(false)
        dialog.show()

        /** Setting text to user's phone number **/
        val phoneNumber:String = intent.extras?.get("phoneNumber").toString()
        binding.textView2.setText("Verify $phoneNumber")

        /** Generating OTP **/
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(object: PhoneAuthProvider.OnVerificationStateChangedCallbacks()
            {
                override fun onVerificationCompleted(p0: PhoneAuthCredential) {}

                override fun onVerificationFailed(p0: FirebaseException) {}

                override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                    super.onCodeSent(p0, p1)
                    dialog.dismiss()
                    verificationId = p0
                }

            }).build()

        PhoneAuthProvider.verifyPhoneNumber(options)

        /** After entering the OTP **/
        binding.otpView.otpListener = object : OTPListener {
            override fun onInteractionListener() {}

            override fun onOTPComplete(otp: String) {
                /** Signing in the user **/
                val credential: PhoneAuthCredential =
                    PhoneAuthProvider.getCredential(verificationId, binding.otpView.otp.toString())
                auth.signInWithCredential(credential).addOnCompleteListener {
                    if (it.isSuccessful)
                        Toast.makeText(this@PhoneNoVerification, "success", Toast.LENGTH_SHORT)
                            .show()
                    else
                        Toast.makeText(this@PhoneNoVerification, "failed", Toast.LENGTH_SHORT)
                            .show()
                }
            }
        }
    }
}