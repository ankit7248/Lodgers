package com.example.lodgers

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    lateinit var ContinueAsGuest: TextView
    lateinit var Login: Button
    lateinit var Sign_Up: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        init()
        //Login page

        Login = findViewById(R.id.login_1)
        Sign_Up = findViewById(R.id.signUp_Button)


        Login.setOnClickListener {
            Intent(this, LoginPage::class.java).also {
                startActivity(it)
//            val intent = Intent(this@MainActivity, LoginPage::class.java)
//            var count: Int = 0
//            val timer = Timer()
//
//            timer.schedule(object : TimerTask() {
//                override fun run() {
//                    count++
//                    progress_bar_1.progress = count
//
//                    if (count > 100) {
//                        timer.cancel()
//                        startActivity(intent)
//                        finish()
//                    }
//
//                }
//            }, 0, 100)
//             mProgressBar.visibility = View.INVISIBLE
            }
        }

            Sign_Up.setOnClickListener {

                Intent(this, Sign_up::class.java).also {
                    startActivity(it)

                }
            }

            //Sign Up page


            ContinueAsGuest = findViewById(R.id.ContinueAsGuest)  // Clickable textview
            ContinueAsGuest.setOnClickListener {
                startActivity(Intent(this@MainActivity, Preferencespage::class.java))
            }
    }
}


//    private fun init() {
//        mProgressBar = findViewById(R.id.progress_bar_1)
//        mProgressBar.visibility = View.INVISIBLE
//    }

