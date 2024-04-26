package com.example.timesyncproject.screens

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.timesyncproject.LoginActivity
import com.example.timesyncproject.R
import com.google.firebase.auth.FirebaseAuth

class settings : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        firebaseAuth = FirebaseAuth.getInstance()

        // Find the updateBtn button
        val updateBtn = view.findViewById<Button>(R.id.updateBtn)

        // Find the logOutBtn button
        val logOutBtn = view.findViewById<Button>(R.id.logOutBtn)

        // Find the deleteAccBtn button
        val deleteAccBtn = view.findViewById<Button>(R.id.deleteAccBtn)

        // Set click listener for the updateBtn button
        updateBtn.setOnClickListener {
            // Call the showResetPasswordDialog method
            showResetPasswordDialog()
        }

        // Set click listener for the logOutBtn button
        logOutBtn.setOnClickListener {
            // Call the logout method
            logout()
        }

        // Set click listener for the deleteAccBtn button
        deleteAccBtn.setOnClickListener {
            // Call the showDeleteConfirmationDialog method
            showDeleteConfirmationDialog()
        }

        return view
    }

    private fun logout() {
        firebaseAuth.signOut() // Sign out from Firebase
        Toast.makeText(requireContext(), "Logged Out", Toast.LENGTH_SHORT).show()
        val intent = Intent(requireContext(), LoginActivity::class.java)
        startActivity(intent)
        requireActivity().finish() // Call finish to prevent the user from returning to this activity on pressing the back button
    }

    private fun showResetPasswordDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val view = layoutInflater.inflate(R.layout.dialog_forgot, null)
        val userEmail = view.findViewById<EditText>(R.id.editBox)

        builder.setView(view)
        val dialog = builder.create()

        view.findViewById<Button>(R.id.btnReset).setOnClickListener {
            if (validateEmail(userEmail)) {
                firebaseAuth.sendPasswordResetEmail(userEmail.text.toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(requireContext(), "You will receive a password reset link shortly.", Toast.LENGTH_LONG).show()
                        } else {
                            // Even if the task is not successful, avoid giving specific feedback that the email does not exist
                            Toast.makeText(requireContext(), "An error occurred, please try again later.", Toast.LENGTH_SHORT).show()
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

    private fun showDeleteConfirmationDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val view = layoutInflater.inflate(R.layout.dialog_delete, null)
        val currentPasswordEditText = view.findViewById<EditText>(R.id.currentPasswordEditText)

        builder.setView(view)
        val dialog = builder.create()

        view.findViewById<Button>(R.id.btnDelete).setOnClickListener {
            // Get the current password entered by the user
            val currentPassword = currentPasswordEditText.text.toString()

            if (currentPassword.isBlank()) {
                currentPasswordEditText.error = "Please enter your password"
            } else {
                // Call the deleteAccount method and pass the current password
                deleteAccount(currentPassword)
                dialog.dismiss()
            }
        }

        // Set click listener for the cancel button
        view.findViewById<Button>(R.id.btnCancel).setOnClickListener {
            dialog.dismiss()
        }

        dialog.window?.setBackgroundDrawable(ColorDrawable(0))
        dialog.show()
    }


    private fun deleteAccount(currentPassword: String) {
        val user = firebaseAuth.currentUser

        // Check if the user is currently logged in
        if (user != null) {
            // Reauthenticate the user with their current password
            val credential = user.email?.let { FirebaseAuth.getInstance().signInWithEmailAndPassword(it, currentPassword) }

            // Delete the user account if reauthentication is successful
            credential?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    user.delete()
                        .addOnCompleteListener { deleteTask ->
                            if (deleteTask.isSuccessful) {
                                Toast.makeText(requireContext(), "Account deleted successfully", Toast.LENGTH_SHORT).show()
                                // Sign out after deleting the account
                                logout()
                            } else {
                                Toast.makeText(requireContext(), "Failed to delete account", Toast.LENGTH_SHORT).show()
                            }
                        }
                } else {
                    Toast.makeText(requireContext(), "Your password is incorrect", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}