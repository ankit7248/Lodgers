package com.example.lodgers

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import com.example.lodgers.Animation.Preferencespage
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

import kotlinx.android.synthetic.main.signup_page.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

lateinit var auth_1 :  FirebaseAuth
const val REQUEST_CODE_SIGN_IN_1 = 0
var callbackManager_1: CallbackManager? = null

class Sign_up: AppCompatActivity() {

    lateinit var Already_have_an_account_1: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_page)

        Already_have_an_account_1 = findViewById(R.id.Already_have_an_account)
        Already_have_an_account_1.setOnClickListener {
            startActivity(Intent(this@Sign_up, LoginPage::class.java))
        }

            auth_1 = FirebaseAuth.getInstance()
            auth_1.signOut()
            callbackManager_1 = CallbackManager.Factory.create()

            login_facebook_button_1.setReadPermissions("email")
            login_facebook_button_1.setOnClickListener {
                FacebookSignIn_1()
            }

        Signup_Button_1.setOnClickListener {
            registerUser()

        }

            // Google Page

            Google_1.setOnClickListener{

                val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .requestIdToken(getString(R.string.webclient))
                    .build()
                val signInClient = GoogleSignIn.getClient(this,options)
                signInClient.signInIntent.also {
                    startActivityForResult(it, REQUEST_CODE_SIGN_IN_1)
                }
            }
        }

        // Google Page

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)

            callbackManager_1!!.onActivityResult(requestCode,resultCode,data)

            if (requestCode == REQUEST_CODE_SIGN_IN_1){
                val account = GoogleSignIn.getSignedInAccountFromIntent(data).result
                account?.let{ // Account check itS null or not
                    googleAuthForFirebase(it)  // we are connecting googleSignIn with firebase
                }
            }
        }

        private fun googleAuthForFirebase(account: GoogleSignInAccount){
            val credentials = GoogleAuthProvider.getCredential(account.idToken,null)
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    auth_1.signInWithCredential(credentials).await()
                    withContext(Dispatchers.Main){
                        Toast.makeText(this@Sign_up,"Successfully logged in", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@Sign_up, Preferencespage::class.java))
                    }
                }catch (e: Exception){
                    withContext(Dispatchers.Main){
                        Toast.makeText(this@Sign_up,e.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        private fun FacebookSignIn_1() {

            callbackManager_1?.let {
                login_facebook_button_1.registerCallback(it,object: FacebookCallback<LoginResult> {

                    override fun onCancel() {

                    }

                    override fun onError(error: FacebookException) {

                    }

                    override fun onSuccess(result: LoginResult) {
                        handleFacebookAccessToken_1(result.accessToken)
                        startActivity(Intent(this@Sign_up, Preferencespage::class.java))
                    }
                })
            }
        }


        private fun handleFacebookAccessToken_1(accessToken: AccessToken) {
            val credential = FacebookAuthProvider.getCredential(accessToken.token)
            auth_1.signInWithCredential(credential)
                .addOnFailureListener{
//                e-> Toast.makeText(this,e.message,Toast.LENGTH_SHORT).show()
                }
                .addOnSuccessListener {   result ->
                    val email: String? = result.user?.email
//                Toast.makeText(this,"You logged with email" +email,Toast.LENGTH_SHORT).show()
                }

        }

    // Register User

    private fun registerUser(){
        val email = Email_Address_1.text.toString()
        val password = Text_Password_1.text.toString()
        val confirmPassword = Text_Password_2.text.toString()
        if(email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty())
        {
            CoroutineScope(Dispatchers.IO).launch {
                // IO -> Fetching data from the database is an IO operation, which is done on the IO thread.
                try {
                    if (password!= confirmPassword){

                        Toast.makeText(this@Sign_up,"Password would no be matched",Toast.LENGTH_SHORT).show()
                        return@launch
                    }


                    if(password.length < 5){
                        Toast.makeText(this@Sign_up,"Password must be at least 5 character",Toast.LENGTH_SHORT).show()
                        return@launch
                    }

                    if(password.isEmpty()) {
                    Toast.makeText(this@Sign_up,"field can't be empty",Toast.LENGTH_SHORT).show()
                        return@launch
                    }

                    else{
                    auth_1.createUserWithEmailAndPassword(email,password).await()
                        startActivity(Intent(this@Sign_up,LoginPage::class.java))
                      // create the email and password and await until when our registraion will be completed
                    withContext(Dispatchers.Main){ // update the login state to user
                        checkRegisterdInState()

                    }
                }
                } catch (e: Exception){ // catch will throw the exception or wrong thing like when we register
                    withContext(Dispatchers.Main){

                        //withContext -> switch the context

                        // main -> // main -> It is mostly used when we need to perform the UI operations
                        // within the coroutine,as UI can only be changed from the main thread(also called the UI thread).

                        Toast.makeText(this@Sign_up, e.message,Toast.LENGTH_SHORT).show()
                    }  //  e.message -> show error message

                }
            }


        }
    }

    private fun checkRegisterdInState() {
        if (auth_1.currentUser == null)
        {
            Toast.makeText(this@Sign_up, "You are Not Registerd In!",Toast.LENGTH_SHORT).show()
        }
        else
        {
            Toast.makeText(this@Sign_up, "You are Registerd In!",Toast.LENGTH_SHORT).show()
        }
    }




}