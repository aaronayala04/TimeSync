package com.example.timesyncproject

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.clans.fab.FloatingActionButton
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class ViewActivity : AppCompatActivity() {
    private lateinit var detailEvent: TextView
    private lateinit var detailDay: TextView
    private lateinit var detailDate: TextView
    private lateinit var detailTime: TextView
    private lateinit var detailPeriod: TextView


    private lateinit var deleteButton: FloatingActionButton
    private lateinit var editButton: FloatingActionButton
    private var key = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)

        detailEvent = findViewById(R.id.detailEvent)
        detailDay = findViewById(R.id.detailDay)
        detailDate = findViewById(R.id.detailDate)
        detailTime = findViewById(R.id.detailTime)
        detailPeriod = findViewById(R.id.detailPeriod)
        deleteButton = findViewById(R.id.deleteButton)
        editButton = findViewById(R.id.editButton)

        val bundle = intent.extras
        bundle?.let {
            detailEvent.text = it.getString("Event")
            detailDay.text = it.getString("Day")
            detailDate.text = it.getString("Date")
            detailTime.text = it.getString("Time")
            detailPeriod.text = it.getString("Period")
            key = it.getString("Key") ?: ""
        }

        deleteButton.setOnClickListener {
            val reference: DatabaseReference = FirebaseDatabase.getInstance().getReference("Timetable")
            reference.child(key).removeValue()
            Toast.makeText(this@ViewActivity, "Deleted", Toast.LENGTH_SHORT).show()
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }

        editButton.setOnClickListener {
            val intent = Intent(this@ViewActivity, ModifyActivity::class.java)
                .putExtra("Event", detailEvent.text.toString())
                .putExtra("Day", detailDay.text.toString())
                .putExtra("Date", detailDate.text.toString())
                .putExtra("Time", detailTime.text.toString())
                .putExtra("Period", detailPeriod.text.toString())
                .putExtra("Key", key)
            startActivity(intent)
        }
    }
}
