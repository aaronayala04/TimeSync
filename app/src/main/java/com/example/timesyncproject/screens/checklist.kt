package com.example.timesyncproject.screens

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.timesyncproject.ReminderActivity
import com.example.timesyncproject.ToDoActivity
import com.example.timesyncproject.databinding.FragmentChecklistBinding

class checklist : Fragment() {

    private var _binding: FragmentChecklistBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentChecklistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.setReminderBtn.setOnClickListener {
            // Start ToDoActivity when createTaskBtn is clicked
            val intent = Intent(activity, ReminderActivity::class.java)
            startActivity(intent)
        }

        binding.viewTaskBtn.setOnClickListener {
            // Start ToDoActivity when viewTaskBtn is clicked
            val intent = Intent(activity, ToDoActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
