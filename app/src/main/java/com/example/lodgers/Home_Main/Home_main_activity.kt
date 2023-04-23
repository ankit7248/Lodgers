package com.example.lodgers.Home_Main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.lodgers.Home_page.Favourite
import com.example.lodgers.Home_page.Home_Profile
import com.example.lodgers.Home_page.RoomateFinder
import com.example.lodgers.Home_page.ghar_home
import com.example.lodgers.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class Home_main_activity : AppCompatActivity() {
    lateinit var bottomNavigationView: BottomNavigationView

    //    lateinit var navHostFragment: NavHostFragment
//        lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_main_activity)


        bottomNavigationView = findViewById(R.id.BottomNavigation_home)

        bottomNavigationView.itemIconTintList = null
//        navController = navHostFragment.navController
//        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
//        // when we use fragment container view then we have to use supportFragmentManager
//        setupWithNavController(bottomNavigationView,navController)
        val GharHome = ghar_home()
        val favourite = Favourite()
        val home_Profile = Home_Profile()
        val roomateFinder = RoomateFinder()

        setcurrentFragment(GharHome)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.ghar_home -> setcurrentFragment(GharHome)
                R.id.favourite -> setcurrentFragment(favourite)
                R.id.roomateFinder -> setcurrentFragment(roomateFinder)
                R.id.home_Profile -> setcurrentFragment(home_Profile)
            }
            true
        }
    }

    private fun setcurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }

}