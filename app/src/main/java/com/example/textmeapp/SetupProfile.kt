package com.example.textmeapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.textmeapp.DataClass.User
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

            if(profileImage != null) {
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
                                    val i = Intent(this, HomeScreen::class.java)
                                    startActivity(i)
                                    finishAffinity()
                                }
                            } }
                        }
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