package com.example.timesyncproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class ModifyActivity : AppCompatActivity() {

    private lateinit var updateButton: Button
    private lateinit var updateEvent: EditText
    private lateinit var updateDay: EditText
    private lateinit var updateDate: EditText
    private lateinit var updateTime: EditText
    private lateinit var updatePeriod: EditText
    private var key = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify)

        updateButton = findViewById(R.id.updateButton)
        updateEvent = findViewById(R.id.updateEvent)
        updateDay = findViewById(R.id.updateDay)
        updateDate = findViewById(R.id.updateDate)
        updateTime = findViewById(R.id.updateTime)
        updatePeriod = findViewById(R.id.updatePeriod)

        val bundle = intent.extras
        bundle?.let {
            updateEvent.setText(it.getString("Event"))
            updateDay.setText(it.getString("Day"))
            updateDate.setText(it.getString("Date"))
            updateTime.setText(it.getString("Time"))
            updatePeriod.setText(it.getString("Period"))
            key = it.getString("Key") ?: ""
        }

        updateButton.setOnClickListener {
            if (validateInputs()) {
                saveData()
                val intent = Intent(this@ModifyActivity, TimetableActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun validateInputs(): Boolean {
        val event = updateEvent.text.toString().trim()
        val day = updateDay.text.toString().trim()
        val date = updateDate.text.toString().trim()
        val time = updateTime.text.toString().trim()
        val period = updatePeriod.text.toString().trim()


        if (event.isEmpty() || day.isEmpty() || date.isEmpty() || time.isEmpty() || period.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun saveData() {
        val event = updateEvent.text.toString()
        val day = updateDay.text.toString()
        val date = updateDate.text.toString()
        val time = updateTime.text.toString()
        val period = updatePeriod.text.toString()

        val dataClass2 = DataClass2(event,day,date,time,period)
        updateData(dataClass2)
    }

    private fun updateData(dataClass: DataClass2) {
        FirebaseDatabase.getInstance().getReference("Timetable").child(key)
            .setValue(dataClass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this@ModifyActivity, "Updated", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this@ModifyActivity, e.message.toString(), Toast.LENGTH_SHORT).show()
            }
    }
}
