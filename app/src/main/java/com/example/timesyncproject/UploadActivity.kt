package com.example.timesyncproject

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import java.text.DateFormat
import java.util.Calendar

class UploadActivity : AppCompatActivity() {
    private lateinit var saveButton: Button
    private lateinit var uploadTopic: EditText
    private lateinit var uploadDesc: EditText
    private lateinit var uploadLang: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)

        uploadDesc = findViewById(R.id.uploadDesc)
        uploadTopic = findViewById(R.id.uploadTopic)
        uploadLang = findViewById(R.id.uploadLang)
        saveButton = findViewById(R.id.saveButton)

        saveButton.setOnClickListener {
            if (validateInputs()) {
                saveData()
            }
        }
    }

    private fun validateInputs(): Boolean {
        val title = uploadTopic.text.toString().trim()
        val desc = uploadDesc.text.toString().trim()
        val lang = uploadLang.text.toString().trim().toLowerCase()

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
        val title = uploadTopic.text.toString()
        val desc = uploadDesc.text.toString()
        val lang = uploadLang.text.toString()
        val dataClass = DataClass(title, desc, lang)
        uploadData(dataClass)
    }

    private fun uploadData(dataClass: DataClass) {
        val currentDate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().time)
        FirebaseDatabase.getInstance().getReference("Notes List").child(currentDate)
            .setValue(dataClass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this@UploadActivity, "Saved", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this@UploadActivity, e.message.toString(), Toast.LENGTH_SHORT).show()
            }
    }
}

