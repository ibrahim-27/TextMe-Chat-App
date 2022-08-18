package com.example.textmeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.textmeapp.databinding.ActivityChatBinding

class Chat : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}