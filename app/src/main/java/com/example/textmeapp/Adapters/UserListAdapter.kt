package com.example.textmeapp.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.textmeapp.Chat
import com.example.textmeapp.DataClass.User
import com.example.textmeapp.R
import de.hdodenhof.circleimageview.CircleImageView

class UserListAdapter(var userList: List<User>, var context: Context):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.sample_conversation, parent, false)

        view.setOnClickListener()
        {
            val i = Intent(context, Chat::class.java)
            context.startActivity(i)
        }

        return UserListViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
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

    inner class UserListViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)
}