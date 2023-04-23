package com.example.lodgers.Animation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lodgers.Home_Main.Home_main_activity
import com.example.lodgers.databinding.ExplorePgFlatBinding

class Explore_Pg_Flat: AppCompatActivity() {

    lateinit var binding: ExplorePgFlatBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ExplorePgFlatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val getStarted = binding.ExploreGetStarted

        getStarted.setOnClickListener {
            startActivity(Intent(this, Home_main_activity::class.java))
        }

    }
}