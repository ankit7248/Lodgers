package com.example.lodgers

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.otp_new_password.*
import kotlinx.android.synthetic.main.signup_page.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class Otp_Create_password : AppCompatActivity() {
    lateinit var Reset_Password: Button
    lateinit var auth_1: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.otp_new_password)

        auth_1 = FirebaseAuth.getInstance()

        Reset_Password = findViewById(R.id.Reset_Password)

        Reset_Password.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        val new_password = Create_Password_edit_text.text.toString()
        val new_confirmPassword = Create_Confirm_Password_edit_text.text.toString()
        if (new_password.isNotEmpty() && new_confirmPassword.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                // IO -> Fetching data from the database is an IO operation, which is done on the IO thread.
                try {
                    if (new_password != new_confirmPassword) {

                        Toast.makeText(
                            this@Otp_Create_password,
                            "Password would no be matched",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@launch
                    }
                    if (new_password.length < 5) {
                        Toast.makeText(
                            this@Otp_Create_password,
                            "Password must be at least 5 character",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@launch
                    }

                    if (new_password.isEmpty()) {
                        Toast.makeText(
                            this@Otp_Create_password,
                            "field can't be empty",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@launch
                    } else {
                        auth_1.confirmPasswordReset(new_password, new_confirmPassword)
                            .await()
                        startActivity(Intent(this@Otp_Create_password, LoginPage::class.java))// create the email and password and await until when our registraion will be completed
                        withContext(Dispatchers.Main) { // update the login state to user
                            checkLoggerInstate()

                        }
                    }
                } catch (e: Exception) { // catch will throw the exception or wrong thing like when we register
                    withContext(Dispatchers.Main) {

                        //withContext -> switch the context

                        // main -> // main -> It is mostly used when we need to perform the UI operations
                        // within the coroutine,as UI can only be changed from the main thread(also called the UI thread).

                        Toast.makeText(this@Otp_Create_password, e.message, Toast.LENGTH_SHORT)
                            .show()
                    }  //  e.message -> show error message

                }
            }


        }
    }

    private fun checkLoggerInstate() {
        val user = auth.currentUser
        if (user == null) {
            Toast.makeText(this@Otp_Create_password, "You are Not Logged In", Toast.LENGTH_SHORT)
                .show()
        } else {
            Toast.makeText(this@Otp_Create_password, "You are Logged In", Toast.LENGTH_SHORT).show()
        }
    }
}