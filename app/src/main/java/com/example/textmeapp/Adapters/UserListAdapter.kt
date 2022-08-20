package com.example.textmeapp.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.textmeapp.Chat
import com.example.textmeapp.DataClass.User
import com.example.textmeapp.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import de.hdodenhof.circleimageview.CircleImageView

class UserListAdapter(var userList: List<User>, var context: Context, var onClick: OnUserClick):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.sample_conversation, parent, false)
        return UserListViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val sendeRoom = Firebase.auth.currentUser?.uid + userList[position].uid
        holder.itemView.apply {
            findViewById<TextView>(R.id.tv_userName).text = userList[position].name
            Glide.with(context).load(userList[position].profileImg)
                .placeholder(R.drawable.user)
                .into(findViewById<CircleImageView>(R.id.iv_userImg))
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    inner class UserListViewHolder(itemView: View, onClick: OnUserClick):RecyclerView.ViewHolder(itemView)
    {
        init {
            itemView.setOnClickListener(){onClick.onUserClick(adapterPosition)}
        }
    }

    /** Interface to handle onClick **/
    interface OnUserClick
    {
        fun onUserClick(pos:Int)
    }
}