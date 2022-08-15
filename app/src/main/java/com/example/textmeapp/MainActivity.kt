package com.example.textmeapp

import android.animation.Animator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.airbnb.lottie.LottieAnimationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        /** Handling splash screen animation **/
        val animation = findViewById<LottieAnimationView>(R.id.animView)
        animation.addAnimatorListener(object: Animator.AnimatorListener{
            override fun onAnimationStart(p0: Animator?) {}

            override fun onAnimationEnd(p0: Animator?) {
                val i = Intent(applicationContext, RegisterPhoneNo::class.java)
                startActivity(i)
            }

            override fun onAnimationCancel(p0: Animator?) {}

            override fun onAnimationRepeat(p0: Animator?) {}

        })
    }
}