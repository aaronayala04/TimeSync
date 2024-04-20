package com.example.timesyncproject

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.timesyncproject.databinding.ActivityDetailBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var imageUrl: String
    private lateinit var itemKey: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras
        if (bundle != null) {
            binding.detailDesc.text = bundle.getString("Description")
            binding.detailTitle.text = bundle.getString("Title")
            binding.detailPriority.text = bundle.getString("Priority")

            imageUrl = bundle.getString("Image").orEmpty()
            itemKey = bundle.getString("Key").orEmpty()

            if (imageUrl.isNotEmpty()) {
                Glide.with(this)
                    .load(imageUrl)
                    .into(binding.detailImage)
            }
        }

        binding.fabDelete.setOnClickListener {
            deleteItem()
        }
    }

    private fun deleteItem() {
        val reference = FirebaseDatabase.getInstance().getReference("Todo List").child(itemKey)
        reference.removeValue()
            .addOnSuccessListener {
                Toast.makeText(this@DetailActivity, "Deleted", Toast.LENGTH_SHORT).show()
                startActivity(Intent(applicationContext, ToDoActivity::class.java))
                finish()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this@DetailActivity, "Failed to delete item from database: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
