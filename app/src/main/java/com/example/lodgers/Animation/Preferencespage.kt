package com.example.lodgers.Animation
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.lodgers.Preference.ProfilePage
import com.example.lodgers.R


class Preferencespage : AppCompatActivity() {

    lateinit var Skip: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.preference_page)

        Skip = findViewById(R.id.Skip_button)

        Skip.setOnClickListener {
            startActivity(Intent(this@Preferencespage,  ProfilePage::class.java))
        }


    }
}