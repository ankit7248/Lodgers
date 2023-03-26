package com.example.lodgers

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.helper.widget.MotionEffect
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import kotlinx.android.synthetic.main.verify_page_otp.*
import java.util.concurrent.TimeUnit

class Verify_OTP : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    lateinit var Verify_OTP: Button
    lateinit var inputOtp_1: EditText
    lateinit var inputOtp_2: EditText
    lateinit var inputOtp_3: EditText
    lateinit var inputOtp_4: EditText
    lateinit var inputOtp_5: EditText
    lateinit var inputOtp_6: EditText
    lateinit var ResendTv: TextView
    lateinit var progressBar: ProgressBar

    lateinit var OTP: String
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var phoneNumber: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.verify_page_otp)

        OTP = intent.getStringExtra("OTP").toString()
        resendToken = intent.getParcelableExtra("resendToken")!!
        phoneNumber = intent.getStringExtra("number")!!

        init()

        Verify_OTP.setOnClickListener {
            // Collect OTP from ALL  editTEXT
            val typedOTP =
                (inputOtp_1.text.toString() + inputOtp_2.text.toString() + inputOtp_3.text.toString() + inputOtp_4.text.toString() + inputOtp_5.text.toString() + inputOtp_6.text.toString())

            if (typedOTP.isNotEmpty()) {
                if (typedOTP.length == 6) {

                    val credential: PhoneAuthCredential =
                        PhoneAuthProvider.getCredential(OTP, typedOTP)

                    progressBar.visibility = View.VISIBLE
                    signInWithPhoneAuthCredential(credential)
                } else {

                    Toast.makeText(this, "Please Enter  Correct OTP", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please Enter OTP", Toast.LENGTH_SHORT).show()
            }
        }

        progressBar.visibility = View.INVISIBLE
        addTextChangeListener()
        resendOTPVisibility()

        ResendTv.setOnClickListener {
            ResendVerificationCode()
            resendOTPVisibility()
        }
    }

    private fun addTextChangeListener() {
        inputOtp_1.addTextChangedListener(EditTextWatcher(inputOtp_1))
        inputOtp_2.addTextChangedListener(EditTextWatcher(inputOtp_2))
        inputOtp_3.addTextChangedListener(EditTextWatcher(inputOtp_3))
        inputOtp_4.addTextChangedListener(EditTextWatcher(inputOtp_4))
        inputOtp_5.addTextChangedListener(EditTextWatcher(inputOtp_5))
        inputOtp_6.addTextChangedListener(EditTextWatcher(inputOtp_6))
    }

    private fun init() {
        auth = FirebaseAuth.getInstance()
        progressBar = findViewById(R.id.Otp_ProgressBar)
        Verify_OTP = findViewById(R.id.Verify_Button)
        ResendTv = findViewById(R.id.Resend_Otp)
        inputOtp_1 = findViewById(R.id.OTP_Text_Password_1)
        inputOtp_2 = findViewById(R.id.OTP_Text_Password_2)
        inputOtp_3 = findViewById(R.id.OTP_Text_Password_3)
        inputOtp_4 = findViewById(R.id.OTP_Text_Password_4)
        inputOtp_5 = findViewById(R.id.OTP_Text_Password_5)
        inputOtp_6 = findViewById(R.id.OTP_Text_Password_6)
    }

    private fun resendOTPVisibility() {
        inputOtp_1.setText("")
        inputOtp_2.setText("")
        inputOtp_3.setText("")
        inputOtp_4.setText("")
        inputOtp_5.setText("")
        inputOtp_6.setText("")
        ResendTv.visibility = View.INVISIBLE
        ResendTv.isEnabled = false

        Handler(Looper.myLooper()!!).postDelayed(Runnable {
            ResendTv.visibility = View.VISIBLE
            ResendTv.isEnabled = true
        }, 60000)
    }

    private fun ResendVerificationCode() {
        val options = PhoneAuthOptions.newBuilder(com.example.lodgers.auth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS)    // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(callbacks)
            .setForceResendingToken(resendToken)// OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {  // check the signIN IS Verified or not
        com.example.lodgers.auth.signInWithCredential(credential)

            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    progressBar.visibility = View.VISIBLE
                    // Sign in success, update UI with the signed-in user's information


                    Toast.makeText(this@Verify_OTP, "Authenticate Successfully", Toast.LENGTH_SHORT)
                        .show()
                    sendToMain()
                } else {
                    // Sign in failed, display a message and update the UI

                    Log.d(MotionEffect.TAG, "signInWithPhoneAuthCredential")
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                    // Update UI
                }
            }
    }

    private fun sendToMain() {
        startActivity(Intent(this@Verify_OTP, Otp_Create_password::class.java))
    }

    val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

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

            Log.d(MotionEffect.TAG, "onVerifiactionFailed: ${e.toString()}")
            if (e is FirebaseAuthInvalidCredentialsException) {
                // Invalid request
            } else if (e is FirebaseTooManyRequestsException) {
                Log.d(MotionEffect.TAG, "onVerifiactionFailed: ${e.toString()}")
                // The SMS quota for the project has been exceeded
            }

            // Show a message and update the UI
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            OTP = verificationId
            resendToken = token
        }
    }

    inner class EditTextWatcher(private val view: View) : TextWatcher {
        /**
         * This method is called to notify you that, within `s`,
         * the `count` characters beginning at `start`
         * are about to be replaced by new text with length `after`.
         * It is an error to attempt to make changes to `s` from
         * this callback.
         */
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        /**
         * This method is called to notify you that, within `s`,
         * the `count` characters beginning at `start`
         * have just replaced old text that had length `before`.
         * It is an error to attempt to make changes to `s` from
         * this callback.
         */
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {


        }

        /**
         * This method is called to notify you that, somewhere within
         * `s`, the text has been changed.
         * It is legitimate to make further changes to `s` from
         * this callback, but be careful not to get yourself into an infinite
         * loop, because any changes you make will cause this method to be
         * called again recursively.
         * (You are not told where the change took place because other
         * afterTextChanged() methods may already have made other changes
         * and invalidated the offsets.  But if you need to know here,
         * you can use [Spannable.setSpan] in [.onTextChanged]
         * to mark your place and then look up from here where the span
         * ended up.
         */
        override fun afterTextChanged(s: Editable?) {
            val text = s.toString()

            // You can switch from one text to another text in OTP
            when (view.id) {
                R.id.OTP_Text_Password_1 -> if (text.length == 1) inputOtp_2.requestFocus()
                R.id.OTP_Text_Password_2 -> if (text.length == 1) inputOtp_3.requestFocus() else if (text.isEmpty()) inputOtp_1.requestFocus()
                R.id.OTP_Text_Password_3 -> if (text.length == 1) inputOtp_4.requestFocus() else if (text.isEmpty()) inputOtp_2.requestFocus()
                R.id.OTP_Text_Password_4 -> if (text.length == 1) inputOtp_5.requestFocus() else if (text.isEmpty()) inputOtp_3.requestFocus()
                R.id.OTP_Text_Password_5 -> if (text.length == 1) inputOtp_6.requestFocus() else if (text.isEmpty()) inputOtp_4.requestFocus()
                R.id.OTP_Text_Password_6 -> if (text.isEmpty()) inputOtp_5.requestFocus()
            }
        }
    }
}