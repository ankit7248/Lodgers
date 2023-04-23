package com.example.lodgers.Preference

import com.example.lodgers.Animation.Explore_Pg_Flat
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lodgers.Profiledata
import com.example.lodgers.R
import com.example.lodgers.REQUEST_CODE_IMAGE_PICK
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.profile_page.*
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

class ProfilePage : AppCompatActivity() {

    var Curfile: Uri? = null
    var imageRef = Firebase.storage.reference
    lateinit var  Delete : TextView

    private val personCollectionRef = Firebase.firestore.collection("Profile")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_page)

//        Update_Profile.setOnClickListener {
//
//        }

        Profile_Continue.setOnClickListener {
            val profile = olditems()
            savedatabase(profile)
//            val profile  = ::olditems  store the olditems function in profile variable
//            savedatabase(profile())
        }


        Frame_Uplaod_Button.setOnClickListener {
            Intent(Intent.ACTION_GET_CONTENT).also {
                it.type = "image/*"
                startActivityForResult(it, REQUEST_CODE_IMAGE_PICK)
            }
        }

        Delete = findViewById(R.id.Delete_Current_pic)
        Delete.setOnClickListener {
            deleteImage("images")
        }

//        Spinner_Gender.onItemClickListener = object : AdapterView.OnItemClickListener
//        {
//             fun onNothingSelected(p0: AdapterView<*>?) {
//            }
//            override fun onItemClick(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
//
//                Toast.makeText(this@ProfilePage,"You Selected ${adapterView?.getItemAtPosition(position).toString()}",
//                    Toast.LENGTH_SHORT).show()
//            }
//
//        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_IMAGE_PICK) {
            data?.data?.let {
                Curfile = it
                // Choose current file form media
                Update_Profile.setImageURI(it)
                uploadImageStorage("images")// Upload the image in Update_Profile
            }
        }
    }
    private fun savedatabase(profile: Profiledata) = CoroutineScope(Dispatchers.IO).async {
        if (olditems() != null){  // Error in this line
            try {
                personCollectionRef.add(profile).await()
                startActivity(Intent(this@ProfilePage, Explore_Pg_Flat::class.java)) // await -> is use when our data completely completed and its show the result
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@ProfilePage, "Sucessfully saved data!", Toast.LENGTH_SHORT)
                        .show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@ProfilePage, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun olditems(): Profiledata {

        val firstName = First_name.text.toString()
        val lastName = Last_Name.text.toString()
        val age = Age.text.toString().toInt()
        val address = Profile_Address.text.toString()
        val Bio = Profile_BIO.text.toString()
        return Profiledata(firstName, lastName, age, address, Bio)

    }

    // Image Upload in firebase
    private fun uploadImageStorage(filename: String) = CoroutineScope(Dispatchers.IO).async {
        try {
            Curfile?.let {

                imageRef.child("images/$filename").putFile(it).await()
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@ProfilePage, "Successfully Uploaded", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(this@ProfilePage, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun deleteImage(filename: String) {

            val DeleteDialog = AlertDialog.Builder(this@ProfilePage)
            .setTitle("Delete Photo")
            .setMessage("Do you want to Delete the Current Photo?")
            .setIcon(R.drawable.delete_image)
            .setPositiveButton("Yes") { _, _ ->

                CoroutineScope(Dispatchers.IO).launch{
                    try {
                        imageRef.child("images/$filename").delete().await()
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@ProfilePage,
                                "You deleted the Current Pic",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }catch (e: Exception){

                        withContext(Dispatchers.Main) {

                            Toast.makeText(this@ProfilePage, e.message, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }

            }

            .setNegativeButton("No"){_,_->
                Toast.makeText(
                    this@ProfilePage,
                    "You didn't deleted the Current Pic",
                    Toast.LENGTH_SHORT
                ).show()
            }
        DeleteDialog.show()

    }

}