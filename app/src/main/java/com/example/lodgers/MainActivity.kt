package com.example.lodgers

import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.Signature
import android.os.Bundle
import android.util.Log
import android.util.Base64

import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        login_1.setOnClickListener{
            Intent(this,LoginPage::class.java).also {
                startActivity(it)
            }
        }


    }


}