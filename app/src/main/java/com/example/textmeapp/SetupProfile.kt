package com.example.textmeapp

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View.inflate
import android.widget.Toast
import androidx.core.content.res.ColorStateListInflaterCompat.inflate
import androidx.core.content.res.ComplexColorCompat.inflate
import com.example.textmeapp.DataClass.User
import com.example.textmeapp.databinding.ActivityHomeScreenBinding.inflate
import com.example.textmeapp.databinding.ActivitySetupProfileBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class SetupProfile : AppCompatActivity() {
    private lateinit var binding:ActivitySetupProfileBinding

    /** Firebase references **/
    val auth = Firebase.auth
    val database = Firebase.database
    val storage = Firebase.storage

    var profileImage: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetupProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        /** To change the profile Image **/
        binding.cameraIcon.setOnClickListener()
        {
            val intent = Intent()
            intent.setAction(Intent.ACTION_GET_CONTENT)
            intent.setType("image/*")
            startActivityForResult(intent, 45)
        }

        /** Setting up the profile **/
        binding.btnSetup.setOnClickListener()
        {
            if(binding.etName.text.toString().isEmpty())
            {
                binding.etName.requestFocus()
                binding.etName.setError("Please enter your name")
                return@setOnClickListener
            }
            
            if(profileImage == null)
            {
                Toast.makeText(this, "Please select a profile pic", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            /** Dialog box - loading for profile setup **/
            val dialog = ProgressDialog(this)
            dialog.setMessage("Logging in")
            dialog.setCancelable(false)
            dialog.show()


            val sref = auth.uid?.let { it1 -> storage.reference.child("Profiles").child(it1) }
            sref?.putFile(profileImage!!)?.addOnCompleteListener {
                if (it.isSuccessful)
                {
                    sref.downloadUrl.addOnSuccessListener {
                        /** Setting up user data **/
                        val imgUrl = it.toString()
                        val uid = auth.uid
                        val phoneNumber = auth.currentUser?.phoneNumber
                        val name = binding.etName.text.toString()
                        val user = User(uid, name, phoneNumber, imgUrl)
                        /** Storing user info on database **/
                        auth.uid?.let { it1 -> database.getReference("Users").child(it1).setValue(user).addOnCompleteListener {
                            if(it.isSuccessful)
                            {
                                dialog.dismiss()
                                val i = Intent(this, HomeScreen::class.java)
                                startActivity(i)
                                finishAffinity()
                            }
                            else{
                                dialog.dismiss()
                                Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                            }
                        } }
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(data != null && data.data != null)
        {
            binding.imageView3.setImageURI(data.data)
            profileImage = data.data
        }
    }
}