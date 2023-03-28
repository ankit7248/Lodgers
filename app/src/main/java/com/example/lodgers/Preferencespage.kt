package com.example.lodgers
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


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