package com.example.textmeapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.example.textmeapp.databinding.ActivityRegisterPhoneNoBinding

class RegisterPhoneNo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =  ActivityRegisterPhoneNoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        // so the keyboard appears automatically
        binding.etPhoneNo.requestFocus()

        /** When confirm button is pressed **/
        binding.btnConfirm.setOnClickListener()
        {
            if(binding.etPhoneNo.text.toString().isEmpty())
            {
                binding.etPhoneNo.setError("Enter your Phone Number")
                binding.etPhoneNo.requestFocus()
                return@setOnClickListener
            }

            // next activity to verify the phone number
            val i = Intent(this, PhoneNoVerification::class.java)
            i.putExtra("phoneNumber", binding.etPhoneNo.text.toString())
            startActivity(i)
        }
    }
}