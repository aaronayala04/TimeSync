package com.example.timesyncproject

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.timesyncproject.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.loginButton.setOnClickListener {
            val email = binding.loginEmail.text.toString()
            val password = binding.loginPassword.text.toString()


            if (email.isNotEmpty() && password.isNotEmpty()){
                firebaseAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this){task ->
                        if (task.isSuccessful){
                            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this,WelcomePage::class.java)
                            startActivity(intent)
                            finish()
                        }else{
                            Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()

                        }
                    }
            } else{
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()

            }
        }

        binding.forgotPassword.setOnClickListener {
            showResetPasswordDialog()
        }

        binding.registerText.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
            finish()
        }
    }

    private fun showResetPasswordDialog() {
        val builder = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.dialog_forgot, null)
        val userEmail = view.findViewById<EditText>(R.id.editBox)

        builder.setView(view)
        val dialog = builder.create()

        view.findViewById<Button>(R.id.btnReset).setOnClickListener {
            if (validateEmail(userEmail)) {
                firebaseAuth.sendPasswordResetEmail(userEmail.text.toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "You will receive a password reset link shortly.", Toast.LENGTH_LONG).show()
                        } else {
                            // Even if the task is not successful, avoid giving specific feedback that the email does not exist
                            Toast.makeText(this, "An error occurred, please try again later.", Toast.LENGTH_SHORT).show()
                        }
                    }
                dialog.dismiss()
            }
        }

        view.findViewById<TextView>(R.id.forgotTitle).setOnClickListener {
            dialog.dismiss()
        }

        dialog.window?.setBackgroundDrawable(ColorDrawable(0))
        dialog.show()
    }

    private fun validateEmail(email: EditText): Boolean {
        if (email.text.toString().isEmpty()) {
            email.error = "Email cannot be empty"
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()) {
            email.error = "Invalid email format"
            return false
        }
        return true
    }
}

