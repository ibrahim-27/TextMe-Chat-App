package com.example.textmeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.textmeapp.Adapters.UserListAdapter
import com.example.textmeapp.DataClass.User
import com.example.textmeapp.databinding.ActivityHomeScreenBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class HomeScreen : AppCompatActivity() {

    val database = Firebase.database
    val auth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /** List and Adapter for rcv **/
        var userList : ArrayList<User> = ArrayList()
        var adapter = UserListAdapter(userList, this)

        /** Database call **/
        val ref = database.getReference("Users")
        ref.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(users in snapshot.children)
                {
                    if(users.child("uid").value == auth.currentUser?.uid)
                        continue

                    users.getValue(User::class.java)?.let { userList.add(it)}
                }

                binding.rcvUserList.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, error.toString(), Toast.LENGTH_SHORT).show()
            }

        })

        binding.rcvUserList.adapter = adapter
        binding.rcvUserList.layoutManager = LinearLayoutManager(this)
    }

    /** Functions for the button on the action bar - using menu **/
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.btn_logout)
        {
            Firebase.auth.signOut()
            val i = Intent(this, RegisterPhoneNo::class.java)
            startActivity(i)
            finishAffinity()
        }
        return super.onOptionsItemSelected(item)
    }
}