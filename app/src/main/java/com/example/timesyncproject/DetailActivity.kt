package com.example.timesyncproject

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.clans.fab.FloatingActionButton
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DetailActivity : AppCompatActivity() {
    private lateinit var detailDesc: TextView
    private lateinit var detailTitle: TextView
    private lateinit var detailLang: TextView
    private lateinit var deleteButton: FloatingActionButton
    private lateinit var editButton: FloatingActionButton
    private var key = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        detailDesc = findViewById(R.id.detailDesc)
        detailTitle = findViewById(R.id.detailTitle)
        deleteButton = findViewById(R.id.deleteButton)
        editButton = findViewById(R.id.editButton)
        detailLang = findViewById(R.id.detailLang)

        val bundle = intent.extras
        bundle?.let {
            detailDesc.text = it.getString("Description")
            detailTitle.text = it.getString("Title")
            detailLang.text = it.getString("Language")
            key = it.getString("Key") ?: ""
        }

        deleteButton.setOnClickListener {
            val reference: DatabaseReference = FirebaseDatabase.getInstance().getReference("Notes List")
            reference.child(key).removeValue()
            Toast.makeText(this@DetailActivity, "Deleted", Toast.LENGTH_SHORT).show()
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }

        editButton.setOnClickListener {
            val intent = Intent(this@DetailActivity, UpdateActivity::class.java)
                .putExtra("Title", detailTitle.text.toString())
                .putExtra("Description", detailDesc.text.toString())
                .putExtra("Language", detailLang.text.toString())
                .putExtra("Key", key)
            startActivity(intent)
        }
    }
}
