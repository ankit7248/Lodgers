package com.example.lodgers.Home_page

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.lodgers.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class Home_main_activity: AppCompatActivity() {
    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var navHostFragment: NavHostFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_main_activity)
        bottomNavigationView = findViewById(R.id.BottomNavigation_home)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        // when we use fragment container view then we have to use supportFragmentManager
        bottomNavigationView.setupWithNavController(navHostFragment!!.findNavController())
    }
}