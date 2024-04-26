package com.example.timesyncproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class UpdateActivity : AppCompatActivity() {
    private lateinit var updateButton: Button
    private lateinit var updateDesc: EditText
    private lateinit var updateTitle: EditText
    private lateinit var updateLang: EditText
    private var key = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        updateButton = findViewById(R.id.updateButton)
        updateDesc = findViewById(R.id.updateDesc)
        updateLang = findViewById(R.id.updateLang)
        updateTitle = findViewById(R.id.updateTitle)

        val bundle = intent.extras
        bundle?.let {
            updateTitle.setText(it.getString("Title"))
            updateDesc.setText(it.getString("Description"))
            updateLang.setText(it.getString("Language"))
            key = it.getString("Key") ?: ""
        }

        updateButton.setOnClickListener {
            if (validateInputs()) {
                saveData()
                val intent = Intent(this@UpdateActivity, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun validateInputs(): Boolean {
        val title = updateTitle.text.toString().trim()
        val desc = updateDesc.text.toString().trim()
        val lang = updateLang.text.toString().trim().toLowerCase()

        if (title.isEmpty() || desc.isEmpty() || lang.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return false
        }

        if (lang != "low" && lang != "medium" && lang != "high") {
            Toast.makeText(this, "Must be either 'low', 'medium', or 'high'", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun saveData() {
        val title = updateTitle.text.toString().trim()
        val desc = updateDesc.text.toString().trim()
        val lang = updateLang.text.toString()
        val dataClass = DataClass(title, desc, lang)
        updateData(dataClass)
    }

    private fun updateData(dataClass: DataClass) {
        FirebaseDatabase.getInstance().getReference("Notes List").child(key)
            .setValue(dataClass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this@UpdateActivity, "Updated", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this@UpdateActivity, e.message.toString(), Toast.LENGTH_SHORT).show()
            }
    }
}
