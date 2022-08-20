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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList

var receiver = User()

class Chat : AppCompatActivity() {

    val auth = Firebase.auth
    val database = Firebase.database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = receiver.name
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        /** MessageList and adapter for rcv **/
        val messageList: ArrayList<Message> = ArrayList()
        val adapter = ChatAdapter(messageList, this)

        /** For Database call **/
        val senderRoom: String = auth.currentUser?.uid + receiver.uid
        val receiverRoom: String = receiver.uid + auth.currentUser?.uid

        /** Getting all messages of the current chat **/
        database.getReference("Chats").child(senderRoom).child("Messages")
            .addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                messageList.clear()
                for(message in snapshot.children) {
                    message.getValue(Message::class.java)?.let { messageList.add(it) }
                }

                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, error.toString(), Toast.LENGTH_SHORT).show()
            }

        })


        /** Sending a message **/
        binding.btnSend.setOnClickListener()
        {

            // Message details
            val date = Date()
            val message = Message(binding.etMessage.text.toString(), auth.currentUser?.uid, date.time)

            /** Sender Room **/
            // adding new message
            database.getReference("Chats")
                .child(senderRoom)
                .child("Messages")
                .push()
                .setValue(message)

            /** Receiver Room **/
            // adding new message
            database.getReference("Chats")
                .child(receiverRoom)
                .child("Messages")
                .push()
                .setValue(message)

            binding.etMessage.setText("")
        }


        binding.rcvChat.adapter = adapter
        binding.rcvChat.layoutManager = LinearLayoutManager(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}