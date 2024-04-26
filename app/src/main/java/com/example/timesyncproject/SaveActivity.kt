package com.example.timesyncproject

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import java.text.DateFormat
import java.util.Calendar

class SaveActivity : AppCompatActivity() {
    private lateinit var saveButton: Button
    private lateinit var uploadEvent: EditText
    private lateinit var uploadDay: EditText
    private lateinit var uploadDate: EditText
    private lateinit var uploadTime: EditText
    private lateinit var uploadPeriod: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save)

        uploadEvent = findViewById(R.id.uploadEvent)
        uploadDay = findViewById(R.id.uploadDay)
        uploadDate = findViewById(R.id.uploadDate)
        uploadTime = findViewById(R.id.uploadTime)
        uploadPeriod = findViewById(R.id.uploadPeriod)
        saveButton = findViewById(R.id.saveButton)

        saveButton.setOnClickListener {
            if (validateInputs()) {
                saveData()
            }
        }
    }

    private fun validateInputs(): Boolean {
        val event = uploadEvent.text.toString().trim()
        val day = uploadDay.text.toString().trim()
        val date = uploadDate.text.toString().trim()
        val time = uploadTime.text.toString().trim()
        val period = uploadPeriod.text.toString().trim()


        if (event.isEmpty() || day.isEmpty() || date.isEmpty() || time.isEmpty() || period.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun saveData() {
        val event = uploadEvent.text.toString()
        val day = uploadDay.text.toString()
        val date = uploadDate.text.toString()
        val time = uploadTime.text.toString()
        val period = uploadPeriod.text.toString()

        val dataClass2 = DataClass2(event,day,date,time,period)
        uploadData(dataClass2)
    }

    private fun uploadData(dataClass: DataClass2) {
        val currentDate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().time)
        FirebaseDatabase.getInstance().getReference("Timetable").child(currentDate)
            .setValue(dataClass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this@SaveActivity, "Saved", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this@SaveActivity, e.message.toString(), Toast.LENGTH_SHORT).show()
            }
    }
}

