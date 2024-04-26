package com.example.timesyncproject

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.timesyncproject.databinding.ActivityPomodoroBinding

class PomodoroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPomodoroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPomodoroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvStart.setOnClickListener {
            val studyTime = binding.etStudy.text.toString()
            val breakTime = binding.etBreak.text.toString()
            val roundCount = binding.etRound.text.toString()

            if (studyTime.isNotEmpty() && breakTime.isNotEmpty() && roundCount.isNotEmpty()) {
                if (validateInput(studyTime) && validateInput(breakTime) && validateInput(roundCount)) {
                    val intent = Intent(this, FeedActivity::class.java)
                    intent.putExtra("study", studyTime.toInt())
                    intent.putExtra("break", breakTime.toInt())
                    intent.putExtra("round", roundCount.toInt())
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Please enter only numbers in the fields above", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateInput(input: String): Boolean {
        return input.matches(Regex("\\d+"))
    }
}