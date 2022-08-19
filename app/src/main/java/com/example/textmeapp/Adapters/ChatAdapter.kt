package com.example.textmeapp.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.textmeapp.DataClass.Message
import com.example.textmeapp.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class ChatAdapter(var messageList:List<Message>, var context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if(viewType == 1) {
             val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.sample_send_message, parent, false)

            return ChatSendViewHolder(view)
        }

        val view = LayoutInflater.from(parent.context).inflate(R.layout.sample_receive_message, parent, false)

        return ChatReceiveViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder.javaClass == ChatSendViewHolder::class.java)
        {
            val tempholder = holder as ChatSendViewHolder
            tempholder.itemView.findViewById<TextView>(R.id.tv_messageSend).text = messageList[position].message
        }
        else
        {
            val tempholder = holder as ChatReceiveViewHolder
            tempholder.itemView.findViewById<TextView>(R.id.tv_messengeReceive).text = messageList[position].message
        }

        //Toast.makeText(context, messageList.size, Toast.LENGTH_SHORT).show()
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun getItemViewType(position: Int): Int {
        if(messageList[position].sender_uid == Firebase.auth.currentUser?.uid)
            return 1        // 1 -> message sent
        else
            return 2        // 2 -> message receive
    }

    inner class ChatSendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    inner class ChatReceiveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}