package com.example.lodgers

import android.content.Intent

import android.os.Bundle

import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.helper.widget.MotionEffect.TAG

import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider

import java.util.concurrent.TimeUnit

class Forget_Password_page: AppCompatActivity() {

    lateinit var mProgressBar : ProgressBar
    lateinit var Send_OTP_1 : Button
    lateinit var Email_Address_Forget_1 : EditText
    lateinit var number: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.forget_password_page)

        init()
        Send_OTP_1.setOnClickListener {
            number = Email_Address_Forget_1.text.trim().toString()
            if(number.isNotEmpty()){
                if (number.length == 10){  // check the number is equal or not

                    number = "+91$number"
                    mProgressBar.visibility = View.VISIBLE
                    val options = PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber(number)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS)    // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
                        .build()
                    PhoneAuthProvider.verifyPhoneNumber(options)
                }else
                {
                    Toast.makeText(this,"Please Enter correct Number",Toast.LENGTH_SHORT).show()
                }
            }else
            {
                Toast.makeText(this,"Please Enter Number",Toast.LENGTH_SHORT).show()
            }

        }
    }
// we can proceed the without init
    // Doubt
    private fun init(){
        mProgressBar = findViewById(R.id.progressBar)
        mProgressBar.visibility = View.INVISIBLE
        Send_OTP_1 = findViewById(R.id.Send_OTP)
        Email_Address_Forget_1 = findViewById(R.id.Email_Address_Forget)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {  // check the signIN IS Verified or not
        auth.signInWithCredential(credential)

            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    // Sign in success, update UI with the signed-in user's information



                    Toast.makeText(this@Forget_Password_page,"Authenticate Successfully",Toast.LENGTH_SHORT).show()
                    sendToMain()
                } else {
                    // Sign in failed, display a message and update the UI

                    Log.d(TAG,"signInWithPhoneAuthCredential")
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                    // Update UI
                }
            }
    }
    private fun sendToMain(){
        startActivity(Intent(this@Forget_Password_page,Otp_Create_password::class.java))
    }

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.
            signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {

            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.

            Log.d(TAG,"onVerifiactionFailed: ${e.toString()}")
            if (e is FirebaseAuthInvalidCredentialsException) {
                // Invalid request
            } else if (e is FirebaseTooManyRequestsException) {
                Log.d(TAG,"onVerifiactionFailed: ${e.toString()}")
                // The SMS quota for the project has been exceeded
            }

            // Show a message and update the UI
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            val intent = Intent(this@Forget_Password_page,Verify_OTP::class.java)
            intent.putExtra("OTP",verificationId)
            intent.putExtra("resendToken",token)
            intent.putExtra("number", number)
            startActivity(intent)
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            // Save verification ID and resending token so we can use them later
            mProgressBar.visibility  = View.INVISIBLE
        }
    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null){
            startActivity(Intent(this@Forget_Password_page, Otp_Create_password::class.java))
        }
    }

}