package com.example.textmeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.textmeapp.Adapters.ChatAdapter
import com.example.textmeapp.DataClass.Message
import com.example.textmeapp.DataClass.User
import com.example.textmeapp.databinding.ActivityChatBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

var receiver = User()

class Chat : AppCompatActivity() {

    val auth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = receiver.name
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        /** MessageList and adapter for rcv **/
        var messageList: ArrayList<Message> = ArrayList()

        messageList.add(Message("1", "hello", auth.currentUser?.uid, 1))
        messageList.add(Message("1", "hello", "auth.currentUser?.uid", 1))
        messageList.add(Message("1", "hello", auth.currentUser?.uid, 1))
        messageList.add(Message("1", "hello", "auth.currentUser?.uid", 1))
        messageList.add(Message("1", "hello", auth.currentUser?.uid, 1))

        var adapter = ChatAdapter(messageList, this)


        binding.rcvChat.adapter = adapter
        binding.rcvChat.layoutManager = LinearLayoutManager(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}