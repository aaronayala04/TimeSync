package com.example.timesyncproject.screens
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.timesyncproject.R
import com.example.timesyncproject.PomodoroActivity

class pomodoro : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_pomodoro, container, false)

        // Find the button
        val goToPomodoroButton = view.findViewById<Button>(R.id.goToPomodoro)

        // Set OnClickListener for the button
        goToPomodoroButton.setOnClickListener {
            // Create an Intent to start the PomodoroActivity
            val intent = Intent(activity, PomodoroActivity::class.java)
            startActivity(intent)
        }

        return view
    }
}
