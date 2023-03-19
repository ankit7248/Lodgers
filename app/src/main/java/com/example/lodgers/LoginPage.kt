package com.example.lodgers
import android.content.Intent
import android.os.Bundle

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


lateinit var auth : FirebaseAuth

const val REQUEST_CODE_SIGN_IN = 0
var callbackManager: CallbackManager? = null

    // Login page

class LoginPage : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_page)


        auth = FirebaseAuth.getInstance()
        auth.signOut()
        callbackManager = CallbackManager.Factory.create()

        login_facebook_button.setReadPermissions("email")
        login_facebook_button.setOnClickListener {
            FacebookSignIn()
        }

        login_2.setOnClickListener{
            LoginUser()
        }

        // Google Page

        Google.setOnClickListener{

            val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(getString(R.string.webclient))
                .build()
            val signInClient = GoogleSignIn.getClient(this,options)
            signInClient.signInIntent.also {
                startActivityForResult(it, REQUEST_CODE_SIGN_IN)
            }
        }
    }

    private fun FacebookSignIn() {

        callbackManager?.let {
            login_facebook_button.registerCallback(it,object:FacebookCallback<LoginResult>{

                override fun onCancel() {

                }

                override fun onError(error: FacebookException) {

                }

                override fun onSuccess(result: LoginResult) {
                    handleFacebookAccessToken(result.accessToken)
                }

            })
        }
    }


    private fun handleFacebookAccessToken(accessToken: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(accessToken.token)
        auth.signInWithCredential(credential)
            .addOnFailureListener{
                e-> Toast.makeText(this,e.message,Toast.LENGTH_SHORT).show()
            }
            .addOnSuccessListener {   result ->
                val email: String? = result.user?.email
                Toast.makeText(this,"You logged with email" +email,Toast.LENGTH_SHORT).show()
            }

    }



    // Login page

    private fun LoginUser(){
        val emails = Email_Address.text.toString()
        val password = Text_Password.text.toString()
        if (emails.isNotEmpty() && password.isNotEmpty()){
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    auth.signInWithEmailAndPassword(emails,password).await()
                    withContext(Dispatchers.Main){
                        checkLoggerInstate()
                    }

                }catch (e: Exception){
                    withContext(Dispatchers.Main){
                        Toast.makeText(this@LoginPage,e.message,Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun checkLoggerInstate(){
        val user = auth.currentUser
        if (user == null){
            Toast.makeText(this@LoginPage,"You are Not Logged In", Toast.LENGTH_SHORT).show()
        }else
        {
            Toast.makeText(this@LoginPage,"You are Logged In", Toast.LENGTH_SHORT).show()
        }
    }

    // Google Page

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        callbackManager!!.onActivityResult(requestCode,resultCode,data)

        if (requestedOrientation == REQUEST_CODE_SIGN_IN){
            val account = GoogleSignIn.getSignedInAccountFromIntent(data).result
            account.let{ // Account check itS null or not
                googleAuthForFirebase(it) // we are connecting googleSignIn with firebase
            }
        }
    }

    private fun googleAuthForFirebase(account: GoogleSignInAccount){
        val credentials = GoogleAuthProvider.getCredential(account.idToken,null)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                auth.signInWithCredential(credentials).await()
                withContext(Dispatchers.Main){
                    Toast.makeText(this@LoginPage,"Successfully logged in", Toast.LENGTH_SHORT).show()
                }
            }catch (e: Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(this@LoginPage,e.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}