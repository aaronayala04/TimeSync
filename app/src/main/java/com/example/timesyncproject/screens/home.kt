package com.example.timesyncproject.screens

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.timesyncproject.EmailActivity
import com.example.timesyncproject.LoginActivity
import com.example.timesyncproject.PomodoroActivity
import com.example.timesyncproject.TimetableActivity
import com.example.timesyncproject.ToDoActivity
import com.example.timesyncproject.WeatherActivity
import com.example.timesyncproject.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class home : Fragment(){

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        firebaseAuth = FirebaseAuth.getInstance()


        // Set email text
        val emailTextView = binding.emailTextView
        val currentUser = firebaseAuth.currentUser
        currentUser?.let {
            val email = it.email
            emailTextView.text = email
        }

        // Set current date text
        val currentDateTextView = binding.textDate
        val pattern = "EEE d, yyyy"
        val currentDate = SimpleDateFormat(pattern, Locale.getDefault()).format(Date())
        currentDateTextView.text = currentDate





        // Set click listener for the to-do card view
        binding.homeToDo.setOnClickListener {
            val intent = Intent(requireActivity(), ToDoActivity::class.java)
            startActivity(intent)
        }

        // Set click listener for the pomodoro card view
        binding.homePomodoro.setOnClickListener {
            val intent = Intent(requireActivity(), PomodoroActivity::class.java)
            startActivity(intent)
        }

        binding.homeWeather.setOnClickListener {
            val intent = Intent(requireActivity(), WeatherActivity::class.java)
            startActivity(intent)
        }

        binding.homeCalender.setOnClickListener {
            val intent = Intent(requireActivity(), TimetableActivity::class.java)
            startActivity(intent)
        }

        binding.homeNotif.setOnClickListener {
            val intent = Intent(requireActivity(), EmailActivity::class.java)
            startActivity(intent)
        }

        binding.homeLogOut.setOnClickListener {
            firebaseAuth.signOut() // Sign out from Firebase
            Toast.makeText(requireContext(), "Logged Out", Toast.LENGTH_SHORT).show()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}