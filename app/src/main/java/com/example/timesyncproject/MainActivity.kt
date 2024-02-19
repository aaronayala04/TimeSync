package com.example.timesyncproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            // User is signed in, navigate to the welcome page
            startActivity(Intent(this, WelcomePage::class.java))
        } else {
            // No user is signed in, navigate to the login activity
            startActivity(Intent(this, LoginActivity::class.java))
        }
        finish() // Finish MainActivity to remove it from the activity stack
    }

    fun goToLogin(view: View){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)

    }
}