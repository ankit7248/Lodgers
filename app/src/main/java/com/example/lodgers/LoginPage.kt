package com.example.lodgers

import android.content.Intent

import android.os.Bundle

import android.widget.TextView

import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity

import com.facebook.AccessToken

import com.facebook.CallbackManager

import com.facebook.FacebookCallback

import com.facebook.FacebookException

import com.facebook.login.LoginResult

import com.google.android.gms.auth.api.signin.GoogleSignIn

import com.google.android.gms.auth.api.signin.GoogleSignInAccount

import com.google.android.gms.auth.api.signin.GoogleSignInOptions

import com.google.firebase.auth.FacebookAuthProvider

import com.google.firebase.auth.FirebaseAuth

import com.google.firebase.auth.GoogleAuthProvider

import kotlinx.android.synthetic.main.login_page.*

import kotlinx.coroutines.CoroutineScope

import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.launch

import kotlinx.coroutines.tasks.await

import kotlinx.coroutines.withContext

lateinit var auth: FirebaseAuth

const val REQUEST_CODE_SIGN_IN = 0
var callbackManager: CallbackManager? = null

// Login page

class LoginPage : AppCompatActivity() {

    lateinit var ContinueAsGuest_1: TextView
    lateinit var Forget_Password_page_1: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_page)

        ContinueAsGuest_1 = findViewById(R.id.ContinueAsGuest_1)
        ContinueAsGuest_1.setOnClickListener {
            startActivity(Intent(this@LoginPage, Preferencespage::class.java))
        }

        Forget_Password_page_1 = findViewById(R.id.Forget_Password)
        Forget_Password_page_1.setOnClickListener {
            startActivity(Intent(this@LoginPage, Forget_Password_page::class.java))
        }


        auth = FirebaseAuth.getInstance()
        auth.signOut()
        callbackManager = CallbackManager.Factory.create()

        login_facebook_button.setReadPermissions("email")
        login_facebook_button.setOnClickListener {
            FacebookSignIn()
        }

        login_2.setOnClickListener {
            LoginUser()
        }


        // Google Page

        Google.setOnClickListener {

            val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(getString(R.string.webclient))
                .build()
            val signInClient = GoogleSignIn.getClient(this, options)
            signInClient.signInIntent.also {
                startActivityForResult(it, REQUEST_CODE_SIGN_IN)
            }
        }
    }

    // Google Page

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        callbackManager!!.onActivityResult(
            requestCode,
            resultCode,
            data
        )  // !!(Null Assertion) -> if it is null then print error and if it is not null it execute

        if (requestCode == REQUEST_CODE_SIGN_IN) {
            val account = GoogleSignIn.getSignedInAccountFromIntent(data).result
            account?.let { // Account check itS null or not by ? -> this Operation
                googleAuthForFirebase(it) // we are connecting googleSignIn with firebase
            }
        }
    }

    private fun googleAuthForFirebase(account: GoogleSignInAccount) {
        val credentials = GoogleAuthProvider.getCredential(account.idToken, null)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                auth.signInWithCredential(credentials).await()
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@LoginPage, "Successfully logged in", Toast.LENGTH_SHORT)
                        .show()
                    startActivity(Intent(this@LoginPage, Preferencespage::class.java))
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@LoginPage, e.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun FacebookSignIn() {

        callbackManager?.let {
            login_facebook_button.registerCallback(it, object : FacebookCallback<LoginResult> {

                override fun onCancel() {

                }

                override fun onError(error: FacebookException) {

                }

                override fun onSuccess(result: LoginResult) {
                    handleFacebookAccessToken(result.accessToken)
                    val intent = Intent(this@LoginPage, Preferencespage::class.java)
                    startActivity(intent)
                }

            })
        }
    }


    private fun handleFacebookAccessToken(accessToken: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(accessToken.token)
        auth.signInWithCredential(credential)
            .addOnFailureListener {
//                e-> Toast.makeText(this,e.message,Toast.LENGTH_SHORT).show()
            }
            .addOnSuccessListener { result ->
                val email: String? = result.user?.email
//                Toast.makeText(this,"You logged with email" +email,Toast.LENGTH_SHORT).show()
            }

    }

    // Login page

    private fun LoginUser() {
        val emails = Email_Address.text.toString()
        val password = Text_Password.text.toString()
        if (emails.isNotEmpty() && password.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    auth.signInWithEmailAndPassword(emails, password).await()
                    withContext(Dispatchers.Main) {
                        checkLoggerInstate()
                        startActivity(Intent(this@LoginPage, Preferencespage::class.java))
                    }

                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@LoginPage, e.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun checkLoggerInstate() {
        val user = auth.currentUser
        if (user == null) {
            Toast.makeText(this@LoginPage, "You are Not Logged In", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this@LoginPage, "You are Logged In", Toast.LENGTH_SHORT).show()
        }
    }
}