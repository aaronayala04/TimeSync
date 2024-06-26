package com.example.timesyncproject

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.timesyncproject.screens.checklist
import com.example.timesyncproject.screens.home
import com.example.timesyncproject.screens.information
import com.example.timesyncproject.screens.pomodoro
import com.example.timesyncproject.screens.settings
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class WelcomePage : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener{




    private lateinit var drawerLayout: DrawerLayout
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_page)

        firebaseAuth = FirebaseAuth.getInstance()

        drawerLayout = findViewById(R.id.drawer_layout)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,home()).commit()
            navigationView.setCheckedItem(R.id.nav_home)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_home -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,home()).commit()
            R.id.nav_calendar -> startActivity(Intent(this, TimetableActivity::class.java))
            R.id.nav_checklist -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,checklist()).commit()
            R.id.nav_info -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,information()).commit()
            R.id.nav_pomodoro -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,pomodoro()).commit()
            R.id.nav_settings -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,settings()).commit()
            R.id.nav_stats -> startActivity(Intent(this, WeatherActivity::class.java))
            R.id.nav_logout -> {
                firebaseAuth.signOut() // Sign out from Firebase
                Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        } else{
            onBackPressedDispatcher.onBackPressed()
        }
    }
}