package com.example.databasereadusedataapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignInActivity : AppCompatActivity() {
    private lateinit var databaseReference: DatabaseReference

    companion object{
        const val KEY1 = "com.example.databasereadusedataapp.SignInActivity.mail"
        const val KEY2 = "com.example.databasereadusedataapp.SignInActivity.name"
        const val KEY3 = "com.example.databasereadusedataapp.SignInActivity.id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val signInButton = findViewById<Button>(R.id.btnSignIn)
        val userName = findViewById<TextInputEditText>(R.id.etUserNameID)

        signInButton.setOnClickListener{
            // Taking reference till node "USERS"
            val uniqueId = userName.text.toString()

            if (uniqueId.isNotEmpty()){
                readData(uniqueId)
            } else{
                Toast.makeText(this, " Please enter userName", Toast.LENGTH_SHORT).show()
            }
        }
    } // onCREATE OVER

    private fun readData(uniqueId: String) {

        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
        // if user exists or not
        databaseReference.child(uniqueId).get().addOnSuccessListener {

            if(it.exists()){            // Welcome user in your app with intent and let them pass

                val email = it.child("email").value
                val name = it.child("name").value
                val userId = it.child("uniqueId").value

                val intentWelcome = Intent(this, WelcomeActivity::class.java)

                intentWelcome.putExtra(KEY1,email.toString())
                intentWelcome.putExtra(KEY2,name.toString())
                intentWelcome.putExtra(KEY3,userId.toString())

                startActivity(intentWelcome)

                Toast.makeText(this, " MISSION PASSED!" , Toast.LENGTH_SHORT).show()

            }else{             // No user exists in the database with the one specified username given
                Toast.makeText(this, "User Does Not Exist, Register First", Toast.LENGTH_SHORT).show()
            }


        }.addOnFailureListener {

            Toast.makeText(this, " MISSION FAILED!" , Toast.LENGTH_SHORT).show()
        }

    }
}